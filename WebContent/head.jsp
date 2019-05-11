<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<link rel="stylesheet"
	href="styles.css?v=<%=System.currentTimeMillis()%>" type="text/css"
	charset="GBK" />
</head>
<body>
	<div id="wrapper">
		<div id="sitename">
				<div style="float: left; padding-top: 0px;">
					<h1>
						<a href="index.jsp">EastEbiz</a>
					</h1>
				</div>
				<div style="float: right; padding-top: 10px;">
					<!-- 写点儿什么-->
				</div>
		</div>

		<div class="clear" ></div>
		<div id="nav">
			<ul class="clear">
				<!-- MENU -->
				<li><a href="index.jsp"><span>Home</span></a></li>
				<li><a href="login.jsp"><span>用户登录</span></a></li>
				<li><a href="registration.jsp"><span>用户注册</span></a></li>
				<li><a href="accountexplanation.jsp"><span>使用说明</span></a></li>
				<li><a href="contactinfor.jsp"><span>联系方式</span></a></li>
				<!-- END MENU -->
			</ul>
		</div>
	</div>
</body>
</html>