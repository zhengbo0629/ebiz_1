<%@page import="nameEnum.EbizCompanyAddressEnum"%>
<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="ebizConcept.EbizCompany"%>
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
		if (user == null || !user.isSelfEmployedDoctor()&&!user.isDoctor()&& !user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany currentCompany = (EbizCompany) session.getAttribute("currentCompany");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div
			id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>²»Æ¥Åä°ü¹ü(UnMatch Package)</h3>
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
					List<EbizPackage> packages = EbizSql.getInstance().readPackagesForUser(offset, pagesize, user,
							EbizPackageStatusEnum.NumberUnMatch.getColumnName(),EbizPackageStatusEnum.UPCUnMatch.getColumnName(),EbizPackageStatusEnum.UPCNumberUnMatch.getColumnName());
				%>
								<table>
					<col width=5%>
					<col width=40%>
					<col width=1%>
					<col width=13%>
					<col width=13%>
					<col width=16%>
					<col width=6%>
					<col width=6%>
					<tr>
						<th>UID</th>
						<th>Model<br>ProductName
						</th>
						<th>Quantity<br>Price
						</th>

						<th>Address<br>Tracking Number
						</th>
						<th>Report Time<br>Update Time
						</th>
						<th>CreditCard<br>Pay Infor
						</th>
						<th>Status</th>
						<th>Update</th>

					</tr>
					<%
						if (packages != null) {
							for (int i = 0; i < packages.size(); i++) {
								EbizPackage row = packages.get(i);
					%>
					<tr>
						<td><%=row.UID%></td>
						<td><%=row.getModel()%><br><%=row.productName%></td>
						<td><%=row.quantity%><br><%=row.price%></td>
						<%
							String shippingAddress = row.shippingAddress;
									if (EbizCompanyAddressEnum.isCompanyAddress(shippingAddress)) {
										String trackingString = row.trackingNumber.replace("?", "<br>");
						%>
						<td><%=currentCompany.getAddressNameByAddress(row.shippingAddress)%><br><%=trackingString%></td>
						<%
							} else {
										String trackingString = row.trackingNumber.replace("\n", "<br>");
						%>
						<td>Home<br><%=trackingString%></td>
						<%
							}
						%>
						<td style='font-size: 10px'><%=row.createdTime%><br><%=row.updateTime%></td>
						<td><%=row.creditcardNumber%></td>
						<td style='font-size: 10px'><%=row.getStatus()%></td>
						<%
							
							
						
						if (EbizCompanyAddressEnum.isCompanyAddress(row.shippingAddress)) {
							%>
						<td><form action='editWareHousePackage.jsp' method='POST'>
								<input type='hidden' name='targetPackage' value="<%=row.UID%>" /><input
									type='submit' style='width: 50px; font-size: 13px' name='submit-btn'
									value='Update' />
							</form></td>
						<%
								} }	}
							%>
						
						
						
					
				</table>
			</div>
			<%
				int prevpage = pageNumber - 1;
				if (prevpage < 1) {
					prevpage = 1;
				}
				int nextpage = pageNumber + 1;
			%>
			<div style="text-align: right; margin-right: 0px">
				<br>
				<h4>
					<a href='unMatchPac.jsp?PageNumber=<%=prevpage%>'>PrevPage</a><a>
					</a><a href='unMatchPac.jsp?PageNumber=<%=nextpage%>'>NextPage</a>
				</h4>
			</div>


		</div>


	</div>

	<jsp:include page="doctorBody.jsp" />

</body>
</html>