package Model.BO;

import Model.Bean.FileBean;
import Model.Bean.UserBean;
import Model.DAO.FileDAO;
import _CONSTAINT.CONSTAINT;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class ConvertFileBO implements Runnable {
	UserBean user;

	public ConvertFileBO(UserBean user) {
		this.user = user;
	}

	@Override
	public void run() {
		System.out.println("convert run");
		ArrayList<FileBean> files = FileDAO.GetListConvertFile(user);
		for (FileBean file : files) {
			String filename = file.getFname().split("\\.")[0];
			System.out.println("convert filename: " + filename);
			try {
				Convert(filename);
				System.out.println("convert done");
				FileDAO.ChangeStatus(file.getFid(), CONSTAINT.SUCCESS);
			} catch (Exception e) {
				System.out.println("convert fail");
				FileDAO.ChangeStatus(file.getFid(), CONSTAINT.CONVERT_ERROR);
			}
		}
	}

	private void Convert(String filename) throws Exception {
		System.out.println("convertting...");
		PdfDocument pdf = new PdfDocument();
		pdf.loadFromFile(GetFolderPath("pdfs").getAbsolutePath() + File.separator + filename + ".pdf");
		pdf.saveToFile(GetFolderPath("docxs").getAbsolutePath() + File.separator + filename + ".docx", FileFormat.DOCX);
		pdf.close();

	}

	private File GetFolderPath(String folder) {
		ClassLoader classLoader = ConvertFileBO.class.getClassLoader();
		URL resource = classLoader.getResource("");

		String projectPath = resource.getPath();
		projectPath = projectPath.split(".metadata")[0] + "CK/src/main/webapp/files";

		File folderUpload = new File(projectPath + "/" + folder);

		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}
}
