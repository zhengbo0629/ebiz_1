<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="ebizConcept.EbizUser"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>登录界面</title>
<link rel="stylesheet"
	href="styles.css?v=<%= System.currentTimeMillis()%>" type="text/css"
	charset="GBK" />
</head>
<body>


	<jsp:include page="head.jsp" />


	<div align="center">

		<br> <br>
		<h2>登录</h2>
<%
EbizUser user=(EbizUser)session.getAttribute("currentUser");
if(user!=null&&user.isActive()){
	

	response.sendRedirect("loginsuccess.jsp");
}
request.setCharacterEncoding("GBK");

%>
<%
List<String> info=(List<String>)request.getAttribute("info");
if(info!=null){
	Iterator<String> iter=info.iterator();
	while(iter.hasNext()){
%>
		<h4><%=iter.next()%></h4>
<% 
}
}
request.removeAttribute("info");
%>

		<form id="login" name="loginForm" action="LoginServlet" method="post">
			<table class="tablewithoutline"
				style="border-collapse: separate; border-spacing: 0px 10px; width: 390px; font-family: Arial, Helvetica, sans-serif; font-size: 32px; text-align: center;">
				<tr>
					<td style="padding: 0px 0px;"><h3>账号：</h3></td>
					<td style="padding: 0px 0px;"><input type="text"
						style="height: 30px; width: 300px" name="username"></td>
				</tr>

				<tr>

					<td style="padding: 0px 0px;"><h3>密码：</h3></td>
					<td style="padding: 0px 0px;"><input type="password"
						style="height: 30px; width: 300px" name="password"></td>
				</tr>
			</table>
			<br> <input type="submit" style="height: 36px; width: 370px"
				value="登录" style="color:#BC8F8F">
		</form>
		<br> 尚未注册？ <br>
		<form action="registration.jsp">
			<input type="submit" style="height: 36px; width: 370px" value="用户注册"
				style="color:#BC8F8F">
		</form>
	</div>
</body>
</html>