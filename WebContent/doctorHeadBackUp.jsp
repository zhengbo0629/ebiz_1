<%@page import="nameEnum.EbizUserTypeEnum"%>
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
		//if (user==null){
		//	response.sendRedirect("index.jsp");
		//	return;
		//}
	%>
	<div id="wrapper">
		<div id="sitename">
			<div style="height: 53px; margin: 0;">

				<div style="float: left; padding-top: 0px;">
					<h1>
						<a href="index.jsp">EastEbiz</a>
					</h1>
				</div>
				<div style="float: right; padding-top: 12px;">
					<div style="font-size: 16px;">
						<a href="userprofile.jsp" style=""> 欢迎您！
						<%if (user!=null){%> 
						<%=user.getUserName()+"("+user.companyName+")"%> 
						<%}%>
						</a>,<a href="logout.jsp" style="">退出</a> | <a
							href="accountSetting.jsp">账户设置</a> |


						<%if (user!=null&&user.getUserType().equals(EbizUserTypeEnum.Doctor.getName())){%>
								
						<a href="companyAccountExplanation.jsp">公司账户设置</a>|
						<%}%>
							
							
						<a href="doctorAccountExplanation.jsp">用户手册</a>
					</div>
					<div style="font-size: 14px; color: #666666; padding-top: 3px;">
						<%if (session.getAttribute("payTimeString") != null) {%>
						Next Payment Day:<%=session.getAttribute("payTimeString")%>
						<%}%>
							
						

					</div>
				</div>
			</div>
		</div>


		<div class="clear"></div>
		<div
			style="width: 100%; height: 3px; background-color: #c90506; float: left">
		</div>
		<div id="nav">
			<ul class="clear">
				<!-- MENU -->
				<li><a href="userManager.jsp"><span>子账户管理</span></a></li>
				<li><a href="productManager.jsp"><span>产品管理</span></a></li>
				<li><a href="packageManager.jsp"><span>库存管理</span></a></li>
				<li><a href="sendLabel.jsp"><span>发label</span></a></li>
				<li><a href="payPackage.jsp"><span>支付</span></a></li>
				<li><a href="NeedProduct.jsp"><span>求购</span></a></li>
				<li><a href="dealManager.jsp"><span>deal管理</span></a></li>
				<li><a href="doctorAccountExplanation.jsp"><span>使用说明</span></a></li>

				<!-- END MENU -->
			</ul>
		</div>
	</div>
</body>
</html>
