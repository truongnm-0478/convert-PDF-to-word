package controllers;

import _CONSTAINT.CONSTAINT;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ShowErrorServlet")
public class ShowErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowErrorServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int errorCode = 0;
		int uid = 0;
		try {
			errorCode = Integer.parseInt(request.getParameter("errorCode"));
			uid = Integer.parseInt(request.getParameter("uid"));
		} catch (Exception e) {
		}
		switch (errorCode) {
		case CONSTAINT.PROCESSING: {
			break;
		}
		case CONSTAINT.CONVERT_ERROR: {
			request.getSession().setAttribute("message",
					"Có lỗi xảy ra trong quá trình chuyển đổi, vui lòng kiểm tra lại tên file và thử lại sau!");
			break;
		}
		case CONSTAINT.UPLOAD_ERROR: {
			request.getSession().setAttribute("message",
					"Có lỗi xảy ra trong quá trình upload file, vui lòng thử lại sau!");
			break;
		}
		default:
			request.getSession().setAttribute("message", "Có lỗi xảy ra, vui lòng thử lại sau!");
			break;
		}

		response.sendRedirect("UserProfile?uid=" + uid);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
