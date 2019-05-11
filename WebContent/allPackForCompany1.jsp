<%@page import="nameEnum.EbizPackageColumnEnum"%>
<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
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
		if (user == null || !user.isSelfEmployedDoctor() && !user.isDoctor()
				&& !user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany currentCompany = (EbizCompany) session.getAttribute("currentCompany");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>公司所有包裹(All Package for Company)</h3>
			</div>
			<div style="width: 100%; overflow: auto;">

				<div style="margin: 0px;">
					<form action='allPackForCompany.jsp' method='POST' id='myform'>

						<div
							style="clear: both; float: left; padding-bottom: 5px; width: 100%;">
							<label for="searchPackageid"> UID:</label>
							<input name="searchPackageid" type="number"
								id="searchPackageid"
								style="width: 70px; height: 16px; border: 1px solid #f0bfca;" />
								<label for="userName"> 用户名:</label>
							<input name="userName" type="text" id="userName"
								style="width: 90px; height: 16px; border: 1px solid #f0bfca;" />
								<label for="brand">品牌: </label>
							<input name="brand" type="text" id="brand"
								style="width: 85px; height: 16px; border: 1px solid #f0bfca;" />
								<label for="modelNumber">型号: </label>
							<input name="modelNumber" type="text" id="modelNumber"
								style="width: 90px; height: 16px; border: 1px solid #f0bfca;" />
									 <%
									String t =(String)session.getAttribute("tracking");
								
										
								%> 
								<label for="tracking">运单号:</label>
							<input name="tracking" type="text" id="tracking"  value="<%=t %>"
								style="width: 150px; height: 16px; border: 1px solid #f0bfca;" />
								<label for="upc">UPC:</label>
							<input name="upc" type="text" id="upc"
								style="width: 90px; height: 16px; border: 1px solid #f0bfca;" />

								<label for="shipStatus">邮寄状态：</label>
							<select name="shipStatus" id="shipStatus" 
								style="font-size: 12px;width: 120px; height: 26px; margin-top: 2px; border: 1px solid #f0bfca;">
							<option selected="selected" value="0">请选择</option>
								<option value="UnMatch">UnMatch</option>
								<option value="UnConfirmed">UnConfirmed</option>


								<%
									String temp1 = EbizPackageStatusEnum.InStock.getColumnName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackageStatusEnum.UnReceived.getColumnName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackageStatusEnum.EmailedLabel.getColumnName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackageStatusEnum.Packed.getColumnName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackageStatusEnum.Shipped.getColumnName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackageStatusEnum.Delivered.getColumnName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackageStatusEnum.Complete.getColumnName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
							</select> 
							<label for="payStatus">支付状态：</label>
							<select name="payStatus" id="payStatus"
								style="font-size: 12px;width: 120px; height: 26px; margin-top: 2px; border: 1px solid #f0bfca;">
								<option selected="selected" value="0">请选择</option>
								<%
									temp1 = EbizPackagePayStatusEnum.UnPaid.getName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackagePayStatusEnum.Paying.getName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackagePayStatusEnum.PartlyPaid.getName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>
								<%
									temp1 = EbizPackagePayStatusEnum.Paid.getName();
								%>
								<option value='<%=temp1%>'><%=temp1%></option>

							</select> 
							<label for="address">仓库：</label>
							<select name="address" id="address"
								style="font-size: 12px;width: 120px; height: 26px; margin-top: 2px; border: 1px solid #f0bfca;">
								<option selected="selected" value="0">请选择</option>
								<option value="Home">Home</option>
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

								<%-- <%
								String a=request.getParameter("productName1");
								request.setAttribute("productname", a);
								%>	 --%>
							
							<label for="productName">商品名:</label>
							<input name="productName" type="text" id="productName" value="${sessionScope.productname}"
								style="width: 150px; height: 16px; border: 1px solid #f0bfca;" />
								<%
								List<EbizProduct> productLists = EbizSql.getInstance()
										.readAllActiveAndAliveDealProducs(currentCompany.companyName);
							%>
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
							</select> <input type="submit" name="search" value="查询" id="search"
								style="width: 120px; height: 26px" />
								<input type="button" name="reset" value="重置" id="reset" onclick="
$(':input','#myform')  
 .not(':button, :submit, :reset, :hidden')  
 .val('')  
 .removeAttr('checked')  
 .removeAttr('selected'); "
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

					HashMap<String, String> keyValuePairs = new HashMap<>();

					String packageID = request.getParameter("searchPackageid");
					if (packageID != null && packageID.length() > 0) {
						keyValuePairs.put(EbizPackageColumnEnum.UID.getName(), packageID);
					}
					String brand = request.getParameter("brand");
					if (brand != null && brand.length() > 0) {
						keyValuePairs.put(EbizPackageColumnEnum.Brand.getName(), brand);
					}
					String model = request.getParameter("modelNumber");
					if (model != null && model.length() > 0) {
						keyValuePairs.put(EbizPackageColumnEnum.ModelNumber.getName(), model);
					}
					String userName = request.getParameter("userName");
					if (userName != null && userName.length() > 0) {
						keyValuePairs.put(EbizPackageColumnEnum.UserName.getName(), userName);
					}
					String tracking = request.getParameter("tracking");
					if (tracking != null && tracking.length() > 0) {
						keyValuePairs.put(EbizPackageColumnEnum.TrackingNumber.getName(), tracking);
					}
					String upcString = request.getParameter("upc");
					if (upcString != null && upcString.length() > 0) {
						keyValuePairs.put(EbizPackageColumnEnum.UPC.getName(), upcString);
					}
					
					String shippingStatus = request.getParameter("shipStatus");

					if (shippingStatus != null && shippingStatus.length() > 2) {
						keyValuePairs.put(EbizPackageColumnEnum.Status.getName(), shippingStatus);
					}
					String payStatus = request.getParameter("payStatus");

					if (payStatus != null && payStatus.length() > 2) {
						keyValuePairs.put(EbizPackageColumnEnum.PayStatus.getName(), payStatus);
					}
					
					String addressString = request.getParameter("address");

					if (addressString != null && addressString.length() > 2) {
						keyValuePairs.put(EbizPackageColumnEnum.ShippingAddress.getName(), addressString);
					}
					//获取到商品名的值
					String productName=request.getParameter("productName");
					if (productName != null && productName.length() > 0) {
						keyValuePairs.put(EbizPackageColumnEnum.ProductName.getName(), productName);
					}
				
					List<EbizPackage> shippedPackages = EbizSql.getInstance().readAllPackagesForCompany(request,offset, pagesize,
							currentCompany.companyName, keyValuePairs);
					
					int prevpage = pageNumber - 1;
					if (prevpage < 1) {	
						prevpage = 1;
					}
					int nextpage = pageNumber + 1;
				%>
				<div
					style="clear: both; float: left; padding-bottom: 0px; width: 100%;">
					<div id="threeBox">
						<div class="alignleft"></div>
						<div class="aligncenter"></div>
						<div class="alignright">
							<div style="height: 8px"></div>
							<h5>
								<a href="allPackForCompany.jsp?PageNumber=<%=prevpage%>">上一页</a><a>
								</a><a href="allPackForCompany.jsp?PageNumber=<%=nextpage%>">下一页</a>
							</h5>
						</div>
					</div>
				</div>
				<table
					style="table-layout: fixed; word-wrap: break-word; color: black">
					<col width=35px>
					<col width=45px>
					<col width=110px>
					<col width=70px>
					<col width=38px>
					<col width=53px>
					<col width=100px>
					<col width=90px>
					<col width=60px>
					<col width=90px>
					<tr>
						<th>UID</th>
						<th style="line-height: 16px;">用户名</th>
						<th style="line-height: 16px;">型号<br>产品名
						</th>

						<th style="font-size: 12px; line-height: 16px;">Brand:UPC:<br>SKU:ASIN
						</th>
						<th>数量<br>价格
						</th>

						<th>报告时间<br>更新时间
						</th>
						<th style="font-size: 12px; line-height: 16px;">地址:邮寄状态:<br>包裹单号:Ship
							Id
						</th>
						<th>支付状态<br>支付信息
						</th>
						<th style="font-size: 12px; line-height: 16px;">报单:对单:<br>发单:支付
						</th>
						<th>操作</th>
					</tr>
					<%
						if (shippedPackages != null) {
							for (int i = 0; i < shippedPackages.size(); i++) {
								EbizPackage row = shippedPackages.get(i);
					%>
					<tr>
						<td><%=row.UID%></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.userName%></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.getModel()%><br><%=row.productName%></td>
						<td style='font-size: 11px; line-height: 13px'><%=row.brand%><br><%=row.UPC%><br><%=row.SKU%><br><%=row.ASIN%></td>
						<td><%=row.quantity%><br><%=row.price%></td>

						<td style='font-size: 11px; line-height: 13px'><%=row.createdTime%><br><%=row.updateTime%></td>



						<%
							String shippingAddress = row.shippingAddress;
									if (EbizCompanyAddressEnum.isCompanyAddress(shippingAddress)) {
										String trackingString = row.trackingNumber.replace("?", "<br>");
						%>
						<td
							style='font-size: 12px;line-height:14px	;			
						<%if (row.getStatus().equals(EbizPackageStatusEnum.Complete.getColumnName())) {%>
						background-color:#a1ff64
						<%}%>'><%=currentCompany.getAddressNameByAddress(row.shippingAddress)%><br><%=row.getStatus()%><br><%=trackingString%></td>
						<%
							} else {
										String trackingString = row.trackingNumber.replace("?", "<br>");
										if (row.shipID != null && row.shipID.length() > 0)
											trackingString = trackingString + "<br>" + row.shipID.replace("?", "<br>");
						%>
						<td
							style='font-size: 12px;line-height:14px;
												<%if (row.getStatus().equals(EbizPackageStatusEnum.Complete.getColumnName())) {%>
						background-color:#a1ff64
						<%}%>'>Home<br><%=row.getStatus()%><br><%=trackingString%></td>
						<%
							}
						%>
						<%
							if (row.getPayStatus().equals(EbizPackagePayStatusEnum.Paid.getName())) {
						%>
						<td
							style='font-size: 11px; line-height: 12px; background-color: #a1ff64'><%=row.getPayStatus()%><br><%=row.creditcardNumber%></td>
						<%
							} else {
						%>
						<td style='font-size: 11px; line-height: 12px;'><%=row.getPayStatus()%><br><%=row.creditcardNumber%></td>

						<%
							}
						%>


						<td style='font-size: 11px; line-height: 12px;'><%=row.userName%><br><%=row.getChecker()%><br><%=row.getLabeler()%><br><%=row.getPayer()%></td>
						<%
							if (row.getStatus().toLowerCase().contains("unconfirmed")) {
						%>

						<td>
							<div>

								<div style="float: left;">
									<form action='editPackageByDoctor.jsp' method='POST'>
										<input type='hidden' name='packageid' value="<%=row.UID%>" /><input
											type='submit'
											style='width: 55px; height: 25px; font-size: 13px'
											name='updateButton' value='Update' />
									</form>
								</div>

								<div style="float: left;">
									<form action="ConfirmPackServlet" method="POST"
										name="confirmForm<%=row.UID%>" id="confirmForm<%=row.UID%>">
										<input type="hidden" name="packID" value="<%=row.UID%>" /><input
											type="button"
											style="width: 55px; height: 25px; background-color: #ffceb7; font-size: 13px"
											name="submit-btn" value="Confirm" onclick="confirmPack(this)" />
									</form>
								</div>



								<div style="float: left;">
									<form action="DeletePackServlet" method="POST"
										name="deleteForm<%=row.UID%>" id="deleteForm<%=row.UID%>">
										<input type="hidden" name="packID" value="<%=row.UID%>" /><input
											type="button"
											style="width: 55px; height: 25px; background-color: #ffc4c4; font-size: 13px"
											name="submit-btn" value="Delete" onclick="deletePack(this)" />
									</form>
								</div>
							</div>

							<div>
								<div style="float: left;">
									<form action='sendEmailToUser.jsp' method='POST'>
										<input type='hidden' name='packageid' value="<%=row.UID%>" /><input
											type='submit'
											style='width: 55px; height: 25px; font-size: 13px'
											name='emailButton' value='Email' />
									</form>
								</div>
							</div>

						</td>

						<%
							} else {
						%>
						<td><div style="float: left;">
								<form action='editPackageByDoctor.jsp' method='POST'>
									<input type='hidden' name='packageid' value="<%=row.UID%>" /><input
										type='submit'
										style='width: 55px; height: 25px; font-size: 13px'
										name='updateButton' value='Update' />
								</form>
							</div>


							<div>
								<div style="float: left;">
									<form action='sendEmailToUser.jsp' method='POST'>
										<input type='hidden' name='packageid' value="<%=row.UID%>" /><input
											type='submit'
											style='width: 55px; height: 25px; font-size: 13px'
											name='emailButton' value='Email' />
									</form>
								</div>
							</div></td>

						<%
							}
								}
							}
						%>
					
				</table>
			</div>

			<div style="text-align: right; margin-right: 0px">
				<br>
				<h4>
					<a href='allPackForCompany.jsp?PageNumber=<%=prevpage%>'>上一页</a><a>
					</a><a href='allPackForCompany.jsp?PageNumber=<%=nextpage%>'>下一页</a>
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
	 function resetFun(){
  $('tracking').value='';
 
  return;
 }
		function confirmPack(source) {
			var msg = "Are you sure want to confirm this package？\n\n Please confirm！";
			if (confirm(msg) == false) {
				return;
			}
			$("#".concat(source.form.name)).ajaxSubmit({
				beforeSubmit : function() {
					// alert("我在提交表单之前被调用！");
				},
				success : function(data) {
					alert(data);
					if (data.includes("Sucessfully")) {
						location.href = "allPackForCompany.jsp";
					} else {

					}
				}
			});
		}
		function deletePack(source) {
			var msg = "Are you sure want to delete this package？\n\n Please confirm！";
			if (confirm(msg) == false) {
				return;
			}
			$("#".concat(source.form.name)).ajaxSubmit({
				beforeSubmit : function() {
					// alert("我在提交表单之前被调用！");
				},
				success : function(data) {
					alert(data);
					if (data.includes("Sucessfully")) {
						location.reload();
					} else {
						location.reload();
					}
				}
			});
		}

		function changeModel() {
			var myselect = document.getElementById("product");
			var index = myselect.selectedIndex;

			var vs = myselect.options[index].value;
			if (vs === "0") {
				return;
			} else {
				var array = vs.split(",");
				var uid = parseInt(array[0]);
				var tick = parseInt(array[1]);
				var productPersonalLimit = parseInt(array[2]);
				var model = array[3];
				//console.log(model);
				// var form = new FormData();
				// form.append("Model","aaa");

				document.getElementById("modelNumber").value = model;

				return;

			}
		}
	</script>

</body>
</html>