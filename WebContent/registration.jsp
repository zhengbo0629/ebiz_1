<%@page import="ebizConcept.EbizUser"%>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>��¼����</title>
<link rel="stylesheet" href="styles.css?v=<%= System.currentTimeMillis()%>" type="text/css" charset="GBK"/>
</head>
<body>


<jsp:include  page="head.jsp" />


<div align="center">

    <br>
    <br>
    <h2>ע��</h2>
    
    <%
EbizUser user=(EbizUser)session.getAttribute("currentUser");
if(user!=null&&user.isActive()){
	response.sendRedirect("loginsuccess.jsp");
}
//if(user!=null&&!user.isActive()){
//	response.sendRedirect("registrationsuccess.jsp");
//}
request.setCharacterEncoding("GBK");

%>

    
            
			<br><br>
			<form action="doctorregistration.jsp">
                <input type="submit" style="height:36px;width:370px;color:black" value="���˺�ע��" >
            </form>
            <br><br>
             <form action="nurseregistration.jsp">
                <input type="submit" style="height:36px;width:370px;color:black" value="���˺�ע��">
            </form>
			<br><br>
            <form action="selfemployedregistration.jsp">
                <input type="submit" style="height:36px;width:370px;color:black" value="�����˺�ע��">
            </form>
            <br><br>
            <%
            /**
            <form action="warehouseregistration.jsp">
                <input type="submit" style="visibility: hidden;height:36px;width:370px;color:black" value="�ֿ��˺�ע��">
            </form>
            <br><br>
            <form action="buyerregistration.jsp">
            <input type="submit" style="visibility: hidden;height:36px;width:370px;color:black" value="����˺�ע��">
        	</form>
        	<br><br>
        **/
             %>
            <form action="accountexplanation.jsp">
                <input type="submit" style="height:36px;width:370px;color:black" value="�˺�˵��">
            </form>
            
</div>
</body>
</html>