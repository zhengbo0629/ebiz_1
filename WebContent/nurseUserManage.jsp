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
		if (user == null || !user.isDoctor()&&!user.getUserPermissions().contains(EbizUserPermissionEnum.UserManage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany currentCompany= (EbizCompany) session.getAttribute("currentCompany");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div
			id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>用户管理</h3>
			</div>
			<div style="width: 100%; overflow: auto;">

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
					List<EbizUser> allUsers=EbizSql.getInstance().readAllSubUsersForCompany(currentCompany.companyName,   offset, pagesize);
					//List<EbizUser> allActiveDoctorUsers=EbizSql.getInstance().readActiveDoctorUsers(offset, pagesize);
				%>
				<table style="table-layout:fixed;word-wrap: break-word;font-size: 11px">
					<col width=2%>
					<col width=25px>
					<col width=3%>
					<col width=4%>
					<col width=4%>
					<col width=22px>
					<col width=32px>
					<col width=3%>
					<col width=3%>
					<col width=2%>
					<col width=2%>
					<tr>
						<th>UID</th>
						<th>UserName<br>Company
						</th>
						<th>FirstName<br>LastName</th>
						<th>Email<br>PhoneNumber
						</th>
						<th>Address</th>
						<th>UserType
						</th>
						<th>CreatedTime<br>UpdatedTime
						</th>
						<th >Status</th>
						<th>Note</th>
						<th>Balance</th>
						<th>Update</th>
					</tr>
					<%
							if (allUsers != null) {
								for (int i = 0; i < allUsers.size(); i++) {
									EbizUser row = allUsers.get(i);
						%>
					<tr>
						<td><%=row.UID%></td>
						<td ><%=row.userName%><br><%=row.companyName%></td>
						<td><%=row.firstName%><br><%=row.lastName%></td>
						<td><%=row.getEmail()%><br><%=row.phoneNumber%></td>
						<td><%=row.address%></td>
						<td><%=row.getUserType()%></td>
						<td  style="font-size: 9px"><%=row.createTime%><br><%=row.updateTime%></td>

						<td style="font-size: 10px"><%=row.getStatus()%></td>
						<td  style="font-size: 10px"><%=row.note%></td>
						<td><%=row.balance%></td>
						<td><form action="updateNurseUserInfor.jsp" method="POST">
						<input type="hidden" name="userID" value="<%=row.UID%>" /><input
									type="submit" style="width: 50px; height:35px;font-size: 13px"
									name="submit-btn" value="Update" />
							</form></td>


					</tr>
					<%
							}
							}
						%>
				</table>
			</div>
			<%
					int prevpage = pageNumber - 1;
					if (prevpage < 1) {
						prevpage = 1;
					}
					int nextpage = pageNumber + 1;
				%>
			<div style="text-align: right; margin-right: 0px">
				<br>
				<h4>
					<a href='nurseUserManage.jsp?PageNumber=<%=prevpage%>'>PrevPage</a><a>
					</a><a href='nurseUserManage.jsp?PageNumber=<%=nextpage%>'>NextPage</a>
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