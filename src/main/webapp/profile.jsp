<%@ page import="_CONSTAINT.CONSTAINT"%>
<%@ page import="Model.Bean.UserBean"%>
<%@ page import="Model.Bean.FileBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Profiles</title>
<link rel="stylesheet" href="./public/css/profile.css">
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
	ArrayList<FileBean> files = (ArrayList<FileBean>) request.getSession().getAttribute("Files");
	//ArrayList<FileBean> files = (ArrayList<FileBean>) request.getAttribute("Files");

	if (user != null) {
	%>
	<div class="">
		<header>
			<a href="/CK/page.jsp">Home</a> <a href="#">Xin chào, <%=user.getUsername()%></a>
			<a href="UserProfile?uid=<%=user.getUid()%>">Lịch sử</a> <a
				href="Logout">Đăng xuất</a>
		</header>
		<div class="container">
			<div class="main">
				<div class="projects">
					<div class="sub-head">
						<h2>Danh sách file:</h2>
						<button class="reset-link">
							<h2>
								<a href="UserProfile?uid=<%=user.getUid()%>">Reset</a>
							</h2>
						</button>
					</div>
					<div style="max-height: 60vh; overflow-y: auto;"> 
						<table style="border: 3px; ">
							<thead>
								<tr>
									<td class="id"><b>ID</b></td>
									<td class="name"><b>File name</b></td>
									<td class="download"><b>Download</b></td>
									<td>#</td>
								</tr>
							</thead>
							<tbody>

								<%
							if (files != null && !files.isEmpty()) {
								for (int i = 0; i < files.size(); i++) {
							%>
								<tr>
									<td><%=i + 1%></td>
									<td><%=files.get(i).getFname()%></td>
									<%
								int fstatus = files.get(i).getFstatus();
								String link = "";
								String status = "";

								switch (fstatus) {
								case CONSTAINT.PROCESSING:
									status = "Đang xử lý...";
									link= "#";
									break;
								case CONSTAINT.CONVERT_ERROR:
									status = "Convert error!";
									link = "ShowErrorServlet?errorCode=" + CONSTAINT.CONVERT_ERROR + "&uid=" + user.getUid();
									break;
								case CONSTAINT.SUCCESS:
									status = "Download";
									link = "DownloadFileServlet?fid=" + files.get(i).getFid();
									break;
								case CONSTAINT.UPLOAD_ERROR:
									status = "Upload error!";
									link = "ShowErrorServlet?errorCode=" + CONSTAINT.UPLOAD_ERROR + "&uid=" + user.getUid();
									break;
								default:
									link = "ShowErrorServlet?errorCode=4&uid=" + user.getUid();
									break;
								}
								%>
									<%if(fstatus == 1 || fstatus == 3) {%>
									<td><a style="color: red;" href="<%=link%>"> <i><%=status%></i>
									</a></td>
									<%}else if(fstatus == 0){  %>
									<td><a style="color: green;" href="<%=link%>"> <i><%=status%></i>
									</a></td>
									<% }else{  %>
									<td><a style="color: blue;" href="<%=link%>"> <i><%=status%></i>
									</a></td>
									<% }%>
									<td><a
										href="DeleteFile?uid=<%=user.getUid()%>&fid=<%=files.get(i).getFid()%>"
										onclick="return confirm('Bạn có chắc chắn muốn xóa file này?');">Delete</a></td>
								</tr>
								<%
							}
							} else {
							%>
								<tr>
									<td>Không có dữ liệu</td>
								</tr>
								<%
							}
							%>
							</tbody>
						</table>
					</div>


					<br>
					<br>
					<div>
						<h2>Lưu ý:</h2>
						<br>
						<ul>
							<li>Nếu chưa thấy danh sách file được cập nhật ngay thì chọn nút Reset</li>
							<li>File khi tải về, nếu máy không tự định dạng là file .doc
								thì bạn tự đổi đuôi nó lại là .doc</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
		} else {
			response.sendRedirect("index.jsp");
	
		}
		%>

</body>

</html>
