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
		if (user == null||!EbizSql.getInstance().isAdministrator(user)) {
			response.sendRedirect("index.jsp");
			return;
		}
		// 上面放两个按钮，要给是激活user，一个是所有user
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div
			id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>主账户管理</h3>
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
					List<EbizPackage> unReceivedPacages = EbizSql.getInstance().readPackagesForUser(offset, pagesize, user,
							EbizPackageStatusEnum.UnReceived.getColumnName());
					List<EbizUser> doctorUsers=EbizSql.getInstance().readActiveDoctorUsers(offset, pagesize);
				%>
				<table>
					<col width=5%>
					<col width=40%>
					<col width=1%>
					<col width=13%>
					<col width=15%>
					<col width=16%>
					<col width=6%>
					<col width=6%>
					<col width=6%>
					<tr>
						<th>UID</th>
						<th>Model<br>ProductName
						</th>
						<th>Quantity<br>Price</th>

						<th>Address<br>Tracking Number
						</th>
						<th>Report Time<br>Update Time
						</th>
						<th>CreditCard<br>Pay Infor
						</th>
						<th>Status</th>
						<th>Update</th>
						<th>Delete</th>
					</tr>
					<%
							if (unReceivedPacages != null) {
								for (int i = 0; i < unReceivedPacages.size(); i++) {
									EbizPackage row = unReceivedPacages.get(i);
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
						<td><%=row.shippingAddress%><br><%=trackingString%></td>
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
								if (EbizCompanyAddressEnum.isCompanyAddress(shippingAddress)) {
							%>
						<td><form action='editWareHousePackage.jsp' method='POST'>
								<input type='hidden' name='unReceivedPackage' value="<%=row.UID%>" /><input
									type='submit' style='width: 50px; font-size: 13px' name='submit-btn'
									value='Update' />
							</form></td>
						<%
								} else {
							%>
						<td><form action='editUnreceivedPackage.jsp' method='POST'>
								<input type='hidden' name='unReceivedPackage' value="<%=row.UID%>" /><input
									type='submit' style='width: 50px; font-size: 13px' name='submit-btn'
									value='Update' />
							</form></td>
						<%
								}
							%>
						<td><form name="deleteForm<%=row.UID%>" id="deleteForm<%=row.UID%>" action='DeletNursePackServlet'  method="post"> 
								<input type='hidden' id='deletPackage' name='deletPackage' value="<%=row.UID%>" /><input
									onclick="deletePack(source)" type='button' style='width: 50px; font-size: 13px' name='submit-btn'
									value='Delete' />
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
					<a href='doctorUserBalanceManage.jsp?PageNumber=<%=prevpage%>'>PrevPage</a><a>
					</a><a href='doctorUserBalanceManage.jsp?PageNumber=<%=nextpage%>'>NextPage</a>
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
	function deletePack(source){
		 var msg = "Are you sure want to delete？\n\n Please confirm！"; 
		 if (confirm(msg)==false){ 
		  return; 
		 }
		$("#".concat(source.form.name)).ajaxSubmit({
			beforeSubmit : function() {
				// alert("我在提交表单之前被调用！");
			},
			success : function(data) {
				alert(data);
				if(data.includes("Sucessfully")){
					location.href = "unReceivedPac.jsp";
				}else {
					
				}
			}
		});
	}
	</script>

</body>
</html>