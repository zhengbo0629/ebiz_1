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
				<h3>添加或修改产品</h3>
			</div>

			<%
			String idString=request.getParameter("productID");
			EbizProduct product=null;
			String UID="";
			String UPC="";
			String ASIN="";
			String SKU="";
			String Brand="";
			String model="";
			String productName="";
			String url="";
			int tickets=2000;
			int personlimits=300;
			double weight=0;
			double length=0;
			double width=0;
			double height=0;
			String status="";
			double price=0;
			double promotprice=0;
			int promotQuantity=0;
			double warehouseprice=0;
			double warehousepromotquantity=0;
			double warehousepromotprice=0;
			String usernote="";
			if (idString!=null&&idString.length()!=0){
				int productID = Integer.parseInt(idString);
				product = EbizSql.getInstance().findProduct(productID);
			}
			if(product!=null){
				UID=product.UID.toString();
				UPC=product.UPC;
				ASIN=product.Asin;
				SKU=product.SKU;
				Brand=product.brand;
				model=product.model;
				productName=product.productName;
				url=product.uRI;
				tickets=product.tickets;
				personlimits=product.limitPerPerson;
				weight=product.weight;
				length=product.length;
				width=product.width;
				height=product.height;
				status=product.status;
				price=product.price;
				promotprice=product.promotPrice;
				promotQuantity=product.promotQuantity;
				warehouseprice=product.warehousePrice;
				warehousepromotprice=product.warehousePromotePrice;
				warehousepromotquantity=product.warehousePromotQuantity;
				usernote=product.getUserNote();
				System.out.println(usernote);
			}
			
			
			%>


			<div align=center>
			<div align=left style="width: 99%; float: left; border: 0px solid #d7d7d7; min-height: 400px; font-size:14px">
		<form name="edit" id="edit" action="AddOrEditProductServlet" method="post">
					<br>
					<% if(UID.length()>0){
						%>

						Product UID:
				<input style="width: 60px;height:25px "
				type="text" id="productID" name="productID"
				value="<%=UID%>" readonly="readonly"/>&emsp;
						<%
					}%>



					Model:&nbsp;<input
						style="width: 100px; height:25px "
						type="text" id="model" name="model"
						value="<%=model%>" placeholder="required"
						<%if (model.length()>0) {
						%>
						readonly="readonly"
						<%
						}
						%>
						
						/>&emsp;Status:&nbsp; 
						
						<select id="status" name="status"
										style="height: 35px; width: 200px;">
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

									</select> <br>
					Product Name: length less than 250
					<textarea id="productName" name="productName"
						style="background-color: transparent; resize: none;"
						rows="2" cols="58"  placeholder="required"><%=productName%></textarea>
					<br>
					Brand:&nbsp;<input
						style="width: 120px; height:25px  "
						type="text" id="productBrand" name="productBrand"  placeholder="required"
						value="<%=Brand%>"/> &emsp;SKU:&nbsp;<input
						style="width: 120px; height:25px "
						type="text" id="productSKU" name="productSKU"
						value="<%=SKU%>"/>&emsp;UPC:&nbsp;<input
						style="width: 120px; height:25px  "
						type="text" id="productUPC" name="productUPC"
						value="<%=UPC%>"/>&emsp;ASIN:&nbsp;<input
						style="width: 120px; height:25px "
						type="text" id="productASIN" name="productASIN"
						value="<%=ASIN%>"/><br>
					Web Address:&nbsp;
					<input
						style="width: 89%; height:25px"
						type="text" id="webAddress" name="webAddress"
						value="<%=url%>"  />
					<br>
					Tickets:&nbsp;<input
						style="width: 60px; height:25px  "
						type="number" id="productTickets" name="productTickets"
						value="<%=tickets%>" min="0" step="1"/> &emsp;PersonalLimit:&nbsp;<input
						style="width: 60px; height:25px  "
						type="number" id="personalLimit" name="personalLimit" min="0" step="1"
						value="<%=personlimits%>"/>&emsp;
						W(LB):&nbsp;<input
						style="width: 60px; height:25px  "
						type="number" id="productWeight" name="productWeight"
						value="<%=weight%>"/>&emsp;L(inch):&nbsp;<input
						style="width: 60px; height:25px  "
						type="number" id="productLength" name="productLength"
						value="<%=length%>"/>&emsp;W(inch):&nbsp;<input
						style="width: 60px; height:25px  "
						type="number" id="productWidth" name="productWidth"
						value="<%=width%>"/>&emsp;H(inch):&nbsp;<input
						style="width: 60px; height:25px  "
						type="number" id="productHeight" name="productHeight"
						value="<%=height%>"/><br><br>

				<div class="clear"></div>
				<div style="width:40%;float:left;border: 1px solid #CCC;padding:5px 5px 5px 5px;">
				下单到家里的收货价格：
				<br>
				<div style="float:left;">
				价格：<br>
				<input
						style="width: 80px; height:25px  "
						type="number" id="productPrice" name="productPrice"
						value="<%=price%>"/>
				</div>
				<div style="float:left">
				加价收购数量：<br>
				<input
						style="width: 80px; height:25px  "
						type="number" id="promotQuantity" name="promotQuantity"
						value="<%=promotQuantity%>" min="0" step="1"/>
				</div>
				<div style="float:left">
				加价收购价格：<br>
				<input
						style="width: 80px; height:25px  "
						type="number" id="promotPrice" name="promotPrice"
						value="<%=promotprice%>"/>
				</div>
				
				
				
				</div>
				<div style="width:40%;float:left;border: 1px solid #CCC;padding:5px 5px 5px 5px;margin-left:10px">
				下单到仓库的收货价格：
				<br>
				<div style="float:left;">
				价格：<br>
				<input
						style="width: 80px; height:25px  "
						type="number" id="productWarehousePrice" name="productWarehousePrice"
						value="<%=warehouseprice%>"/>
				</div>
				<div style="float:left">
				加价收购数量：<br>
				<input
						style="width: 80px; height:25px  "
						type="number" id="warehousePromotQuantity" name="warehousePromotQuantity"
						value="<%=warehousepromotquantity%>" min="0" step="1" />
				</div>
				<div style="float:left">
				加价收购价格：<br>
				<input
						style="width: 80px; height:25px  "
						type="number" id="warehousePromotPrice" name="warehousePromotPrice"
						value="<%=warehousepromotprice%>"/>
				</div>
				
				
				
				
				
				</div>
				<div class="clear"></div><br>
				
				Note:<br>
					<textarea id="userNote" name="userNote"
						style="background-color: transparent; resize: none;"
						rows="2" cols="58" ><%=usernote%></textarea>
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
			alert(data);
			if(data.includes("Nothing Updated")||data.includes("Update Failed")){
				
			}else {
				
				location.href = "allProductManage.jsp";
			}
		}
	});

}
	
	
	
	
	
	</script>

</body>
</html>