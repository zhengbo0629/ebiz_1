<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="ebizConcept.EbizUser"%>
<%@page import="dataCenter.EbizSql"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
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
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div
			id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>更新/修改包裹</h3>
			</div>

			<%
				int packageID = Integer.parseInt(request.getParameter("targetPackage"));
				//获取当前包裹对象
				EbizPackage pack = EbizSql.getInstance().findPackage(packageID);
				String temp="";//trackingnumber 
				if(pack.trackingNumber!=null){
					temp=pack.trackingNumber;
				}
				//把字符串转化为数组
				String[] trackingStrings=temp.split("\\?");
				int row=5;
				if(trackingStrings.length>5){
					row=trackingStrings.length;
				}
				
				
				
				 if(pack.getStatus().equals(EbizPackageStatusEnum.Shipped.getColumnName())){
					
			%><div align="center" style="padding-bottom: 1px; padding-top: 10px;">
			<h4 style="color:blue;">只能修改 tracking 和信用卡信息</h4>
			</div>
			<%
				 }
			%>
			
			<div class="form" style="width: 43%; margin-left: 1%;margin-right: 2%; float: left;">
				<form name="edit" id="edit" action="EditWareHouseTrackingServlet"
					method="post">
					<br>
					<h4 style="padding-bottom: 0px">
						Package UID:
						<%=packageID%></h4>
					<br>
					<h4 style="padding-bottom: 0px">Product Name:</h4>
					<textarea id="productName" name="productName"
						style="border: none; background-color: transparent; resize: none;"
						rows="4" cols="58" readonly><%=pack.productName%></textarea>
					<br> <br>
					<h4 style="padding-bottom: 0px">Shipping Address:</h4>
					<input
						style="width: 100%; border: none; background-color: transparent;"
						type="text" id="shippingAddress" name="shippingAddress"
						value="<%=pack.shippingAddress%>" readonly="readonly" /> <br>
					<br>
					<h4 style="padding-bottom: 0px">Total Quantity:</h4>
					<input style="width: 100%;" type="number" id="quantity"
						name="quantity" value="<%=pack.quantity%>" onchange="quantityChange()" <% if(pack.getStatus().equals(EbizPackageStatusEnum.Shipped.getColumnName())){
						%>
						 readonly="readonly" /> <%}%><br> <br>
					<h4 style="padding-bottom: 0px">CreditCard Information:</h4>
					<textarea id="creditcard" name="creditcard" style="resize: none;"
						rows="8" cols="58"><%=pack.creditcardNumber%></textarea>
					<br> <input style="width: 200px" type="hidden"
						id="trackingnumber" name="trackingnumber"
						value="<%=pack.trackingNumber%>" readonly="readonly" /> <input
						style="width: 200px" type="hidden" id="packageId" name="packageId"
						value="<%=packageID%>" readonly="readonly" /> <input
						style="width: 200px" type="hidden" id="totalQuantity"
						name="totalQuantity" value="" readonly="readonly" /> <input
						style="width: 200px" type="hidden" id="basePrice" name="basePrice"
						value="<%=pack.basePrice%>" readonly="readonly" /> <input
						style="width: 200px" type="hidden" id="promPrice" name="promPrice"
						value="<%=pack.promPrice%>" readonly="readonly" /> <input
						style="width: 200px" type="hidden" id="promQuantity"
						name="promQuantity" value="<%=pack.promQuantity%>"
						readonly="readonly" />
						
						
					<div style="text-align: center;">
						<input type="button" id="InStock" name="InStock" value="submit"
							onClick="checkboxprocess()" />
					</div>
				</form>
			</div>
			<div id=trackingTableDiv
				style="width: 53%; font-size: 14px; float: left; line-height: 20px margin-top: 40px;">
				<table id="trackingTable">
					<tr>
						<th><h4 style="padding-bottom: 0px">Row</h4></th>
						<th><h4 style="padding-bottom: 0px">Tracking Number</h4></th>
						<th><h4 style="padding-bottom: 0px">Quantity</h4></th>
					</tr>
					<%
						for (int i = 1; i <= row; i++) {
					%>
					<tr>
						<TD><%=i%></TD>
						<TD><input
							style="width: 97%; vertical-align: middle; height: 30px; margin-bottom: 0px; margin-top: 0px; font-size: 12px;"
							type="text" name="Tracking<%=i%>" id="Tracking<%=i%>"
							placeholder="Tracing Number <%=i%>" 
							<%if((i-1)<trackingStrings.length&&trackingStrings[i-1].contains("_")){%>
								value="<%=trackingStrings[i-1].split("_")[0]%>"
							<%}%>
							/></TD>
						<TD><input
							style="width: 97%; vertical-align: middle; height: 30px; margin-bottom: 0px; margin-top: 0px; font-size: 12px;"
							type="number" name="Quantity<%=i%>" id="Quantity<%=i%>"
							placeholder="Quantity <%=i%>"
							<%if((i-1)<trackingStrings.length&&trackingStrings[i-1].contains("_")){%>
								value="<%=trackingStrings[i-1].split("_")[1]%>"
							<%}%>/></TD>
					</tr>
					<%
						}
					%>
				</table>
				<div style="text-align: center;">
					<input style="width: 200px" type="button" id="addRows"
						name="moreRow" value="More Rows" onClick="addRow()" />
				</div>
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
		function quantityChange() {
			var oldQuantity = parseInt(document.getElementById("quantity").defaultValue);
			var newQuantity = parseInt(document.getElementById("quantity").value);
			if (newQuantity == 0) {
				document.getElementById("quantity").value = oldQuantity;
				alert("You can not adjust to 0, please delete this package if you do not any units");
				return;
			}
			if (newQuantity > oldQuantity) {
				document.getElementById("quantity").value = oldQuantity;
				alert("Please Contact the Administrator to increase the Total Quantity");
				return;
			}
		}

		function checkboxprocess() {
			var totalQuantity = parseInt("0");
			var table = document.getElementById("trackingTable");
			var rowCount = table.rows.length;
			var trackingString = "";
			for (i = 1; i < rowCount; i++) {
				var tracking = document.getElementById("Tracking" + i).value;
				var quantityString = document.getElementById("Quantity" + i).value;

				if (tracking.length <= 5 && quantityString.length != 0) {
					alert("tracking " + i + " is not good, Please check");
					return;
				}
				if (tracking.length != 0 && quantityString.length == 0) {
					alert("Quantity " + i + " is not good, Please check");
					return;
				}
				if (quantityString.length != 0 && parseInt(quantityString) == 0) {
					alert("Quantity " + i + " is not good, Please check");
					return;
				}
				if (tracking.length == 0 && quantityString.length == 0) {
					continue;
				}
				var quantity = parseInt(quantityString);
				trackingString = trackingString
						+ tracking.replace(/[^A-Za-z0-9]/g, '') + "_"
						+ quantityString + "?";
				totalQuantity = totalQuantity + quantity;
			}
			//document.write(totalQuantity);
			var inputQuantity = parseInt(document.getElementById("quantity").value);
			if (totalQuantity != inputQuantity && totalQuantity != 0) {
				alert("Quantity in all packages does not match the Total Quantity, Please check");
				return;
			}
			trackingString = trackingString.substring(0,
					trackingString.length - 1);
			document.getElementById("trackingnumber").value = trackingString;
			document.getElementById("totalQuantity").value = totalQuantity;

			$("#edit").ajaxSubmit({
				beforeSubmit : function() {
					// alert("我在提交表单之前被调用！");
				},
				success : function(data) {
					alert(data);
					if(data.includes("Updated Sucessfully")||data.includes("Nothing Updated")){
					 	location.href = "shippedPack.jsp";
					}

				}
			});
		}

		function addRow() {
			for (var i = 0; i < 5; i++) {
				var table = document.getElementById("trackingTable");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				var cell1 = row.insertCell(0);
				cell1.innerHTML = rowCount;
				var cell2 = row.insertCell(1);
				var element1 = document.createElement("input");
				element1.type = "text";
				element1.name = "Tracking" + rowCount;
				element1.id = "Tracking" + rowCount;
				element1.placeholder = "Tracing Number " + rowCount;
				element1.style = "width: 97%; vertical-align:middle; height:30px; margin-bottom: 0px; margin-top: 0px;font-size: 12px;";
				cell2.appendChild(element1);
				var cell3 = row.insertCell(2);
				var element2 = document.createElement("input");
				element2.type = "number";
				element2.name = "Quantity" + rowCount;
				element2.id = "Quantity" + rowCount;
				element2.placeholder = "Quantity " + rowCount;
				element2.style = "width: 95%; vertical-align:middle; height:30px; margin-bottom: 0px; margin-top: 0px;font-size: 12px;";
				cell3.appendChild(element2);
			}
		}
		function deleteRow() {
			try {
				var table = document.getElementById("trackingTable");
				var rowCount = table.rows.length;

				for (var i = 0; i < rowCount; i++) {
					var row = table.rows[i];
					var chkbox = row.cells[0].childNodes[0];
					if (null != chkbox && true == chkbox.checked) {
						table.deleteRow(i);
						rowCount--;
						i--;
					}

				}
			} catch (e) {
				alert(e);
			}
		}
	</script>

</body>
</html>