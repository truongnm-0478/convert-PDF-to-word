package controllers;

import Model.BO.UploadFileBO;
import Model.Bean.FileBean;
import Model.DAO.FileDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/DownloadFileServlet")
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DownloadFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fid = 0;
		
		try {
			fid = Integer.parseInt(request.getParameter("fid"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(fid != 0) {
			FileBean file = FileDAO.GetFile(fid);
			
			try {
				String scrpath = GetFolderPath("docxs").getAbsolutePath() + File.separator + file.getFname();
				java.nio.file.Path path = Paths.get(scrpath);
				byte[] data = Files.readAllBytes(path);
				
				response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			    response.setHeader("Content-disposition", "attachment; filename=" + file.getFname());
			    response.setContentLength(data.length);
			    InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			    
			    OutputStream outStream = response.getOutputStream();
			    byte[] buffer = new byte[4096];
			    int bytesRead = -1;
			    
			    while ((bytesRead = inputStream.read(buffer)) != -1) {
			      outStream.write(buffer, 0, bytesRead);
			    }
			    
			    inputStream.close();
			    outStream.close();
				
			} catch (Exception e) {
				request.getSession().setAttribute("message", "File không tồn tại hoặc đã bị xóa!");
				response.sendRedirect("profile.jsp");
			}
			
			
			
		}else {
			request.getSession().setAttribute("message", "File không tồn tại hoặc đã bị xóa!");
			response.sendRedirect("profile.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private File GetFolderPath(String folder) {
		
		ClassLoader classLoader = UploadFileBO.class.getClassLoader();
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
