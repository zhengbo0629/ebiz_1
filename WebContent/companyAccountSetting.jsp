<%@page import="ebizTools.GeneralMethod"%>
<%@page import="nameEnum.EbizCompanyPayPeriodEnum"%>
<%@page import="ebizConcept.EbizCompany"%>
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
<%@ page language="java" contentType="text/html; charset=utf-8"
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
		EbizCompany company = (EbizCompany) session.getAttribute("currentCompany");
		if (company == null) {
			company = EbizSql.getInstance().getCompanyForDoctor(user);
		}
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>公司资料(Company Profile)</h3>

			</div>

			<div class="clear"></div>
			<div align=center
				style="width: 45%; float: left; border: 0px dotted #d7d7d7;">

				<form name="updatepaytimeinfor" id="updatepaytimeinfor"
					action="UpdateCompanyPayTimeInforServlet" method="post">
					<div align="left"
						style="font-size: 14px; padding-bottom: 5px; padding-top: 1px;">
						<h4 style="color: #1e65ea">设置支付时间：</h4>
					</div>
					<div align="center" style="padding-bottom: 1px; padding-top: 1px;">

						<h4 style="color: #ff8000">系统会根据你设置的支付时间自动计算下一个支付日，会显示在所有用户的账户里，年设置为0以后不显示支付时间信息</h4>

					</div>
					<div align="left"
						style="font-size: 14px; padding-bottom: 1px; padding-top: 10px;">
						<h4 style="color: #1e65ea">设置支付周期：(Please chose a pay period)</h4>
					</div>

					<select id="period" name="period"
						style="height: 38px; width: 100%; font-size: 14px">
						<%
							String period = company.getPayPeriod();
							if (period == null) {
								period = "0";
						%>
						<option selected="selected" value="<%=period%>">Please
							choose a period</option>
						<%
							for (EbizCompanyPayPeriodEnum e : EbizCompanyPayPeriodEnum.values()) {
									String value = e.getName();
						%>

						<option value='<%=value%>'><%=value%></option>
						<%
							}
						%>
						<%
							} else {
						%>
						<option value="0">Please choose a product</option>
						<%
							for (EbizCompanyPayPeriodEnum e : EbizCompanyPayPeriodEnum.values()) {
									String value = e.getName();
									if (value.equals(period)) {
						%>

						<option selected="selected" value='<%=value%>'><%=value%></option>
						<%
							} else {
						%>
						<option value='<%=value%>'><%=value%></option>
						<%
							}
								}
							}
						%>
					</select>
					<div align="left"
						style="font-size: 14px; padding-bottom: 1px; padding-top: 10px;">
						<h4 style="color: #1e65ea">设置支付日：(Please chose a example of
							payment day)</h4>
					</div>
					<%
						int year = company.getPayYear();
						int month = company.getPayMonth();
						int day = company.getPayDay();
					%>
					年：<input type="number" name="year" id="year" value="<%=year%>"
						style="height: 20px; width: 80px" /> 月：<input type="number"
						name="month" id="month" value="<%=company.getPayMonth()%>"
						style="height: 20px; width: 80px" min="1" max="12" /> 日：<input
						type="number" name="day" id="day" value="<%=company.getPayDay()%>"
						style="height: 20px; width: 80px" min="1" max="31" /> <br>
					<div align=center>
						<input type="button" id="submitpayinforbutton"
							name="submitpayinforbutton" value="Update Payment Day"
							onClick="submitPayTimeInfor()" />
					</div>

				</form>
			</div>
			<div align=left
				style="width: 53%; float: right; border: 0px dotted #d7d7d7;">
				<form name="updatecompanyaddress" id="updatecompanyaddress"
					action="UpdateCompanyAddressServlet" method="post">
					<div align="left"
						style="font-size: 14px; padding-bottom: 1px; padding-top: 1px;">
						<h4 style="color: #1e65ea">设置公司地址：(Company Address)</h4>
					</div>
					<div align="center" style="padding-bottom: 1px; padding-top: 1px;">

						<h4 style="color: #ff8000">系统会保留三个公司地址名称方便子账号下单报单用(长读小于100)</h4>

					</div>
					<%
						String address1name = company.getAddress1Name();
						String address2name = company.getAddress2Name();
						String address3name = company.getAddress3Name();
						String address1 = company.getAddress1();
						String address2 = company.getAddress2();
						String address3 = company.getAddress3();
						String email = company.getEmail();
						String emailPassword = company.getEmailPassword();

						String phoneNumber = company.getPhoneNumber();
					%>
					<div align=left
						style="width: 32%; float: left; border: 1px solid #d7d7d7">
						地址 1： <input type="text" name="address1name" id="address1name"
							value="<%=address1name%>" style="height: 18px; width: 60%"
							placeholder="Name 1" /><br>
						<textarea name="address1" id="address1"
							style="resize: none; width: 95%; font-size: 15px" rows="3"
							cols="30" placeholder="Address 1"><%=address1%></textarea>
					</div>
					<div align=left
						style="width: 32%; float: left; border: 1px solid #d7d7d7">
						地址 2： <input type="text" name="address2name" id="address2name"
							value="<%=address2name%>" style="height: 18px; width: 60%"
							placeholder="Name 2" /><br>
						<textarea name="address2" id="address2"
							style="resize: none; width: 95%; font-size: 15px" rows="3"
							cols="150" placeholder="Address 2"><%=address2%></textarea>
					</div>
					<div align=left
						style="width: 32%; float: left; border: 1px solid #d7d7d7">
						地址 3： <input type="text" name="address3name" id="address3name"
							value="<%=address3name%>" style="height: 18px; width: 60%"
							placeholder="Name 3" /><br>
						<textarea name="address3" id="address3"
							style="resize: none; width: 95%; font-size: 15px" rows="3"
							cols="150" placeholder="Address 3"><%=address3%></textarea>
					</div>
					<br> Email：&nbsp; <input type="text" name="email" id="email"
						value="<%=email%>" style="height: 18px; width: 35%"
						placeholder="Email" /><input type="text" name="emailPassword"
						id="emailPassword" value="<%=emailPassword%>"
						style="height: 18px; width: 45%" placeholder="email Password" />
					<br> Phone： <input type="text" name="phoneNumber"
						id="phoneNumber" value="<%=phoneNumber%>"
						style="height: 18px; width: 83%" placeholder="Phone Number" /> <br>
					<div align=center>
						<input type="button" id="submitAddress" name="submitAddress"
							style="width: 83%" value="Update Address, Email And Phone Number"
							onClick="submitAddressFunction()" />
					</div>
				</form>

			</div>
			<br>
			<div class="clear"></div>

			<div align=center>
				<form name="updateusermanual" id="updateusermanual"
					action="UpdateCompanyUserManualServlet" method="post">
					<h4 style="padding-top: 1px; padding-bottom: 1px">用户手册:</h4>
					<div align="center" style="padding-bottom: 1px; padding-top: 1px;">
						<h4 style="color: #ff8000">设置的用户手册内容会作为贵公司所有人的用户手册内容，长读不要超过5000字</h4>
					</div>

					<textarea id="userManual" name="userManual"
						style="resize: none; font-size: 15px" rows="16" cols="150"
						placeholder="User Manual,Length Less Than 5000 Characters"><%=company.getUserManual()%></textarea>
					<br>

					<div align=center>
						<input type="button" id="submitusermanualbutton"
							name="submitusermanualbutton" value="Update User Manual"
							onClick="submitUserManual()" />
					</div>
				</form>



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
		function submitPayTimeInfor() {
			$("#updatepaytimeinfor").ajaxSubmit(
					{
						beforeSubmit : function() {
							// alert("我在提交表单之前被调用！");
						},
						success : function(data) {
							alert(data);
							if (data.includes("Update Failed")
									|| data.includes("Nothing Updated")) {
							} else {
								if (data.includes("Sucessfully")) {
									location.reload();
								}
							}
						}
					});
		}

		function submitAddressFunction() {
			// 这个邮箱是用来群发deal用的，所以要输入密码，平常和护士之间的沟通也可以用这个邮箱。
			//护士也可以很方便的通过系统给这个邮箱发送邮件。目前建议用hotmail的邮箱，gmail的邮箱有100个邮件地址的限制，hotmail邮箱也有限制，每天不能发很多
			var email = document.getElementById("email").value;
			var emailPassword = document.getElementById("emailPassword").value;
			if (email.length && email.includes(" ")) {
				alert("你的邮箱不能包含空格，请修改后再提交");
				return;
			}
			if (emailPassword.length && emailPassword.includes(" ")) {
				alert("你的邮箱密码不能包含空格，请修改后再提交");
				return;
			}

			$("#updatecompanyaddress").ajaxSubmit(
					{
						beforeSubmit : function() {
							// alert("我在提交表单之前被调用！");
						},
						success : function(data) {
							alert(data);
							if (data.includes("Update Failed")
									|| data.includes("Nothing Updated")) {

							} else {

								location.reload();
							}
						}
					});

		}
		function submitUserManual() {
			$("#updateusermanual").ajaxSubmit(
					{
						beforeSubmit : function() {
							// alert("我在提交表单之前被调用！");
						},
						success : function(data) {
							alert(data);
							if (data.includes("Update Failed")
									|| data.includes("Nothing Updated")) {

							} else {

								location.reload();
							}
						}
					});

		}
	</script>


</body>
</html>