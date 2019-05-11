<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="ebizConcept.EbizUser"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; GBK" />
<title>Insert title here</title>
<link rel="stylesheet"
	href="styles.css?v=<%=System.currentTimeMillis()%>" type="text/css"
	charset="GBK" />
</head>

<body>
	<%
		EbizUser user = (EbizUser) session.getAttribute("currentUser");
		if (user == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany currentCompany=(EbizCompany) session.getAttribute("currentCompany");
	%>
	<div id="wrapper">
		<div id="sitename">

				<div style="float: left; padding-top: 0px;">
					<h1>
						<a href="index.jsp">EastEbiz</a>
					</h1>
				</div>
				<div style="float: right; padding-top: 10px;">
					<div style="font-size: 16px;">
						<a href="userAccountSetting.jsp" style=""> 欢迎您！
						<%if (user!=null){%> 
						<%=user.getUserName()+"("+user.companyName+")"%> 
						<%}%>
						</a>,<a href="logout.jsp" style="">退出</a> | <a
							href="userAccountSetting.jsp">账户设置</a> |


						<%
						if (user!=null&&user.getUserType().equals(EbizUserTypeEnum.Doctor.getName())){%>
								
						<a href="companyAccountSetting.jsp">公司账户设置</a>|
						<%}%>
							
							
						<a href="dnusermanual.jsp">用户手册</a>
					</div>
					<div style="font-size: 14px; color: #666666; padding-top: 3px;">
						<%if (currentCompany.getPayYear()!=0) {%>
						<%=currentCompany.getPayTimeString()%>
						<%}%>
					</div>
			</div>
		</div>


		<div class="clear"></div>
		<div
			style="width: 100%; height: 1px; background-color: #1e4a60; float: left">
		</div>

	</div>
</body>
</html>
