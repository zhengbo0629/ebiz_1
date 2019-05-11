<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
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

		int packageID = Integer.parseInt(request.getParameter("packageid"));
		EbizPackage pack = EbizSql.getInstance().findPackage(packageID);
		EbizCompany company=(EbizCompany) session.getAttribute("currentCompany");

		//List<EbizProduct> productLists = new ArrayList<EbizProduct>();
	%>
	<jsp:include page="doctorHead.jsp" />



	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>修改包裹信息</h3>
				<br>
			</div>
			<br>
			<h4 style="padding-bottom: 0px">
				Package UID:
				<%=packageID%></h4>
			<br>

			<form name="editPackForm" id="editPackForm"
				action="EditPackByDocotorServlet" method="post" onsubmit="return check();">
				<table class="tablewithoutline">
					<tr>
						<td class="alignRight" style="width: 80px">型号：</td>
						<td style="font-size: 16px"><input type="text"
							id="productModel" name="productModel"
							value="<%=pack.getModel()%>" placeholder="Product Model"
							style="width: 120px" required />&emsp;品牌:<input
							type="text" id="productBrand" name="productBrand"
							value="<%=pack.brand%>" placeholder="Product Brand"
							style="width: 100px" required />&emsp;邮寄状态:<select id="packageStatus" name="packageStatus"
							style="width: 20%;height:30px">
								<option selected="selected" value="0">Choose a
									ship status</option>

								<%
									for (EbizPackageStatusEnum status:EbizPackageStatusEnum.values()) {
										if(pack.getStatus().equals(status.getColumnName())){		
								%>
								<option selected="selected" value="<%=status.getColumnName()%>"><%=status.getColumnName()%></option>
								

								<%
									}else{%>
										
											<option value="<%=status.getColumnName()%>"><%=status.getColumnName()%></option>
										
										
									<%}
									}
								%>
						</select>&emsp;支付状态:<select id="payStatus" name="payStatus"
							style="width: 25%;height:30px" disabled>
								<option selected="selected" value="0">Choose a pay
									status</option>

								<%
									for (EbizPackagePayStatusEnum status:EbizPackagePayStatusEnum.values()) {
										if(pack.getPayStatus().equals(status.getName())){		
								%>
								<option selected="selected" value="<%=status.getName()%>"><%=status.getName()%></option>
								

								<%
									}else{%>
										
											<option value="<%=status.getName()%>"><%=status.getName()%></option>
										
										
									<%}
									}
								%>
						</select>
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">商品名：</td>
						<td style="font-size: 16px"><textarea id="productName"
								name="productName" style="resize: none; font-size: 15px"
								rows="3" cols="150"
								placeholder="Product Name,Name Length Less Than 200 Characters"><%=pack.productName%></textarea></td>
					</tr>
				</table>
				<table class="tablewithoutline">
					<tr>
						<td class="alignRight" style="width: 80px">数量：</td>
						<td style="font-size: 16px"><input type="number"
							id="quantity" name="quantity" placeholder="Your Quantity"
							value="<%=pack.quantity%>" style="width: 100px" required />&emsp; 价格：<input
							type="number" id="price" name="price" value="<%=pack.price%>"
							placeholder="Your Price" style="width: 100px" readonly /> 
					 &emsp;SKU:&nbsp;<input
						style="width: 120px; height:25px "
						type="text" id="productSKU" name="productSKU"
						value="<%=pack.SKU%>"/>&emsp;UPC:&nbsp;<input
						style="width: 120px; height:25px  "
						type="text" id="productUPC" name="productUPC"
						value="<%=pack.UPC%>"/>&emsp;ASIN:&nbsp;<input
						style="width: 120px; height:25px "
						type="text" id="productASIN" name="productASIN"
						value="<%=pack.ASIN%>"/><br>

