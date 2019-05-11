<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; GBK" />
<title>EastEbiz</title>
<link rel="stylesheet" href="styles.css?v=<%= System.currentTimeMillis()%>" type="text/css" charset="GBK"/>
</head>

<body>

<jsp:include  page="head.jsp" />



	<div align="center">
		<br><br><br><br><br>
		<h1>Welcome To EastEbiz</h1>
			<br>
			<br>
			<br>
		<form action="" method="post" name="home">
			<button type="button" style="height:36px;width:300px; font-size:18px" name="用户登陆" onclick="location.href='/UserManager/login.jsp';" >用户登录</button>
			<br>
			<br>
			<br>
			<button type="button" style="height:36px;width:300px; font-size:18px" name="用户注册" onclick="location.href='/UserManager/registration.jsp';">用户注册</button>
		</form>
	</div>
</body>
</html>