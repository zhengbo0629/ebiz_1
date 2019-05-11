<%@page import="ebizConcept.EbizUser" language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="db" class="dataCenter.EbizSql" scope="page" />
<%

    String username=(String)request.getParameter("username");
    String password=(String)request.getParameter("password");


    EbizUser user=db.findUser(username);
    if(user==null){
        out.print("<script language='javaScript'> alert('账号错误——else');</script>");
        response.setHeader("refresh", "0;url=login.jsp");
    }else if(user.passWord.equals(password)){
    	 response.sendRedirect("loginsuccess.jsp");
    }else{
        out.print("<script language='javaScript'> alert('密码错误');</script>");
        response.setHeader("refresh", "0;url=login.jsp");
    }
    
%>
</body>
</html>