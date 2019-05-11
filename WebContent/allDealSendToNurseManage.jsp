<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
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
		if (user == null || !user.isSelfEmployedDoctor()&&!user.isDoctor()&&!user.getUserPermissions().contains(EbizUserPermissionEnum.ProductManage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany currentCompany=(EbizCompany) session.getAttribute("currentCompany");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div
			id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>群发deal管理(Send Deal To Group)</h3>
			</div>
				<div style="float:right">
					<input onclick="javascript:window.location.href='addOrEditProduct.jsp'" type="button" value="Add A New Product"
						name="addNewProduct"
						style="margin-top: 0px; width: 150px; font-size: 12px; height: 25px; background-color: #ffcebf">
				</div>
								<div style="float:right">
					<input onclick="javascript:window.location.href='allDealSendToNurseManage.jsp'" type="button" value="All Deal"
						name="allProduct"
						style="margin-top: 0px; width: 150px; font-size: 12px; height: 25px; background-color: #ffcebf">
				</div>
				<div style="float:right">
					<input onclick="javascript:window.location.href='activeDealSendToNurseManage.jsp'" type="button" value="Active Deal"
						name="activeProduct"
						style="margin-top: 0px; width: 150px; font-size: 12px; height: 25px; background-color: #ffcebf">
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
					List<EbizProduct> productList = EbizSql.getInstance().readAllNonDeletedProductSet(offset, pagesize, currentCompany.companyName);
				%>
				<table>
					<col width=4%>
					<col width=35%>
					<col width=16%>
					<col width=9%>
					<col width=9%>
					<col width=12%>
					<col width=6%>
					<col width=3%>

					<tr>
						<th>UID</th>
						<th>Model:ASIN<br>ProductName
						</th>
						<th>Brand<br>SKU</th>
						<th style='font-size: 10px; line-height: 12px;'>家里收货价格<br>家里加价数量<br>家里加价价格
						</th>

						<th style='font-size: 10px;line-height: 12px;'>仓库收货价格<br>仓库加价数量<br>仓库加价价格</th>
						<th>Note</th>
						<th>Status</th>
						<th>Update</th>
					</tr>
					<%
							if (productList != null) {
								for (int i = 0; i < productList.size(); i++) {
									EbizProduct row = productList.get(i);
						%>
					<tr>
						<td><%=row.UID%></td>
						<td><%=row.getModel()%>:<%=row.Asin%><br>
						<%
						if(row.uRI!=null&&row.uRI.length()>0){%>
						<a href="<%=row.uRI%>" target="_blank"><%=row.productName%></a>
							
						<%}else{%>
							<%=row.productName%>
						<%}%>
						</td>
						<td><%=row.brand%><br><%=row.SKU%></td>
						<td style='font-size: 10px;line-height: 12px;'><%=row.price%><br><%=row.promotQuantity%><br><%=row.promotPrice%></td>
						<td style='font-size: 10px;line-height: 12px;'><%=row.warehousePrice%><br><%=row.warehousePromotQuantity%><br><%=row.warehousePromotePrice%></td>

						<td><%=row.getUserNote()%></td>
						<td ><%=row.status%></td>

						<td><form action='sendDealToGroup.jsp' method='POST'>
								<input type='hidden' name='productID' value="<%=row.UID%>"/><input
									type='submit' style='width: 80px; font-size: 13px' name='submit-btn'
									value='Send Deal' />
							</form></td>
					</tr>
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
					<a href='allDealSendToNurseManage.jsp?PageNumber=<%=prevpage%>'>PrevPage</a><a>
					</a><a href='allDealSendToNurseManage.jsp?PageNumber=<%=nextpage%>'>NextPage</a>
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