<%@page import="nameEnum.EbizUserTypeEnum"%>
<%@page import="ebizConcept.EbizUser"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ע��ɹ�</title>
<link rel="stylesheet"
	href="styles.css?v=<%=System.currentTimeMillis()%>" type="text/css"
	charset="GBK" />
</head>
<body>


	<jsp:include page="head.jsp" />


	<div align="center">

		<br> <br>
		
		<%
			EbizUser user = (EbizUser) session.getAttribute("currentUser");
			if (user == null) {
		%>
		<h2><%="����û��ע�ᣬ����ע��"%></h2>
		<%
			} else if (user.isActive()) {
		%>
		<h2>ע��ɹ�</h2>
		<br> <br>
		<h3><%="Dear: " + user.getUserName()%></h3>
		<br> <br>
		<h3><%="��ϲ���������˺��Ѿ�����"%></h3>
		<%
			} else if (user.getUserType().equals(EbizUserTypeEnum.Doctor.getName())
					|| user.getUserType().equals(EbizUserTypeEnum.SelfEmployedDoctor.getName())) {
		%>
		<h2>ע��ɹ�</h2>
		<br> <br>
		<h3><%="Dear: " + user.getUserName()%></h3>
		<br> <br>
		<h3><%="��ϲ�������Ѿ�ע��ɹ�������ϵ��վ����Ա�����˺�"%></h3>
		<%
			} else if (user.getUserType().equals(EbizUserTypeEnum.Nurse.getName())) {
		%>
		<h2>ע��ɹ�</h2>
		<br> <br>
		<h3><%="Dear: " + user.getUserName()%></h3>
		<br> <br>
		<h3><%="��ϲ�������Ѿ�ע��ɹ�������ϵ���˺Ź���Ա���߹�˾����Ա�����˺�"%></h3>
		<%
			}
			else if (user.getUserType().equals(EbizUserTypeEnum.Oversea_Buyer.getName())) {
				%>
				<h2>ע��ɹ�</h2>
				<br> <br>
				<h3><%="Dear: " + user.getUserName()%></h3>
				<br> <br>
				<h3><%="��ϲ�������Ѿ�ע��ɹ�������ϵ����Ա�����˺�"%></h3>
				<%
					}

			request.setCharacterEncoding("GBK");
		%>

	</div>
	<br> <br><br> <br>

<div align="center">
		<form action="registration.jsp">
			<input type="submit" style="height: 36px; width: 370px" value="ע�����û� "
				style="color:#BC8F8F">
		</form>
	</div>
	
</body>


</html>