package controllers;

import Model.BO.UserBO;
import Model.Bean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class C_User
 */
@WebServlet("/C_User")
public class C_User extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
    public C_User() {
        super();
    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
	
		UserBean user = UserBO.select(username);
		
		if (user == null) {
			request.getSession().setAttribute("message","Tên đăng nhập không hợp lệ!");
			response.sendRedirect("index.jsp");
			return;
		}
		
		if (password.equals(user.getPassword())) {
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("message", "Đăng nhập thành công!");
			response.sendRedirect("page.jsp");
		} else {
			request.getSession().setAttribute("message", "Tên đăng nhập hoặc mật khẩu không hợp lệ!");
			response.sendRedirect("index.jsp");
		
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
