<%@page import="ebizConcept.EbizUser"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>独立账户注册</title>
<link rel="stylesheet"
	href="styles.css?v=<%=System.currentTimeMillis()%>" type="text/css" />
</head>
<body>
	<form name="registrationSendForm" method="post"
		action="SelfemployedRegistrationServlet" id="registrationSendForm" onsubmit="return checkUserNameprocess();">


		<jsp:include page="head.jsp" />


		<div align="center">

			<br> <br>
			<h2>独立账户注册</h2>
			<%
			EbizUser user=(EbizUser)session.getAttribute("currentUser");
			if(user!=null&&user.isActive()){
				response.sendRedirect("loginsuccess.jsp");
			}
			if(user!=null&&!user.isActive()){
				response.sendRedirect("registrationsuccess.jsp");
			}
			%>
			<%
				List<String> info = (List<String>) request.getAttribute("info");
				if (info != null) {
					Iterator<String> iter = info.iterator();
					while (iter.hasNext()) {
			%>
			<h4><%=iter.next()%></h4>
			<%
				}
					request.removeAttribute("info");
				}
				
			%>

			<div style="width: 900px; margin: 0 auto; clear: both;">
				<div
					style="width: 700px; float: left; padding-top: 10px; padding-left: 150px; line-height: 30px;">
					<div
						style="float: left; width: 80px; font-size: 18px; padding-top: 1px;">账户信息
					</div>
					<div style="height: 10px; width: 100%; clear: both; float: left;">
					</div>
					<div style="padding-left: 60px;">
						<table class="tablewithoutline">
							<tr>
								<td class="labelRightAlign">登录名：</td>
								<td><input name="username" type="text" id="username"
									required style="width: 300px; height: 24px;" /></td>
							</tr>
							<tr>
								<td class="labelRightAlign">密码：</td>
								<td><input name="password" type="password" id="password"
									required style="width: 300px; height: 24px;" /></td>
							</tr>
							<tr>
								<td class="labelRightAlign">确认密码：</td>
								<td><input name="confirmpassword" type="password"
									id="confirmpassword" required
									style="width: 300px; height: 24px;" /></td>
							</tr>
						</table>
					</div>
					<div style="height: 10px; width: 100%; clear: both; float: left;">
					</div>


					<div
						style="float: left; width: 80px; font-size: 18px; padding-top: 1px;">公司信息</div>

					<div style="height: 10px; width: 100%; clear: both; float: left;">
					</div>
					<div style="padding-left: 60px;">
						<table class="tablewithoutline">
							<tr>
								<td class="labelRightAlign">公司名称：</td>
								<td><input name="companyName" type="text" id="companyName"
									required style="width: 300px; height: 24px;" /></td>
							</tr>
							<tr>
								<td class="labelRightAlign">地址：</td>
								<td><input name="address" type="text" id="address" required
									style="width: 300px; height: 24px;" />
							</tr>
							<tr>
								<td class="labelRightAlign">邮编：</td>
								<td><input name="zipcode" type="text" maxlength="6"
									id="zipcode" required style="width: 300px; height: 24px;" /></td>
							</tr>
						</table>
					</div>

					<div style="height: 10px; width: 100%; clear: both; float: left;">
					</div>
					<div
						style="float: left; width: 80px; font-size: 18px; padding-top: 1px;">联系信息</div>
					<div style="height: 10px; width: 100%; clear: both; float: left;">
					</div>
					<div style="padding-left: 60px;">
						<table class="tablewithoutline">
							<tr>
								<td class="labelRightAlign">姓：</td>
								<td><input name="lastName" type="text" id="lastName"
									required style="width: 300px; height: 24px;" /></td>
							</tr>
							<tr>
								<td class="labelRightAlign">名：</td>
								<td><input name="firstName" type="text" id="firstName"
									required style="width: 300px; height: 24px;" /></td>
							</tr>
							<tr>
								<td class="labelRightAlign">联系电话：</td>
								<td><input name="contactPhone" type="text"
									id="contactPhone" required style="width: 300px; height: 24px;" />
								</td>
							</tr>
							<tr>
								<td class="labelRightAlign">公司邮箱：</td>
								<td><input name="contactEmail" type="text"
									id="contactEmail" required style="width: 300px; height: 24px;" />
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<div
										style="height: 10px; width: 100%; clear: both; float: left;">
									</div>
									<div style="width: 100%; text-align: left;">
										<input type="submit" id="registration" name="registration"
											value="注 册" style="width: 300px; height: 30px;" />
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
	function checkUserNameprocess() {
		var password = document.getElementById('password').value;
		var confirmpassword = document.getElementById('confirmpassword').value

		if (validateForm()) {
			if (password === confirmpassword && password.length >= 7) {
				return true;
				//document.getElementById('registrationSendForm').submit();
			} else {
				alert("Your password and confirmPassword are not equal and lengh should greater or equal to 7, please check");
				document.getElementById('password').focus();
				return false;
			}

		}
		return false;
	}
	function validateForm() {
		var nameRegex = /^[a-zA-Z0-9]+$/;
		var username = document.getElementById('username').value;
		var validfirstUsername = username.match(nameRegex);
		if (validfirstUsername == null
				|| username.length<5||username.length>20) {
			alert("Your user name is not valid. Only number 0-9, characters A-Z, a-z and '-' are  acceptable. Lengh should greater or equal 5 and less than 20");
			document.getElementById('username').focus();
			return false;
		}
		var companyName = document.getElementById('companyName').value;
		var validCompanyName = companyName.match(nameRegex);
		if (companyName.length<5||companyName.length>=20) {
			alert("Your companyName name lengh should greater or equal 5 and less than 20");
			document.getElementById('companyName').focus();
			return false;
		}

		return true;
	}
	</script>
</body>
</html>