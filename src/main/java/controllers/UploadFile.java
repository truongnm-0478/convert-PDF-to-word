package controllers;

import Model.BO.ConvertFileBO;
import Model.BO.FileBO;
import Model.BO.UploadFileBO;
import Model.BO.UserBO;
import Model.Bean.FileBean;
import Model.Bean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebServlet("/UploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 50MB vì thư viện Stripe 
										//không convert được file lớn hơn
		maxRequestSize = 1024 * 1024 * 300) // 300MB
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final Queue<HttpServletRequest> requestQueue = new ConcurrentLinkedQueue<>();

	public UploadFile() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			try {
				if (request.getPart("files").getSize() != 0) {
					String username = request.getParameter("username");
					UserBean user = UserBO.select(username);
	
					UploadFileBO uploadFileBO = new UploadFileBO(request, user);
					uploadFileBO.run();
	
					requestQueue.offer(request);
					Thread newThread = new Thread(() -> {
						processNextRequest(user);
					});
					newThread.start();
					
					ArrayList<FileBean> files = FileBO.GetProcessedFile(user.getUid());
					request.getSession().setAttribute("Files", files);
					request.getSession().setAttribute("message", "Upload completed!");
					response.sendRedirect("profile.jsp");
				}
			} catch (Exception e) {
				e.printStackTrace();
		        request.getSession().setAttribute("message", "File size limit exceeded. Maximum allowed size is 50MB.");
				response.sendRedirect("profile.jsp");
		    }
	 

	}
	

	private  void processNextRequest(UserBean user) {
		HttpServletRequest nextRequest = requestQueue.poll();
		if (nextRequest != null) {
			synchronized (this) {
				new ConvertFileBO(user).run();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
