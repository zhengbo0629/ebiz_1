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
				<h3>领票或预报</h3>
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

			<form name="reportPackageForm" id="reportPackageForm"
				action="ReportPackageServlet" method="post"
				>
				<table class="tablewithoutline">
					<tr>
						<td class="alignRight" style="width: 80px">选择商品：</td>
						<td><select onchange="changeTickets(<%=user.personalLimit%>)"
							id="product" name="product" style="width: 100%">
								<option selected="selected" value="0">Please choose a
									product</option>

								<%
								String value;
								String content;
								if(productLists!=null){
									for (int i = 0; i < productLists.size(); i++) {
										value = productLists.get(i).getUID() + "," + productLists.get(i).tickets + ","
												+ productLists.get(i).limitPerPerson + "," + productLists.get(i).getModel();
										content = productLists.get(i).getProductName() + "," + productLists.get(i).getModel() + ","
												+ productLists.get(i).getPrice();
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
					<tr>
						<td class="alignRight" style="width: 80px">填写数量：</td>
						<td style="font-size: 16px"><input type="number"
							name="quantity" placeholder="Your Quantity" style="width: 150px"
							required /> Limit:<input type="text" id="ticket" name="ticket"
							value="0" style="width: 130px" readonly="readonly" /> <%
 	if (user != null) {
 		if (user.userName.toLowerCase().equals("mike")

 				|| user.getUserType().equals(EbizUserTypeEnum.Doctor.getName())) {
 %> Total left:<input type="text" id="ticketLeft" name="ticketLeft"
							value="0" style="width: 150px" readonly="readonly" /> <%
 	} else {
 			//不加的话后面javascript里面无法发现这个，就出错，就没办法继续执行下去 <input type="hidden"
 %> <input type="hidden" id="ticketLeft" name="ticketLeft" value="0"
							readonly="readonly" /> <%
 	}
 	}
 %></td>
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">信用卡信息：</td>
						<td><textarea name="CreditCardNumber"
								placeholder="Credit Card Number, New Card Needs Bank Name At Least"
								style="resize: none;" rows="4" required /></textarea></td>
					</tr>

					<tr>
						<td class="alignRight">选择地址：</td>
						<td>
							<div class='container'>
								<input onclick="changeToHome('<%=user.getAddress()%>');"
									type="button" value="Home" id="homeButton">
								<% if (company.getAddress1()!=null&&company.getAddress1().length()>3){
										%>

								<input onclick="changeToAddress1()" type="button"
									value="<%=company.getAddress1Name()%>" id="address1">

								<%
									}%>
								<% if (company.getAddress2()!=null&&company.getAddress2().length()>3){
										%>

								<input onclick="changeToAddress2()" type="button"
									value="<%=company.getAddress2Name()%>" id="address2">

								<%
									}%>
								<% if (company.getAddress3()!=null&&company.getAddress3().length()>3){
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
						value="Submit" onClick="addPackage()" />
				</div>
			</form>
			<form method="post" action="GetReportedNumberServlet" id="ajaxForm">
				<input type="hidden" name="Model" id="Model" /><br> <input
					type="hidden" value="submit" id="ajaxSubmit" />&nbsp;&nbsp;
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
		var subed = true;

		function addPackage(){
			if (subed) {
				subed = false;
				$("#reportPackageForm").ajaxSubmit({
					beforeSubmit : function() {
						// alert("我在提交表单之前被调用！");
					},
					success : function(data) {
						alert(data);
						if(data.includes("Failed")){
							
						}else {
							
							location.href = "unReceivedPac.jsp";
						}
					}
				});

			} else {
				return;
				//alert("You request is being processed, Please do not repeat");  
			}


		}

		function changeToHome(homestring) {
			document.getElementById("address").value =homestring;
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
		
		function changeTickets(limit) {
			var myselect = document.getElementById("product");
			var index = myselect.selectedIndex;


			var vs = myselect.options[index].value;
			if(vs==="0"){
				return;
			}else{
			var array = vs.split(",");
			var uid=parseInt(array[0]);
			var tick = parseInt(array[1]);
			var productPersonalLimit = parseInt(array[2]);
			var model =array[3];
			//console.log(model);
			// var form = new FormData();
			 // form.append("Model","aaa");
			 
			 document.getElementById("Model").value =model;
			 
			 
			 var reportedNumber=0;
			  $("#ajaxForm").ajaxSubmit({
                    beforeSubmit:function () {
                       // alert("我在提交表单之前被调用！");
                    },
                    success:function (data) {
                            reportedNumber= data;
            				var personalLimit =limit;
            				if (personalLimit > productPersonalLimit) {
            					limit = productPersonalLimit;
            				}
            				limit=limit-reportedNumber;
            				if(limit<0){
            					limit=0;
            				}
            				document.getElementById("ticketLeft").value = tick;
            				if (tick > limit) {
            					document.getElementById("ticket").value = limit;
            				} else {
            					document.getElementById("ticket").value = tick;
            				}
                    }
                });
			 return;

		}
		}

	</script>
</body>
</html>