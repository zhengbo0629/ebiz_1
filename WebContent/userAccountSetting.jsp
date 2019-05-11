<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>
<%@page import="java.util.List"%>
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
		if (user == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div
			id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>用户资料(User Profile)</h3>
			</div>
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">

				<h4 style="color: #ff8000">密码和确认密码要一致才能更新用户资料</h4>

			</div>
			<div align=center>
				<div align=left
					style="width: 40%; float: center; border: 0px solid #d7d7d7; min-height: 400px;">
					<form name="updateProfile" id="updateProfile"
						action="UpdateUserProfileServlet" method="post">
						<input type="hidden" name="accountNumber" value="<%=user.UID%>"
							style="width: 400px" readonly /> <br>
						<br>
						<h4>User Name:</h4>
						<input type="text" name="username" value="<%=user.userName%>"
							readonly style="width: 400px" /> <br>
						<br>
						<h4>Email:</h4>
						<input type="email" name="email" id="email"
							value="<%=user.getEmail()%>" style="width: 400px" /> <br>
						<br>
						<h4>Phone Number:</h4>
						<input type="number" name="phoneNumber" id="phoneNumber"
							value="<%=user.phoneNumber%>" style="width: 400px" /> <br>
						<br>
						<h4>Password:</h4>
						<input type="password" name="password"  id="password"
							value="<%=user.passWord%>" style="width: 400px" /> <br>
						<br>
						<h4>Confirm Password:</h4>
						<input type="password" name="confirmpassword" id="confirmpassword"
							value="" style="width: 400px" /> <br>
						<br>
						<h4>Address:</h4>
						<input type="text" name="address" id="address"
							value="<%=user.address%>" style="width: 400px" /> <br>
						<br>
						<br>
						<div align=center>
							<input type="button" id="submitbutton" name="submitbutton"
								value="Update" onClick="submitInfor()" />
						</div>
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
		function submitInfor() {
			var email = document.getElementById("email").value;
			if (email.length === 0) {
				alert("Please check email");
				return;
			}
			
			var password = document.getElementById("password").value;
			
			var confirmpassword = document.getElementById("confirmpassword").value;
			
			if (password != confirmpassword|| password.length < 7) {
				alert("Password does not equal to confirm Password or password length is less than 7, Please check");
				return;
			}
			
			var address = document.getElementById("address").value;
			if (address.length === 0) {
				alert("Please check address");
				return;
			}

			$("#updateProfile").ajaxSubmit(
					{
						beforeSubmit : function() {
							// alert("我在提交表单之前被调用！");
						},
						success : function(data) {
							alert(data);
							if (data.includes("Update Failed")
									|| data.includes("Nothing Updated")) {

							} else {
								
								//location.href = "unConfirmedSellOrOBOPack.jsp";
							}
						}
					});

		}
	</script>


</body>
</html>