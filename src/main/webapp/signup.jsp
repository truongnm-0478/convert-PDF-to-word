<%@page import="Model.Bean.UserBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Sign Up</title>
	<link rel="stylesheet" href="./public/css/signup.css">
</head>

<body>
<% if (request.getSession().getAttribute("message") != null) { %>
<script type="text/javascript">
	alert("<%= request.getSession().getAttribute("message") %>");
</script>
<% request.getSession().setAttribute("message", null);
} %>
<header>
</header>
<div class="container">
	<div class="main">
		<form action="Signup" method="POST" class="form" id="form-1">
			<h3 class="heading">Đăng kí</h3>
			<div class="spacer"></div>

			<input name="func" value="login" hidden>
			<div class="form-group">
				<label for="username" class="form-label">Tài khoản</label>
				<div class="col-sm-10">
					<input id="username" name="username" rules="required" type="text" placeholder="VD: admin" class="form-control">
					<span class="form-message"></span>
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="form-label">Mật khẩu</label>
				<div class="col-sm-10">
					<input id="password" name="password" rules="required" type="password" placeholder="Nhập mật khẩu" class="form-control">
					<span class="form-message"></span>
				</div>
			</div>
			<div class="txt-right">
				<a href="./index.jsp" style="margin: 10px;">Đăng nhập</a>
			</div>

			<button class="form-submit">Đăng kí</button>
		</form>
	</div>
</div>


<script src="./public/js/validator.js"></script>

<script>
	var form = new Validator('#form-1');
</script>
</body>

</html>
