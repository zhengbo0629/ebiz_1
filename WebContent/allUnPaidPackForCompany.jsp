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
				&& !user.getUserPermissions().contains(EbizUserPermissionEnum.PayPackage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany company = (EbizCompany) session.getAttribute("currentCompany");
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
			List<EbizPackage> shippedPackages = EbizSql.getInstance().readAllNeedPayListPackagesForCompany(offset,
					pagesize, company.companyName);

		%>
		<%
			int prevpage = pageNumber - 1;
			if (prevpage < 1) {
				prevpage = 1;
			}
			int nextpage = pageNumber + 1;
		%>
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3 style="margin-top: 5px;">所有未支付包裹(All UnPaid Packages)</h3>
			</div>

			<div id="threeBox">
				<div class="alignleft">
					<input onclick="takeGroupTasks()" type="button" value="批量领取任务"
						name="updateAll"
						style="margin-top: 10px; width: 90px; font-size: 12px; height: 30px; background-color: #ffcebf">
					<form name="editCheckBox" id="editCheckBox"
						action="TakePayTasksServlet" method="post">

						<input type="hidden" name="packagesUID" id="packagesUID" value="" />
					</form>
				</div>
				<div class="aligncenter">
					
				</div>
				<div class="alignright">
					<div style="height: 8px"></div>
					<h5>
						<input onclick="myTasks()" type="button" value="已领取任务"
							name="tookenTasks"
							style="margin-top: 0px; width: 90px; font-size: 12px; height: 30px; background-color: #ffcebf">
						<input onclick="finishedTasks()" type="button" value="已完成任务"
							name="finishedTasks"
							style="margin-top: 0px; width: 90px; font-size: 12px; height: 30px; background-color: #ffcebf">
					</h5>
				</div>
			</div>
			<div style="width: 100%; overflow: auto;">


				<table
					style="table-layout: fixed; word-wrap: break-word; color: black">
					<col width=45px>
					<col width=45px>
					<col width=120px>
					<col width=65px>
					<col width=35px>
					<col width=77px>
					<col width=80px>
					<col width=115px>
					<col width=115px>
					<col width=60px>
					<tr>
						<th style="white-space: nowrap;"><input id="checkAll"
							type="checkbox" name="checkAll" onclick="toggle(this)" /><label
							for="checkAll">UID</label></th>
						<th style="line-height: 16px;">用户名
						</th>
						<th style="line-height: 16px;">型号<br>产品名
						</th>
						<th style="font-size: 12px; line-height: 16px;">Brand:UPC:<br>SKU:ASIN
						</th>
						<th>数量<br>价格
						</th>

						<th>报告时间<br>更新时间
						</th>
						<th style="font-size: 12px; line-height: 16px;">地址<br>邮寄状态
						</th>

						<th style="font-size: 12px; line-height: 16px;">包裹单号<br>Ship
							Id
						</th>
						<th style="font-size: 12px; line-height: 16px;">信用卡
						</th>
						<th>操作</th>
					</tr>
					<%
						if (shippedPackages != null) {
							for (int i = 0; i < shippedPackages.size(); i++) {
								
								EbizPackage row = shippedPackages.get(i);
								//System.out.println(row.UID);
								//System.out.println(row.userName);
								String credit=row.creditcardNumber;
								//if(credit.trim().length()==0) continue;
					%>
					<tr>
						<td style="white-space: nowrap;"><input
							id="check<%=row.UID%>" type="checkbox" name="checkbox"
							value="<%=row.UID%>" /><label for="check<%=row.UID%>"><%=row.UID%></label></td>
							<td style='font-size: 11px; line-height: 14px'><%=row.userName%></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.getModel()%><br><%=row.productName%></td>
						<td style='font-size: 11px; line-height: 13px'><%=row.brand%><br><%=row.UPC%><br><%=row.SKU%><br><%=row.ASIN%></td>
						<td><%=row.quantity%><br><%=row.price%></td>

						<td style='font-size: 11px; line-height: 13px'><%=row.createdTime%><br><%=row.updateTime%></td>

						<%
							String trackingString = "";
									String shippingAddress = row.shippingAddress;
									if (EbizCompanyAddressEnum.isCompanyAddress(shippingAddress)) {
										trackingString = row.trackingNumber.replace("?", "<br>");
						%>
						<td
							style='font-size: 12px;line-height:14px	;			
						<%if (row.getStatus().equals(EbizPackageStatusEnum.Complete.getColumnName())) {%>
						background-color:#a1ff64
						<%}%>'><%=company.getAddressNameByAddress(row.shippingAddress)%><br><%=row.getStatus()%></td>
						<%
							} else {
										trackingString = row.trackingNumber.replace("?", "<br>");
										if (row.shipID != null && row.shipID.length() > 0)
											trackingString = trackingString + "<br>" + row.shipID.replace("?", "<br>");
						%>
						<td
							style='font-size: 12px;line-height:14px;
												<%if (row.getStatus().equals(EbizPackageStatusEnum.Complete.getColumnName())) {%>
						background-color:#a1ff64
						<%}%>'>Home<br><%=row.getStatus()%></td>
						<%
							}
						%>
						<td
							style='font-size: 12px; line-height: 15px; padding-top: 5px; padding-bottom: 5px'><%=trackingString%></td>						
						<td
							style='font-size: 12px; line-height: 15px; padding-top: 5px; padding-bottom: 5px'><%=credit%></td>


						<td><form action='TakeOneUserPayTaskServlet' method='POST' name="oneUserTaskForm<%=row.UID%>" id="oneUserTaskForm<%=row.UID%>">
								<input type='hidden' name='packageUserName' id='packageUserName' value="<%=row.userName%>" /><input
									onclick="takeOneUserTasks(this)" type="button" value="领取该用户"
									name="updateOne"
									style="margin-top: 0px; width: 75px; font-size: 13px; height: 30px; background-color: #ffcebf">
							</form></td>

						<%
							}
							}
						%>
					
				</table>
			</div>

			<div style="text-align: right; margin-right: 0px">
				<br>
				<h4>
					<a href='allUnPaidPackForCompany.jsp?PageNumber=<%=prevpage%>'>PrevPage</a><a>
					</a><a href='allUnPaidPackForCompany.jsp?PageNumber=<%=nextpage%>'>NextPage</a>
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
		function toggle(source) {
			checkboxes = document.getElementsByName("checkbox");
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				checkboxes[i].checked = source.checked;
			}
		}
		
		function takeOneUserTasks(source) {
			$("#".concat(source.form.name)).ajaxSubmit({
				beforeSubmit : function() {
					// alert("我在提交表单之前被调用！");
				},
				success : function(data) {
					alert(data);
					if(data.includes("Sucessfully")){
						location.href = "allCurrentPayingTasksForUser.jsp";
					}else {
						
					}
				}
			});
			return;
		}
		
		function takeGroupTasks() {
		
			
			checkboxes = document.getElementsByName("checkbox");
			
			var counter=0;
			var message = "";
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
			
			document.getElementById("packagesUID").value =message;
			
			$("#editCheckBox").ajaxSubmit({
				beforeSubmit : function() {
					// alert("我在提交表单之前被调用！");
				},
				success : function(data) {
					alert(data);
					if(data.includes("Sucessfully")){
						location.href = "allCurrentPayingTasksForUser.jsp";
					}else {
						
					}
				}
			});
			return;
		}
		
		function myTasks() {
			location.href = "allCurrentPayingTasksForUser.jsp";
		}
		function finishedTasks() {
			location.href = "allPaidPackForUser.jsp";
		}
		
		
	</script>

</body>
</html>