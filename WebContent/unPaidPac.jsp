<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
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
	if (user == null || !user.isSelfEmployedDoctor()&&!user.isDoctor()&&!user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())) {
		response.sendRedirect("index.jsp");
		return;
	}
	EbizCompany currentCompany = (EbizCompany) session.getAttribute("currentCompany");
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
			List<EbizPackage> unReceivedPacages = EbizSql.getInstance().readUnpaidPackagesForUser(offset, pagesize,
					user);
			int prevpage = pageNumber - 1;
			if (prevpage < 1) {
				prevpage = 1;
			}
			int nextpage = pageNumber + 1;
		%>

		<div
			id="wrapperContent">

			<div id="threeBox">
				<div class="alignleft">
					<input onclick="updateAllSelected()" type="button" value="批量更新"
						name="updateAll"
						style="margin-top: 0px; width: 75px; font-size: 12px; height: 25px; background-color: #ffcebf">
				</div>
				<div class="aligncenter">
					<h3>未支付的包裹(UnPaid)</h3>
				</div>
				<div class="alignright">
					<div style="height: 8px"></div>
					<h5>
						<a href="unPaidPac.jsp?PageNumber=<%=prevpage%>">PrevPage</a><a>
						</a><a href="unPaidPac.jsp?PageNumber=<%=nextpage%>">NextPage</a>
					</h5>
				</div>
			</div>
			<div style="width: 100%; overflow: auto;">

				<table>
					<col width=5%>
					<col width=40%>
					<col width=1%>
					<col width=3%>
					<col width=13%>
					<col width=16%>
					<col width=6%>
					<col width=6%>

					<tr>
						<th style="white-space: nowrap;"><input id="checkAll"
							type="checkbox" name="checkAll" onclick="toggle(this)" /><label
							for="checkAll">UID</label></th>
						<th>Model<br>ProductName
						</th>
						<th>Quantity<br>Price
						</th>
						<th>Address</th>
						<th>Report Time<br>Update Time
						</th>
						<th>CreditCard<br>Pay Infor
						</th>
						<th>Status</th>
						<th>Update</th>

					</tr>
					<%
						if (unReceivedPacages != null) {
							for (int i = 0; i < unReceivedPacages.size(); i++) {
								EbizPackage row = unReceivedPacages.get(i);
					%>
					<tr>
						<td style="white-space: nowrap;"><input
							id="check<%=row.UID%>" type="checkbox" name="checkbox"
							value="<%=row.UID%>,<%=row.quantity%>,<%=row.price%>,<%=row.creditcardNumber%>" /><label for="check<%=row.UID%>"><%=row.UID%></label></td>

						<td><%=row.getModel()%><br><%=row.productName%></td>
						<td><%=row.quantity%><br><%=row.price%></td>
						<%
							String shippingAddress = row.shippingAddress;
									if (EbizCompanyAddressEnum.isCompanyAddress(shippingAddress)) {
										String trackingString = row.trackingNumber.replace("?", "<br>");
						%>
						<td><%=currentCompany.getAddressNameByAddress(row.shippingAddress)%></td>
						<%
							} else {
										String trackingString = row.trackingNumber.replace("\n", "<br>");
						%>

						<td>Home<br><%=trackingString%></td>
						<%
							}
						%>

						<td style="font-size: 10px"><%=row.createdTime%><br><%=row.updateTime%></td>
						<td><%=row.creditcardNumber%></td>
						<td style="font-size: 10px"><%=row.getStatus()%></td>


						<td><form action="updateCreditCard.jsp" method="POST">
								<input type="hidden" name="unPaidPackage" value="<%=row.UID%>" /><input
									type="submit" style="width: 50px; font-size: 13px"
									name="submit-btn" value="Update" />
							</form></td>

					</tr>
					<%
						}
						}
					%>
				</table>

			</div>

			<form name="edit" id="edit" action="updateCreditCardForPackages.jsp"
				method="post">

				<input type="hidden" name="unPaidPackagesUid" id="unPaidPackagesUid"
					value="" />	
			</form>


			<div id="threeBox">
				<div class="alignleft"></div>
				<div class="aligncenter">
					<input onclick="updateAllSelected()" type="button" value="批量更新"
						name="updateAll" style="height: 50px; background-color: #ffcebf">
				</div>
				<div class="alignright">
					<h4>
						<a href="unPaidPac.jsp?PageNumber=<%=prevpage%>">PrevPage</a><a>
						</a><a href="unPaidPac.jsp?PageNumber=<%=nextpage%>">NextPage</a>
					</h4>
				</div>
			</div>
			<div class="clear" style="height: 50px;"></div>
		</div>
	</div>

	<jsp:include page="doctorBody.jsp" />
	<!--jquery需要引入的文件-->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.js"></script>

	<!--ajax提交表单需要引入jquery.form.js-->
	<script type="text/javascript"
		src="http://malsup.github.io/jquery.form.js"></script>

	<script type="text/javascript">
		function toggle(source) {
			checkboxes = document.getElementsByName("checkbox");
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				checkboxes[i].checked = source.checked;
			}
		}

		function updateAllSelected() {
		
			
			checkboxes = document.getElementsByName("checkbox");
			
			var counter=0;
			var message = "";
			var tototalValue=0.0;
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				if (checkboxes[i].checked) {
					message = message + checkboxes[i].value + ";";
					counter=counter+1;
				}
			}
			if(counter==0){
				alert("Please chose at least one package");
				return;
			}
			
			document.getElementById("unPaidPackagesUid").value =message;
			
			document.getElementById("edit").submit();
			return;
		}
	</script>
</body>
</html>