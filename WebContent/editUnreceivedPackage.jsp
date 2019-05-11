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
				<h3>更新包裹</h3>
			</div>

			<%
				int unReceivedPackageID = Integer.parseInt(request.getParameter("targetPackage"));
				EbizPackage pack = EbizSql.getInstance().findPackage(unReceivedPackageID);
			%>


			<div align=center>
			<div align=left style="width: 50%; float: center; border: 0px solid #d7d7d7; min-height: 400px;">
		<form name="edit" id="edit" action="EditUnreceivedPackServlet" method="post">
					<br>
					<h4 style="padding-bottom: 0px">
						Package UID:
						<%=unReceivedPackageID%></h4>
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
						name="quantity" value="<%=pack.quantity%>"
						onchange="quantityChange()" /> <br> <br>
					<h4 style="padding-bottom: 0px">CreditCard Information:</h4>
					<textarea id="creditcard" name="creditcard" style="resize: none;"
						rows="8" cols="58"><%=pack.creditcardNumber%></textarea><br><br>
			<input style="width: 200px"
				type="hidden" id="basePrice" name="basePrice"
				value="<%=pack.basePrice%>"  readonly="readonly" />
			<input style="width: 200px"
				type="hidden" id="promPrice" name="promPrice"
				value="<%=pack.promPrice%>"  readonly="readonly" />
			<input style="width: 200px"
				type="hidden" id="promQuantity" name="promQuantity"
				value="<%=pack.promQuantity%>"  readonly="readonly" />	
			<input
				style="width: 200px" type="hidden" id="packageId" name="packageId"
				value="<%=unReceivedPackageID%>" readonly="readonly" />
				

		<label style="font-size:18px" for="InStock"> InStock?</label> <input type="checkBox" id="InStock" name="InStock" value="InStock" />

		 <br>
			<br><div align=center> <input type="button" name="submit" value="submit" onClick="updatePackage()" /></div>

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

	function quantityChange(){
		var oldQuantity=parseInt(document.getElementById("quantity").defaultValue);
		var newQuantity=parseInt(document.getElementById("quantity").value);
		if(newQuantity>oldQuantity){
			document.getElementById("quantity").value=oldQuantity;
			alert("Please Contact the Administrator to increase the Total Quantity");
			return;
		}
		if(newQuantity==0){
			document.getElementById("quantity").value=oldQuantity;
			alert("Can not change quantity to 0");
			return;	
		}
	}
	function updatePackage(){

	$("#edit").ajaxSubmit({
		beforeSubmit : function() {
			// alert("我在提交表单之前被调用！");
		},
		success : function(data) {
			var isChecked = document.getElementById('InStock').checked; 
			alert(data);
			if(data.includes("Nothing Updated")){
				
			}else if(isChecked){
				
				location.href = "instockPack.jsp";
			}else{
				window.location=document.referrer;
				//location.href = "unReceivedPac.jsp";
			}
		}
	});

}
	
	
	
	
	
	</script>

</body>
</html>