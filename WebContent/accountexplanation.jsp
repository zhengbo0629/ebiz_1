<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>账号说明</title>
<link rel="stylesheet" href="styles.css" type="text/css" charset="GBK"/>
</head>
<body>


<jsp:include  page="head.jsp" />


<div align="center" >

    <br>
    <br>
    <br>
    <h2>账号说明</h2>
          
			<br><br>
			<div align="left" style="width:50%; ">

<h3>三种账号说明：</h3>
<p>
第一种账号：主账号，可以进行子账号管理，库存管理。 具备发shipping label，发deal，支付，deal 等管理功能。需要填写公司英文名称来作为公司的唯一标志代码。
</p><p>
第二种账号：子账号，子账号必须绑定一个主账号，具有报货，求款，求出货等功能。 需要填写主账号公司名称来进行绑定，也可又主账号升级后，具备管理账号功能，比如发label，对支付进行记录等。
</p><p>
第三种账号：独立账号，具备仓库管理，报货等功能，不能管理子账号，可升级为主账号。
</p><p>
<%
//第四种账号：仓库账号，主要为仓库放入库，出库，对单等开放。
//</p><p>
%>
</div>

             <br>
            <form action="registration.jsp">
                <input type="submit" style="height:36px;width:370px" value="返回用户注册" style="color:#BC8F8F">
            </form>


</div>
</body>
</html>







