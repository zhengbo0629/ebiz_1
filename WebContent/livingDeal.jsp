<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>
<%@page import="java.util.List"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="ebizConcept.EbizUser"%>
<%@page import="ebizConcept.EbizProduct"%>
<%@page import="dataCenter.EbizSql"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; GBK" />
<title>EastEbiz</title>
<link rel="stylesheet"
	href="styles.css?v=<%=System.currentTimeMillis()%>" type="text/css"
	charset="GBK" />
</head>

<body>
	<%
		EbizUser user = (EbizUser) session.getAttribute("currentUser");
	//不是医生也没有权限的人不给看这个网页
		if (user == null || !user.isSelfEmployedDoctor()&&!user.isDoctor()&&!user.getUserPermissions().contains(EbizUserPermissionEnum.LiveDeal.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany company = (EbizCompany) session.getAttribute("currentCompany");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div
			id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>正在收购(Live Deal)</h3>
			</div>
			<div style="width: 100%; overflow: auto;">

				<%
					int pagesize = 50;
					String temp = request.getParameter("PageNumber");
					int pageNumber = 1;
					if (temp != null) {
						int tempnum = Integer.parseInt(temp);
						if (tempnum > 1) {
							pageNumber = tempnum;
						}
					}
					int offset = pagesize * (pageNumber - 1);
					List<EbizProduct> liveDealProducts = EbizSql.getInstance().readAllLiveDealProducs(company.companyName);
				%>
				<table>
					<col width=5%>
					<col width=13%>
					<col width=40%>
					<col width=8%>
					<col width=8%>
					<col width=8%>
					<tr>
						<th>UID</th>
						<th>产品型号<br>Model
						</th>
						<th>产品名称<br>ProductName
						</th>
						<th>收购价格<br>Buy Price
						</th>
						<th>领票<br>Get Tickets
						</th>
						<th>查看网页<br>Check
						</th>


					</tr>
					<%
						if (liveDealProducts != null&&(user.isDoctor()||user.isSelfEmployedDoctor()||user.getUserPermissions().contains(EbizUserPermissionEnum.LiveDeal.getName()))) {
							for (int i = 0; i < liveDealProducts.size(); i++) {
								EbizProduct row = liveDealProducts.get(i);
					%>
					<tr>
						<td><%=row.UID%></td>
						<td><%=row.getModel()%></td>
						<td><a href='<%=row.uRI%>'><%=row.productName%></a></td>
						<td>$<%=row.price%></td>
						<td><input
									type="button" style="width: 80px; font-size: 13px"
									name="submit-btn" onClick="location.href='reportPackage.jsp'" value="领票" /></td>
						<td><input
									type="button" style="width: 80px; font-size: 13px"
									name="submit-btn" onClick="window.open('<%=row.uRI%>')"  value="查看网页" /></td>
					</tr>
					<%}}%>
				</table>
			</div>
		</div>


	</div>

	<jsp:include page="doctorBody.jsp" />

</body>
</html>