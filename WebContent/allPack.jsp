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
				<h3>所有包裹(All Package)</h3>
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
					List<EbizPackage> shippedPackages = EbizSql.getInstance().readAllPackagesForUser(offset, pagesize, user);
				%>
				<table>
					<col width=35px>
					<col width=150px>
					<col width=38px>
					<col width=80px>
					<col width=70px>
					<col width=90px>
					<col width=60px>
					<tr>
						<th>UID</th>
						<th>Model<br>ProductName
						</th>
						<th>数量<br>价格
						</th>

						<th>报告时间<br>更新时间
						</th>
						<th style="font-size: 12px; line-height: 16px;">地址:邮寄状态:<br>包裹单号:Ship
							Id
						</th>
						<th>支付状态<br>支付信息
						</th>
						<th style='font-size: 10px;line-height: 12px;'>Checked By<br>Labeled By<br>Paid By</th>
					</tr>
					<%
						if (shippedPackages != null) {
							for (int i = 0; i < shippedPackages.size(); i++) {
								EbizPackage row = shippedPackages.get(i);
					%>
					<tr>
						<td><%=row.UID%></td>
						<td><%=row.getModel()%><br><%=row.productName%></td>
						<td><%=row.quantity%><br><%=row.price%></td>

						<td style='font-size: 11px; line-height: 13px'><%=row.createdTime%><br><%=row.updateTime%></td>



						<%
							String shippingAddress = row.shippingAddress;
									if (EbizCompanyAddressEnum.isCompanyAddress(shippingAddress)) {
										String trackingString = row.trackingNumber.replace("?", "<br>");
						%>
						<td
							style='font-size: 12px;line-height:14px	;			
						<%if (row.getStatus().equals(EbizPackageStatusEnum.Complete.getColumnName())) {%>
						background-color:#a1ff64
						<%}%>'><%=currentCompany.getAddressNameByAddress(row.shippingAddress)%><br><%=row.getStatus()%><br><%=trackingString%></td>
						<%
							} else {
										String trackingString = row.trackingNumber.replace("?", "<br>");
										if (row.shipID != null && row.shipID.length() > 0)
											trackingString = trackingString + "<br>" + row.shipID.replace("?", "<br>");
						%>
						<td
							style='font-size: 12px;line-height:14px;
												<%if (row.getStatus().equals(EbizPackageStatusEnum.Complete.getColumnName())) {%>
						background-color:#a1ff64
						<%}%>'>Home<br><%=row.getStatus()%><br><%=trackingString%></td>
						<%
							}
						%>
						<%
							if (row.getPayStatus().equals(EbizPackagePayStatusEnum.Paid.getName())) {
						%>
						<td
							style='font-size: 11px; line-height: 12px; background-color: #a1ff64'><%=row.getPayStatus()%><br><%=row.creditcardNumber%></td>
						<%
							} else {
						%>
						<td style='font-size: 11px; line-height: 12px;'><%=row.getPayStatus()%><br><%=row.creditcardNumber%></td>

						<%
							}
						%>
						<td style='font-size: 10px;line-height: 12px;'><%=row.getChecker()%><br><%=row.getLabeler()%><br><%=row.getPayer()%></td>
						<%
							}
							}
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
					<a href='allPack.jsp?PageNumber=<%=prevpage%>'>PrevPage</a><a>
					</a><a href='allPack.jsp?PageNumber=<%=nextpage%>'>NextPage</a>
				</h4>
			</div>


		</div>


	</div>

	<jsp:include page="doctorBody.jsp" />

</body>
</html>