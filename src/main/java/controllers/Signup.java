package controllers;

import Model.BO.UserBO;
import Model.Bean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username == null || password == null) {
			request.getSession().setAttribute("message","Vui lòng nhập username và password");
			response.sendRedirect("signup.jsp");
			return;
		}
		
		UserBean user = UserBO.select(username);
		
		if (user != null) {
			request.getSession().setAttribute("message","Username đã tồn tại!");
			response.sendRedirect("signup.jsp");
			return;
		}
		
		UserBO.create(new UserBean(username, password));
		request.getSession().setAttribute("message", "Đăng kí thành công!");
		response.sendRedirect("index.jsp");
	
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
