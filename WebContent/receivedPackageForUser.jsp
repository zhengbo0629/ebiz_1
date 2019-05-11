<%@page import="nameEnum.EbizPackageCheckStatusEnum"%>
<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
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
		if (user == null || !user.isSelfEmployedDoctor() && !user.isDoctor()
				&& !user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany company = (EbizCompany) session.getAttribute("currentCompany");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
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
			List<EbizPackage> packages = EbizSql.getInstance().readAllReceivedPackagesForUser(user, company,offset,
					pagesize);
		%>
		<%
			int prevpage = pageNumber - 1;
			if (prevpage < 1) {
				prevpage = 1;
			}
			int nextpage = pageNumber + 1;
		%>
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3 style="margin-top: 5px;">已接受的包裹(All Received Packages)</h3>
			</div>

			<div id="threeBox">
				<div class="alignleft">

				</div>
				<div class="aligncenter">

				</div>
				<div class="alignright">
					<div style="height: 8px"></div>
					<h5>
						<input onclick="location.href='receivedPackageForUser.jsp';" type="button" value="已经收取包裹"
							name="updateAll"
							style="margin-top: 0px; width: 90px; font-size: 12px; height: 30px; background-color: #ffcebf">
						<input onclick="location.href='receivePackageWeb.jsp';" type="button" value="接收包裹"
							name="updateAll"
							style="margin-top: 0px; width: 90px; font-size: 12px; height: 30px; background-color: #ffcebf">
					
					
					<a
						href='allCurrentCheckingTasksForUser.jsp?PageNumber=<%=prevpage%>'>上一页</a><a>
					</a><a
						href='allCurrentCheckingTasksForUser.jsp?PageNumber=<%=nextpage%>'>下一页</a>

				</h5>
				</div>
			</div>
			<div style="width: 100%; overflow: auto;">


				<table
					style="table-layout: fixed; word-wrap: break-word; color: black">
					<col width=45px>
					<col width=50px>
					<col width=50px>
					<col width=50px>
					<col width=50px>
					<col width=75px>
					<col width=70px>
					<col width=38px>
					<col width=85px>
					<col width=120px>
					<tr>
						<th style="white-space: nowrap;"><input id="checkAll"
							type="checkbox" name="checkAll" onclick="toggle(this)" /><label
							for="checkAll">UID</label></th>
						<th style="line-height: 16px;">型号<br>产品名
						</th>
						<th style="line-height: 16px;">接收员
						</th>
						<th style="line-height: 16px;">收件人
						</th>
						<th style="line-height: 16px;">公司
						</th><th style="line-height: 16px;">接收时间
						</th>
						<th style="font-size: 12px; line-height: 16px;">UPC:
						</th>
						<th>数量
						</th>
						<th style="font-size: 12px; line-height: 16px;">地址
						</th>

						<th style="font-size: 12px; line-height: 16px;">包裹单号
						</th>
					</tr>
					<%
						if (packages != null) {
							for (int i = 0; i < packages.size(); i++) {
								EbizPackage row = packages.get(i);
					%>
					<tr>
						<td style="white-space: nowrap;"><input
							id="check<%=row.UID%>" type="checkbox" name="checkbox"
							value="<%=row.UID%>" /><label for="check<%=row.UID%>"><%=row.UID%></label></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.getModel()%><br><%=row.productName%></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.receiver%></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.recipient%></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.company%></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.createdTime%></td>
						<td style='font-size: 11px; line-height: 13px'><%=row.UPC%></td>
						<td><%=row.quantity%></td>


						<%
							String trackingString = "";
									String shippingAddress = row.shippingAddress;
										trackingString = row.trackingNumber.replace("\n", "<br>");
						%>
						<td
							style='font-size: 12px;line-height:14px'>WareHouse Address</td>

						<td
							style='font-size: 12px; line-height: 15px; padding-top: 5px; padding-bottom: 5px'><%=trackingString%></td>



						<%
							}
							}
						%>
					
				</table>
			</div>

			<div style="text-align: right; margin-right: 0px">
				<br>
				<h4>
					<a
						href='receivedPackageForUser.jsp?PageNumber=<%=prevpage%>'>上一页</a><a>
					</a><a
						href='receivedPackageForUser.jsp?PageNumber=<%=nextpage%>'>下一页</a>
				</h4>
			</div>
		</div>
	</div>

	<jsp:include page="doctorBody.jsp" />
	<!--jquery需要引入的文件-->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.js"></script>

	<!--ajax提交表单需要引入jquery.form.js-->
	<script type="text/javascript"
		src="http://malsup.github.io/jquery.form.js"></script>

	<script type="text/javascript">
	</script>

</body>
</html>