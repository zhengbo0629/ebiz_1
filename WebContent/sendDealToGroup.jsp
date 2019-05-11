<%@page import="nameEnum.EbizNurseGroupTypeEnum"%>
<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizStatusEnum"%>
<%@page import="ebizConcept.EbizProduct"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="ebizConcept.EbizUser"%>
<%@page import="dataCenter.EbizSql"%>
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
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>添加或修改产品</h3>
			</div>

			<%
				String idString = request.getParameter("productID");
				EbizProduct product = null;
				String UID = "";
				String UPC = "";
				String ASIN = "";
				String SKU = "";
				String Brand = "";
				String model = "";
				String productName = "";
				String url = "";
				int tickets = 2000;
				int personlimits = 300;
				double weight = 0;
				double length = 0;
				double width = 0;
				double height = 0;
				String status = "";
				double price = 0;
				double promotprice = 0;
				int promotQuantity = 0;
				double warehouseprice = 0;
				double warehousepromotquantity = 0;
				double warehousepromotprice = 0;
				String usernote = "";
				if (idString != null && idString.length() != 0) {
					int productID = Integer.parseInt(idString);
					product = EbizSql.getInstance().findProduct(productID);
				}
				if (product != null) {
					UID = product.UID.toString();
					UPC = product.UPC;
					ASIN = product.Asin;
					SKU = product.SKU;
					Brand = product.brand;
					model = product.model;
					productName = product.productName;
					url = product.uRI;
					tickets = product.tickets;
					personlimits = product.limitPerPerson;
					weight = product.weight;
					length = product.length;
					width = product.width;
					height = product.height;
					status = product.status;
					price = product.price;
					promotprice = product.promotPrice;
					promotQuantity = product.promotQuantity;
					warehouseprice = product.warehousePrice;
					warehousepromotprice = product.warehousePromotePrice;
					warehousepromotquantity = product.warehousePromotQuantity;
					usernote = product.getUserNote();
				}
			%>


			<div align=center>
				<div align=left
					style="width: 99%; float: left; border: 0px solid #d7d7d7; min-height: 400px; font-size: 14px">
					<form name="edit" id="edit" action="SendDealServlet" method="post">
						<br>
						<%
							if (UID.length() > 0) {
						%>
						Product UID: <input style="width: 60px; height: 25px" type="text"
							id="productID" name="productID" value="<%=UID%>"
							readonly="readonly" />&emsp;
						<%
							}
						%>

						Model:&nbsp;<input style="width: 100px; height: 25px" type="text"
							id="model" name="model" value="<%=model%>" placeholder="required"
							<%if (model.length() > 0) {%> readonly="readonly"
							<%}%> />&emsp;Status:&nbsp; <select id="status"
							name="status" style="height: 35px; width: 100px;">
							<option selected="selected" value="0">Please Chose</option>
							<%
								for (EbizStatusEnum statustype : EbizStatusEnum.values()) {

									if (status.equals(statustype.getName())) {
							%>
							<option selected="selected" value="<%=statustype.getName()%>"><%=statustype.getName()%></option>
							<%
								} else {
							%>
							<option value="<%=statustype.getName()%>"><%=statustype.getName()%></option>
							<%
								}
								}
							%>
						</select> <br> Product Name: length less than 250
						<textarea id="productName" name="productName"
							style="background-color: transparent; resize: none;" rows="2"
							cols="58" placeholder="required"><%=productName%></textarea>
						<br> Brand:&nbsp;<input style="width: 120px; height: 25px"
							type="text" id="productBrand" name="productBrand"
							placeholder="required" value="<%=Brand%>" /> &emsp;SKU:&nbsp;<input
							style="width: 120px; height: 25px" type="text" id="productSKU"
							name="productSKU" value="<%=SKU%>" />&emsp;UPC:&nbsp;<input
							style="width: 120px; height: 25px" type="text" id="productUPC"
							name="productUPC" value="<%=UPC%>" />&emsp;ASIN:&nbsp;<input
							style="width: 120px; height: 25px" type="text" id="productASIN"
							name="productASIN" value="<%=ASIN%>" /><br> Web
						Address:&nbsp; <input style="width: 89%; height: 25px" type="text"
							id="webAddress" name="webAddress" value="<%=url%>" /> <br>
						Tickets:&nbsp;<input style="width: 60px; height: 25px"
							type="number" id="productTickets" name="productTickets"
							value="<%=tickets%>" min="0" step="1" />
						&emsp;PersonalLimit:&nbsp;<input style="width: 60px; height: 25px"
							type="number" id="personalLimit" name="personalLimit" min="0"
							step="1" value="<%=personlimits%>" />&emsp; W(LB):&nbsp;<input
							style="width: 60px; height: 25px" type="number"
							id="productWeight" name="productWeight" value="<%=weight%>" />&emsp;L(inch):&nbsp;<input
							style="width: 60px; height: 25px" type="number"
							id="productLength" name="productLength" value="<%=length%>" />&emsp;W(inch):&nbsp;<input
							style="width: 60px; height: 25px" type="number" id="productWidth"
							name="productWidth" value="<%=width%>" />&emsp;H(inch):&nbsp;<input
							style="width: 60px; height: 25px" type="number"
							id="productHeight" name="productHeight" value="<%=height%>" /><br>
						<br>

						<div class="clear"></div>
						<div
							style="width: 40%; float: left; border: 1px solid #CCC; padding: 5px 5px 5px 5px;">
							下单到家里的收货价格： <br>
							<div style="float: left;">
								价格：<br> <input style="width: 80px; height: 25px"
									type="number" id="productPrice" name="productPrice"
									value="<%=price%>" />
							</div>
							<div style="float: left">
								加价收购数量：<br> <input style="width: 80px; height: 25px"
									type="number" id="promotQuantity" name="promotQuantity"
									value="<%=promotQuantity%>" min="0" step="1" />
							</div>
							<div style="float: left">
								加价收购价格：<br> <input style="width: 80px; height: 25px"
									type="number" id="promotPrice" name="promotPrice"
									value="<%=promotprice%>" />
							</div>
						</div>
						<div
							style="width: 40%; float: left; border: 1px solid #CCC; padding: 5px 5px 5px 5px; margin-left: 10px">
							下单到仓库的收货价格： <br>
							<div style="float: left;">
								价格：<br> <input style="width: 80px; height: 25px"
									type="number" id="productWarehousePrice"
									name="productWarehousePrice" value="<%=warehouseprice%>" />
							</div>
							<div style="float: left">
								加价收购数量：<br> <input style="width: 80px; height: 25px"
									type="number" id="warehousePromotQuantity"
									name="warehousePromotQuantity"
									value="<%=warehousepromotquantity%>" min="0" step="1" />
							</div>
							<div style="float: left">
								加价收购价格：<br> <input style="width: 80px; height: 25px"
									type="number" id="warehousePromotPrice"
									name="warehousePromotPrice" value="<%=warehousepromotprice%>" />
							</div>

						</div>
						<div class="clear"></div>

						EmailContent(添加邮件内容) 可以在这里写一些注意事项:<br>
						<textarea id="emailContent" name="emailContent"
							style="background-color: transparent; resize: none;" rows="4"
							cols="58"></textarea>
						<br> <br> 请选择下单地址：
						<div class='container'>
							<input type="checkbox" name="addressCheckbox" value="Home"
								id="homeButton"><label for="Home">&nbsp; :Home</label>&emsp;&emsp;
							<%
								if (company.getAddress1() != null && company.getAddress1().length() > 0) {
							%>

							<input type="checkbox" name="addressCheckbox"
								value="<%=company.getAddress1Name()%>" id="address1"><label
								for="<%=company.getAddress1Name()%>">&nbsp; :<%=company.getAddress1Name()%></label>&emsp;&emsp;

							<%
								}
							%>
							<%
								if (company.getAddress2() != null && company.getAddress2().length() > 0) {
							%>

							<input type="checkbox" name="addressCheckbox"
								value="<%=company.getAddress2Name()%>" id="address2"><label
								for="<%=company.getAddress2Name()%>">&nbsp; :<%=company.getAddress2Name()%></label>&emsp;&emsp;

							<%
								}
							%>
							<%
								if (company.getAddress3() != null && company.getAddress3().length() > 0) {
							%>

							<input type="checkbox" name="addressCheckbox"
								value="<%=company.getAddress3Name()%>" id="address2"><label
								for="<%=company.getAddress3Name()%>">&nbsp; :<%=company.getAddress3Name()%></label>&emsp;

							<%
								}
							%>
						</div>
						<br> <br> 请选择发送群体：
						<div class='container'>
							<%
								for (EbizNurseGroupTypeEnum group : EbizNurseGroupTypeEnum.values()) {
							%>

							<input type="checkbox" name="userGroupCheckbox"
								value="<%=group.getName()%>" id="group<%=group.ordinal()%>"
								<%if (group.ordinal() != 0 && group.ordinal() != 6) {%>
								checked="checked" <%}%> onclick="toggle(this)"><label
								for="<%=group.getName()%>">&nbsp; :<%=group.getChinese()%></label>&emsp;&emsp;
							<%
								}
							%>

						</div>


						<br>
						<div align=center>
							<input type="button" name="submit" value="submit"
								onClick="updatePackage()" />
						</div>

					</form>
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
		function toggle(source) {
			checkboxes = document.getElementsByName("userGroupCheckbox");
			var id = source.id;
			var checked = source.checked;
			if (id === "group0") {
				for (var i = 0, n = checkboxes.length; i < n; i++) {
					if (checkboxes[i].id !== id&&checkboxes[i].id!="group6") {
						checkboxes[i].checked = checked;
					}

				}
			} else if (id === "group1") {

			} else if (id === "group2") {
				for (var i = 0, n = checkboxes.length; i < n; i++) {
					if ((checkboxes[i].id === "group3" || checkboxes[i].id === "group4")
							&& checked) {
						checkboxes[i].checked = checked;
					}

				}
			} else if (id === "group3") {
				for (var i = 0, n = checkboxes.length; i < n; i++) {
					if (checkboxes[i].id === "group4" && checked) {
						checkboxes[i].checked = checked;
					}

				}
			}
		}
		function updatePackage() {

			if (confirm("确认要群发deal吗？")) {
				$("#edit")
						.ajaxSubmit(
								{
									beforeSubmit : function() {
										// alert("我在提交表单之前被调用！");
									},
									success : function(data) {
										alert(data);
										if (data.includes("Update Failed")
												|| data.includes("Send Goup Email Failed")) {

										} else {

											location.href = "allDealSendToNurseManage.jsp";
										}
									}
								});
			} else {

			}

		}
	</script>

</body>
</html>