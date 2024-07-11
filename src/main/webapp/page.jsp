<%@page import="Model.Bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
<link rel="stylesheet" href="./public/css/page.css">
<style>
</style>
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
	
<%
	UserBean user = (UserBean) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect("index.jsp");
	}else{
	%>
	<div class="">
		<header>
		<a href="/CK/page.jsp">Xin chào, <%=user.getUsername() %>!</a>
		<a href="UserProfile?uid=<%=user.getUid()%>">Lịch sử</a>
		<a href="Logout">Đăng xuất</a>
		</header>
		<div class="container">
			<div class="main">

				<form action="UploadFile" method="POST"
					enctype="multipart/form-data">
					<div class="txt-center">
						<h1>PDF to Word Converter</h1>
						<h2><a class="history" href="UserProfile?uid=<%=user.getUid()%>">Lịch sử</a></h2>
					</div>
					<div class="spacer"></div>

					<input type="hidden" name="username" value="<%=user.getUsername() %>">
					<div class="form-group">
						<label for="file" class="form-label">Vui lòng chọn file: </label>
						<input name="files" type="file"
							accept="application/pdf" multiple size="3000" class="form-control" onchange="validateFileSize(this)">
					</div>
					<button class="form-submit btnSubmit">Convert</button>
					
					<div>
					<br>
					<br>
					<h2> Lưu ý: </h2>
					<br>
					<ul>
						<li>Có thể convert 1 lúc nhiều file</li>
						<li> 1 file < 50 MB </li>
						<li> Tổng files < = 300 MB  </li>
						<li> Khi "Converting..." thì bạn có thể làm bất cứ gì, không cần đợi.</li>
					</ul>
				</div>
				</form>
				
				
			</div>
		</div>
	</div>
<%} %>

<script type="text/javascript">
  const btnSubmit = document.querySelector('.btnSubmit');
  const fileInput = document.querySelector('input[name="files"]');
  const form = document.querySelector('form[action="UploadFile"]'); 

  btnSubmit.addEventListener('click', (e) => {
    e.preventDefault();
    
    if (fileInput.files.length === 0) {
      alert("Vui lòng chọn một file trước khi chuyển đổi.");
      return;
    }

    btnSubmit.style.color = 'red'; 
    btnSubmit.innerHTML = "Converting...";
    
    form.submit();
  });
</script>


<script>
    function validateFileSize(input) {
        if (input.files[0].size > 50 * 1024 *1024 ) {
            alert("Kích thước file phải <= 50MB");
            input.value = ''; 
        }
    }
</script>

</body>

</html>