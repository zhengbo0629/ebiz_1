<%@page import="ebizConcept.EbizCompany"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="nameEnum.EbizUserParaMeterEnum"%>
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
		if (user == null || !EbizSql.getInstance().isAdministrator(user)) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>更新用户信息</h3>
			</div>
			<%
				int userID = Integer.parseInt(request.getParameter("userID"));
				EbizUser tempUser = EbizSql.getInstance().findUser(userID);
				String introducer = "";
				if (tempUser.getParameter(EbizUserParaMeterEnum.Introducer.getName()) != null) {
					introducer = user.getParameter(EbizUserParaMeterEnum.Introducer.getName());
				}
				EbizCompany company = EbizSql.getInstance().findCompany(tempUser.companyName);
			%>
			<form name="updateDoctorInforForm" method="post"
				action="UpdateDoctorInforServlet" id="updateDoctorInforForm">
				<div
					style="width: 30%; float: left; padding-top: 3px; line-height: 20px;">
					<div
						style="float: left; width: 80px; font-size: 18px; padding-top: 1px;">基本信息
					</div>
					<div>
						<table class="tablewithoutline">
							<tr>
								<td class="labelRightAlign" style="width: 80px;">用户ID：</td>
								<td><input name="userID" type="text" id="userID" required
									style="width: 150px; height: 20px;" value="<%=tempUser.UID%>"
									readonly /></td>
							</tr>
							<tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">登录名：</td>
								<td><input name="username" type="text" id="username"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.userName%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">用户类：</td>
								<td><input name="userType" type="text" id="userType"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.getUserType()%>" readonly /></td>
							</tr>

							<tr>
								<td class="labelRightAlign" style="width: 80px;">姓：</td>
								<td><input name="lastName" type="text" id="lastName"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.lastName%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">名：</td>
								<td><input name="firstName" type="text" id="firstName"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.firstName%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">联系电话：</td>
								<td><input name="contactPhone" type="text"
									id="contactPhone" required style="width: 150px; height: 20px;"
									value="<%=tempUser.phoneNumber%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">邮箱：</td>
								<td><input name="contactEmail" type="text"
									id="contactEmail" required style="width: 150px; height: 20px;"
									value="<%=tempUser.getEmail()%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">地址：</td>
								<td><input name="address" type="text" id="address" required
									style="width: 150px; height: 20px;"
									value="<%=tempUser.address%>" readonly />
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">公司名称：</td>
								<td><input name="companyName" type="text" id="companyName"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.companyName%>" readonly /></td>
							</tr>
						</table>
					</div>

				</div>
				<div
					style="width: 65%; float: left; padding-top: 3px; line-height: 20px;">
					<div
						style="width: 50%; float: left; padding-top: 3px; line-height: 20px;">
						<div
							style="float: left; width: 80px; font-size: 18px; padding-top: 1px;">修改信息
						</div>
						<div>
							<table class="tablewithoutline">
								<tr>
									<td class="labelRightAlign" style="width: 80px;">介绍人：</td>
									<td><input name="introducer" type="text" id="introducer"
										required style="width: 150px; height: 20px;"
										value="<%=introducer%>" /></td>
								</tr>
								<tr>
									<td class="labelRightAlign" style="width: 80px;">密码：</td>
									<td><input name="password" type="text" id="password"
										style="width: 150px; height: 20px; font-size: 12px;"
										placeholder="New Password Or Empty" /></td>
								</tr>

								<tr>
									<td class="labelRightAlign" style="width: 80px;">用户状态</td>
									<td><select id="status" name="status"
										style="height: 35px; width: 160px; font-size: 12px">
											<%
												if (tempUser.isActive()) {
											%>
											<option selected="selected"
												value="<%=EbizUserPermissionEnum.Active.getName()%>"><%=EbizUserPermissionEnum.Active.getName()%></option>


											<option value="UnActive">UnActive</option>
											<%
												} else {
											%>
											<option value="<%=EbizUserPermissionEnum.Active.getName()%>"><%=EbizUserPermissionEnum.Active.getName()%></option>


											<option selected="selected" value="UnActive">UnActive</option>
											<%
												}
											%>
									</select></td>
								</tr>
								<tr>
									<td class="labelRightAlign" style="width: 80px;">余额：</td>
									<td><input name="balance" type="number" id="balance"
										required style="width: 150px; height: 20px;"
										value="<%=tempUser.balance%>" /></td>
								</tr>
							</table>
						</div>
					</div>
					<div
						style="width: 50%; float: left; padding-top: 3px; line-height: 20px;">
						<div
							style="float: left; width: 180px; font-size: 18px; padding-top: 1px;">设置公司营业范围

						</div>
						<div class="clear"></div>
						<div>



							<%
								for (EbizUserPermissionEnum permission : EbizUserPermissionEnum.values()) {
							%>

							<%
								if (permission.getRole().equals("DoctorDefault")||permission.getRole().equals("NurseDefault")) {
							%>
							<input id="<%=permission.getName()%>"
								value="<%=permission.getName()%>" type="checkbox"
								name="checkbox" checked onclick="event.preventDefault();" /><label
								for="<%=permission.getName()%>">&nbsp;<%=permission.getChinese()%></label><br>
							<%
								} else if (permission.getRole().equals("DoctorOption")) {
							%>
							<input id="<%=permission.getName()%>"
								value="<%=permission.getName()%>" type="checkbox"
								name="checkbox"
								<%if (company.getPermissions().contains(permission.getName())) {%>
								checked <%}%> /><label for="<%=permission.getName()%>">&nbsp;<%=permission.getChinese()%></label><br>


							<%
								}
								}
							%>

						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div style="width: 100%; text-align: center;">
					<input type="button" id="modify" name="modify" value="修改"
						style="width: 300px; height: 30px;" onclick="updatePackage()" />
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
		function updatePackage() {

			$("#updateDoctorInforForm").ajaxSubmit({
				beforeSubmit : function() {
					// alert("我在提交表单之前被调用！");
				},
				success : function(data) {
					alert(data);
					if (data.includes("Nothing Updated")) {

					} else {

						location.href = "doctorUserManage.jsp";
					}
				}
			});

		}
	</script>

</body>
</html>