</td>
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">信用卡信息：</td>
						<td><textarea id="CreditCardNumber" name="CreditCardNumber"
								style="resize: none;" rows="3" cols="58"
								placeholder="Credit Card Number, New Card Needs Bank Name At Least" readonly><%=pack.creditcardNumber%></textarea>
						</td>
					</tr>
					<tr>
						<td class="alignRight">选择地址：</td>
						<td>
							<div class='container'>
								<input onclick="changeToHome('<%=user.getAddress()%>');"
									type="button" value="Home" id="homeButton">


								<% if (company.getAddress1()!=null&&company.getAddress1().length()>0){
										%>

								<input onclick="changeToAddress1()" type="button"
									value="<%=company.getAddress1Name()%>" id="address1">

								<%
									}%>
								<% if (company.getAddress2()!=null&&company.getAddress2().length()>0){
										%>

								<input onclick="changeToAddress2()" type="button"
									value="<%=company.getAddress2Name()%>" id="address2">

								<%
									}%>
								<% if (company.getAddress3()!=null&&company.getAddress3().length()>0){
										%>

								<input onclick="changeToAddress3()" type="button"
									value="<%=company.getAddress3Name()%>" id="address3">

								<%
									}%>
							</div>
						</td>
					</tr>
					<tr>
						<td class="alignRight">或填写地址：</td>
						<td><input id="address" type="text" name="address"
							value="<%=pack.shippingAddress%>" style="width: 99%" required /></td>
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">Tracking：</td>
						<td><textarea id="trackingNumber" name="trackingNumber"
								style="resize: none;" rows="3" cols="58"
								placeholder="Tracking Number"><%=pack.trackingNumber%></textarea>
						</td>
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px"></td>
						<td><textarea id="shipID" name="shipID"
								style="resize: none; display:none;" rows="3" cols="58"
								placeholder="Ship ID"><%=pack.shipID%></textarea>
						</td>
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">Note：</td>
						<td><textarea id="userNote" name="userNote"
								style="resize: none;" rows="3" cols="58"
								placeholder="Note"><%=pack.note%></textarea>
						</td>
					</tr>
				</table>

				<br>
				<div style="text-align: center;">
					<input type="button" id="submitbutton" name="submitbutton"
						value="submit" onClick="submitPackage()" />
				</div>
				<input style="width: 200px" type="hidden" id="packageId"
					name="packageId" value="<%=packageID%>"
					readonly="readonly" />
			</form>

		</div>
	</div>
	<jsp:include page="doctorBody.jsp" />
	<!--jquery需要引入的文件-->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.js"></script>

	<!--ajax提交表单需要引入jquery.form.js-->
	<script type="text/javascript"
		src="http://malsup.github.io/jquery.form.js"></script>

	<script type="text/javascript">
		var subed = false;

		function check() {
			if (subed == false) {
				subed = true;
				return subed;
			} else {
				return false;
				//alert("You request is being processed, Please do not repeat");  
			}

		}
		function toggle(source) {
			checkboxes = document.getElementsByName("checkbox");
			var id = source.id;
			var checked = source.checked;
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				if (checkboxes[i].id !== id && checked) {
					checkboxes[i].checked = false;
				}

			}
		}

		function changeToHome(homestring) {
			document.getElementById("address").value = homestring;
			document.getElementById("address").readOnly = false;

		}
		function changeToAddress1() {
			document.getElementById("address").value = "<%=EbizCompanyAddressEnum.Address1.getName()%>";
			document.getElementById("address").readOnly = true;

		}
		function changeToAddress2() {
			document.getElementById("address").value = "<%=EbizCompanyAddressEnum.Address2.getName()%>";
			document.getElementById("address").readOnly = true;

		}
		function changeToAddress3() {
			document.getElementById("address").value = "<%=EbizCompanyAddressEnum.Address3.getName()%>";
			document.getElementById("address").readOnly = true;

		}
		function submitPackage(){
			
			var model=document.getElementById("productModel").value;
			if(model.length===0){
				alert("Please Input Model");
				return;
			}
			var name=document.getElementById("productName").value;
			if(name.length===0){
				alert("Please Input Name");
				return;
			}
			var brand=document.getElementById("productBrand").value;
			if(brand.length===0){
				alert("Please Input Brand");
				return;
			}
			var quantity=document.getElementById("quantity").value;
			if(quantity.length===0||quantity<=0){
				alert("Please Check Quantity");
				return;
			}
			
			var price=document.getElementById("price").value;
			if(price.length===0||price<=0){
				alert("Please Check Price");
				return;
			}


			

			$("#editPackForm").ajaxSubmit({
				beforeSubmit : function() {
					// alert("我在提交表单之前被调用！");
				},
				success : function(data) {
					alert(data);
					if(data.includes("Update Failed")||data.includes("Nothing Updated")){
						
					}else{
						//history.back(-1);
						location.href = "allPackForCompany.jsp";
					}
				}
			});
			

		}
	</script>
</body>
</html>