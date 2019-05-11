<%@page import="nameEnum.EbizProductColumnEnum"%>
<%@page import="ebizConcept.EbizInventory"%>
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

<%@page import="java.util.HashMap"%>
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
				<h3>所有产品管理(All Product Manage)</h3>
			</div>


				<div style="float:right">
					<input onclick="javascript:window.location.href='addOrEditProduct.jsp'" type="button" value="Add A New Product"
						name="addNewProduct"
						style="margin-top: 0px; width: 150px; font-size: 12px; height: 25px; background-color: #ffcebf">
				</div>
								<div style="float:right">
					<input onclick="javascript:window.location.href='allProductManage.jsp'" type="button" value="All Product"
						name="allProduct"
						style="margin-top: 0px; width: 150px; font-size: 12px; height: 25px; background-color: #ffcebf">
				</div>
				<div style="float:right">
					<input onclick="javascript:window.location.href='activeProductManage.jsp'" type="button" value="Active Product"
						name="activeProduct"
						style="margin-top: 0px; width: 150px; font-size: 12px; height: 25px; background-color: #ffcebf">
				</div>

			<div style="width: 100%; overflow: auto;">
				<div style="margin: 0px;">
					<form action='allProductManage.jsp' method='POST'>

						<div
							style="clear: both; float: left; padding-bottom: 5px; width: 100%;">
							<label for="address">仓库：</label>
							<select name="address" id="address"
								style="font-size: 12px;width: 120px; height: 26px; margin-top: 2px; border: 1px solid #f0bfca;">
								<option selected="selected" value="0">请选择</option>
								<!-- <option value="Home">Home</option> -->
								<%
									String address1 = currentCompany.getAddress1Name();
									if (address1 != null && address1.trim().length() > 0) {
								%>
								<option value='<%=EbizCompanyAddressEnum.Address1.getName()%>'><%=address1%></option>
								<%
									}
									String address2 = currentCompany.getAddress2Name();
									if (address2 != null && address2.trim().length() > 0) {
								%>
								<option value='<%=EbizCompanyAddressEnum.Address2.getName()%>'><%=address2%></option>
								<%
									}
									String address3 = currentCompany.getAddress3Name();
									if (address3 != null && address3.trim().length() > 0) {
								%>
								<option value='<%=EbizCompanyAddressEnum.Address3.getName()%>'><%=address3%></option>
								<%
									}
								%>

							</select>
							
							
							<label for="searchPackageid"> UID:</label>
							<input name="searchPackageid" type="number"
								id="searchPackageid"
								style="width: 70px; height: 16px; border: 1px solid #f0bfca;" />
								<label for="brand">品牌: </label>
							<input name="brand" type="text" id="brand"
								style="width: 85px; height: 16px; border: 1px solid #f0bfca;" />
								<label for="modelNumber">型号: </label>
							<input name="modelNumber" type="text" id="modelNumber"
								style="width: 90px; height: 16px; border: 1px solid #f0bfca;" />
								
								<label for="upc">UPC:</label>
							<input name="upc" type="text" id="upc"
								style="width: 90px; height: 16px; border: 1px solid #f0bfca;" />

								
							
							

							<%
								List<EbizProduct> productLists = EbizSql.getInstance()
										.readAllActiveAndAliveDealProducs(currentCompany.companyName);
							%>
							<label for="productName">商品名:</label>
							<input name="productName" type="text" id="productName"
								style="width: 150px; height: 16px; border: 1px solid #f0bfca;" />
							
							<label for="product">或选择商品：</label>
							<select onchange="changeModel()" id="product"
								name="product"
								style="font-size: 12px;width: 50%; height: 26px; margin-top: 2px;border: 1px solid #f0bfca">
								<option selected="selected" value="0">请选择</option>

								<%
									String value;
									String content;
									if (productLists != null) {
										for (int i = 0; i < productLists.size(); i++) {
											value = productLists.get(i).getUID() + "," + productLists.get(i).tickets + ","
													+ productLists.get(i).limitPerPerson + "," + productLists.get(i).getModel();
											content = productLists.get(i).getProductName() + "," + productLists.get(i).getModel() + ","
													+ productLists.get(i).getPrice();
								%>

								<option value='<%=value%>'><%=content%></option>

								<%
									}
									} else {
										value = "";
										content = "";
									}
								%>
							</select> 
							<input type="submit" name="search" value="查询" id="search"
								style="width: 120px; height: 26px" />

						</div>
						

					</form>
				</div>
			

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
/* 					List<EbizProduct> productList = EbizSql.getInstance().readAllNonDeletedProductSet(offset, pagesize, currentCompany.companyName);
 */
					HashMap<String, String> keyValuePairs = new HashMap<>();

					String productID = request.getParameter("searchPackageid");
					if (productID != null && productID.length() > 0) {
						keyValuePairs.put(EbizProductColumnEnum.UID.getColumnName(), productID);
					}
					String brand = request.getParameter("brand");
					if (brand != null && brand.length() > 0) {
						keyValuePairs.put(EbizProductColumnEnum.Brand.getColumnName(), brand);
					}
					String model = request.getParameter("modelNumber");
					if (model != null && model.length() > 0) {
						keyValuePairs.put(EbizProductColumnEnum.ModelNumber.getColumnName(), model);
					}
					
					String upcString = request.getParameter("upc");
					if (upcString != null && upcString.length() > 0) {
						keyValuePairs.put(EbizProductColumnEnum.UPC.getColumnName(), upcString);
					}
					
				
				//获取到商品名的值
					String productName=request.getParameter("productName");
					if (productName != null && productName.length() > 0) {
						keyValuePairs.put(EbizProductColumnEnum.ProductName.getColumnName(), productName);
					}



					List<EbizProduct> productList = EbizSql.getInstance().readAllProductsForCompany(offset, pagesize,
							currentCompany.companyName, keyValuePairs);

					int prevpage = pageNumber - 1;
					if (prevpage < 1) {
						prevpage = 1;
					}
					int nextpage = pageNumber + 1;
				%>
				
				
				
				
				
				
				
				<table>
					<col width=4%>
					<col width=30%>
					<col width=11%>
					<col width=9%>
					<col width=9%>
					<col width=9%>
					<col width=12%>
					<col width=6%>
					<col width=6%>

					<tr>
						<th>UID</th>
						<th>Model:ASIN<br>ProductName
						</th>
						<th>Brand<br>SKU</th>
						<th>W(LB)<br>Size(Inch)
						</th>
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

						<td><%=row.weight%><br><%=row.length%>*<%=row.width%>*<%=row.height%></td>
						<td style='font-size: 10px;line-height: 12px;'><%=row.price%><br><%=row.promotQuantity%><br><%=row.promotPrice%></td>
						<td style='font-size: 10px;line-height: 12px;'><%=row.warehousePrice%><br><%=row.warehousePromotQuantity%><br><%=row.warehousePromotePrice%></td>

						<td><%=row.getUserNote()%></td>
						<td ><%=row.status%></td>

						<td><form action='addOrEditProduct.jsp' method='POST'>
								<input type='hidden' name='productID' value="<%=row.UID%>" /><input
									type='submit' style='width: 50px; font-size: 13px' name='submit-btn'
									value='Update' />
							</form></td>
					</tr>
					<%
							}
							}
						%>
				</table>
			</div>
			
			
			<%-- <%
					int prevpage = pageNumber - 1;
					if (prevpage < 1) {
						prevpage = 1;
					}
					int nextpage = pageNumber + 1;
				%> --%>
			<div style="text-align: right; margin-right: 0px">
				<br>
				<h4>
					<a href='allProductManage.jsp?PageNumber=<%=prevpage%>'>PrevPage</a><a>
					</a><a href='allProductManage.jsp?PageNumber=<%=nextpage%>'>NextPage</a>
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