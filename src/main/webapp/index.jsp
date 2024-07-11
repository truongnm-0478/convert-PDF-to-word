<%@page import="Model.Bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="stylesheet" href="./public/css/signup.css">
</head>

<body>
	
	<% 
		if(request.getSession().getAttribute("message") != null){
	%>
	<script type="text/javascript">
		alert("<%=request.getSession().getAttribute("message") %>")
	</script>
	<%
	request.getSession().setAttribute("message", null);
		} %>
	<header>
		
	</header>
	<div class="container">
		<div class="main">

			<form action="C_User" method="POST" class="form" id="form-1">
				<h3 class="heading">Đăng nhập</h3>
				<div class="spacer"></div>

				<input name="func" value="login" hidden>
				<div class="form-group">
					<label for="username" class="form-label">Tài khoản</label> <input
						id="username" name="username" rules="required" type="text"
						placeholder="VD: admin" class="form-control"> <span
						class="form-message"></span>
				</div>
				<div class="form-group">
					<label for="password" class="form-label">Mật khẩu</label> <input
						id="password" name="password" rules="required" type="password"
						placeholder="Nhập mật khẩu" class="form-control"> <span
						class="form-message"></span>
				</div>
				<div class="txt-right" >
					<a href="./signup.jsp" style="margin: 10px;">Đăng kí</a>
				</div>
		 

				<button class="form-submit">Đăng nhập</button>
			</form>
		</div>
	</div>


	<script src="./public/js/validator.js"></script>

	<script>
		var form = new Validator('#form-1');
	</script>


</body>

</html>