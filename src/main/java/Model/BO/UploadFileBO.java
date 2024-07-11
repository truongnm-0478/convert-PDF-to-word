package Model.BO;

import Model.Bean.UserBean;
import Model.DAO.FileDAO;
import _CONSTAINT.CONSTAINT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UploadFileBO implements Runnable {
	HttpServletRequest request;
	UserBean user;

	public UploadFileBO(HttpServletRequest request, UserBean user) {
		this.request = request;
		this.user = user;
	}

	@Override
	public void run() {
		try {
			for (Part part : request.getParts()) {
				if (part.getName().equals("files")) {
					String filename = extractFileName(part);
					filename = new File(filename).getName();

					String newFilename = filename.split(".pdf")[0] + "_" + user.getUid() + "_" + user.getUsername() + ".pdf";

	                try {
	                    File file = new File(getFolderUpload(), newFilename);
						Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
						FileDAO.Upload(newFilename, user, CONSTAINT.PROCESSING);
					} catch (Exception e) {
						System.out.println(e);
						FileDAO.Upload(newFilename, user, CONSTAINT.UPLOAD_ERROR);
					}
				}
			}
			System.out.println("upload done");
		} catch (Exception e) {

		}
		//new ConvertFileBO(user).run();
	}

	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	private File getFolderUpload() {
		
        ClassLoader classLoader = UploadFileBO.class.getClassLoader();
        URL resource = classLoader.getResource("");

        String projectPath = resource.getPath();
        projectPath = projectPath.split(".metadata")[0] + "CK/src/main/webapp/files";
        
        File folderUpload = new File(projectPath + "/pdfs");

		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}

}
