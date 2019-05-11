<%@page import="java.util.ArrayList"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>
<%@page import="java.util.List"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
<%@page import="ebizConcept.EbizUser"%>
<%@page import="ebizConcept.EbizProduct"%>
<%@page import="ebizConcept.EbizCompany"%>
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
		
		EbizCompany company=(EbizCompany) session.getAttribute("currentCompany");
		List<EbizProduct> productLists = EbizSql.getInstance().readAllActiveAndAliveDealProducs(company.companyName);
		

		//List<EbizProduct> productLists = new ArrayList<EbizProduct>();
	%>
	<jsp:include page="doctorHead.jsp" />



	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>求购/议价</h3>
				<%
					List<String> info = (List<String>) request.getAttribute("info");
					if (info != null) {
						Iterator<String> iter = info.iterator();
						while (iter.hasNext()) {
				%>
				<br>
				<h4>
					<font color="#006699"><%=iter.next()%></font>
				</h4>
				<%
					}
					}
					request.removeAttribute("info");
				%>
			</div>
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">

				<h4 style="color: #ff8000">如还未下单请等到交易确认信息后在下单</h4>

			</div>
			<div align="left" style="padding-bottom: 1px; padding-top: 10px;">

				<h4 style="color: #1e65ea">请从商品list里面选择商品</h4>

			</div>
			<form name="sellOrOBOForm" id="sellOrOBOForm"
				action="SellOrOBOServlet" method="post" onsubmit="return check();">
				<table class="tablewithoutline">
					<tr>
						<td class="alignRight" style="width: 80px">选择商品：</td>
						<td><select onchange="change()" id="product" name="product"
							style="width: 100%">
								<option selected="selected" value="0">Please choose a
									product</option>

								<%
								String value;
								String content;
								if(productLists!=null){
									for (int i = 0; i < productLists.size(); i++) {
										value = productLists.get(i).getUID() + "," + productLists.get(i).productName.replace(",", " ") + ","
												+ productLists.get(i).getModel()+ ","+ productLists.get(i).getBrand();
										content = productLists.get(i).getProductName() + "," + productLists.get(i).getModel();
								%>

								<option value='<%=value%>'><%=content%></option>

								<%
									}}else{
										value="";
										content="";
									}
								%>
						</select></td>
					</tr>
				</table>
				<div align="left" style="padding-bottom: 1px; padding-top: 10px;">

					<h4 style="color: #1e65ea">或者填写商品</h4>

				</div>
				<table class="tablewithoutline">
					<tr>
						<td class="alignRight" style="width: 80px">商品型号：</td>
						<td style="font-size: 16px"><input type="text"
							id="productModel" name="productModel" placeholder="Product Model"
							style="width: 150px" required onchange="resetSelect()" />&emsp;商品品牌：&emsp;<input
							type="text" id="productBrand" name="productBrand"
							placeholder="Product Brand" style="width: 150px" required
							onchange="resetSelect()" />
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">商品名：</td>
						<td style="font-size: 16px"><textarea id="productName"
								name="productName" style="resize: none; font-size: 15px"
								rows="3" cols="100"
								placeholder="Product Name,Name Length Less Than 200 Characters"
								placeholder="Product Name,Name Length Less Than 200 Characters"
								required onchange="resetSelect()"></textarea>
					</tr>
				</table>
				<div align="left" style="padding-bottom: 1px; padding-top: 10px;">

					<h4 style="color: #1e65ea">输入其它信息</h4>

				</div>
				<table class="tablewithoutline">
					<tr>
						<td class="alignRight" style="width: 80px">数量：</td>
						<td style="font-size: 16px"><input type="number"
							id="quantity" name="quantity" placeholder="Your Quantity"
							style="width: 100px" required /> 价格：<input type="number"
							id="price" name="price" placeholder="Your Price"
							style="width: 100px" required /> <label for="orderUnplaced">&nbsp;未下单：</label><input
							id="orderUnplaced" value="orderUnplaced" type="checkbox"
							name="checkbox" onclick="toggle(this)" />&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label
							for="orderPlaced">已下单：</label><input id="orderplaced"
							value="orderPlaced" type="checkbox" name="checkbox"
							onclick="toggle(this)" />&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label
							for="instock">现货：</label><input id="instock" value="instock"
							type="checkbox" name="checkbox" onclick="toggle(this)" /></td>
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">信用卡信息：</td>
						<td><textarea id="CreditCardNumber" name="CreditCardNumber"
								style="resize: none;"
								placeholder="Credit Card Number, New Card Needs Bank Name At Least"
								rows="3" cols="58"></textarea></td>
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
						<td><input id="address" type="text" name="address" value=""
							style="width: 60%" required /></td>
					</tr>
				</table>

				<br>
				<div style="text-align: center;">
					<input type="button" id="submitbutton" name="submitbutton"
						value="submit" onClick="submitPackage()" />
				</div>

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
			if(source==null) return;
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
		function resetSelect(){
			document.getElementById("product").selectedIndex = 0;
			
		}
		function change() {
			var myselect = document.getElementById("product");
			var index = myselect.selectedIndex;

			var vs = myselect.options[index].value;
			if (vs === "0") {
				document.getElementById("productName").value = "";
				document.getElementById("productModel").value = "";
				document.getElementById("productBrand").value = "";
				
			} else {
				var array = vs.split(",");
				var uid = parseInt(array[0]);
				var name = array[1];
				var model = array[2];
				var brand = array[3];

				document.getElementById("productModel").value = model;
				document.getElementById("productName").value = name;
				document.getElementById("productBrand").value = brand;

			}
		}
		function submitPackage(){
			
			var model=document.getElementById("productModel").value;
			if(model.length===0){
				alert("Please Input Product Model");
				return;
			}
			var name=document.getElementById("productName").value;
			if(name.length===0){
				alert("Please Input Product Name");
				return;
			}
			var brand=document.getElementById("productBrand").value;
			if(brand.length===0){
				alert("Please Input Product Brand");
				return;
			}
			var quantity=document.getElementById("quantity").value;
			if(quantity.length===0||quantity<=0){
				alert("Please Check Product Quantity");
				return;
			}
			
			var price=document.getElementById("price").value;
			if(price.length===0||price<=0){
				alert("Please Check Product Price");
				return;
			}

			
			checkboxes = document.getElementsByName("checkbox");
			var checked = false;
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				checked=checked||checkboxes[i].checked;
			}
			if(!checked){
				alert("Please Chose Product Status, UnPlaceed Order, Placed Order or Instock");
				return;
			}
			var address=document.getElementById("address").value;
			if(address.length===0){
				alert("Please Input Product Address");
				return;
			}
			

			$("#sellOrOBOForm").ajaxSubmit({
				beforeSubmit : function() {
					// alert("我在提交表单之前被调用！");
				},
				success : function(data) {
					alert(data);
					if(data.includes("Sucessfully")){
						location.href = "unConfirmedSellOrOBOPack.jsp";
					}else {
						
						
					}
				}
			});
			

		}
	</script>
</body>
</html>