<%@page import="nameEnum.EbizUserTypeEnum"%>
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
		if (user == null || !user.isDoctor()
				&& !user.getUserPermissions().contains(EbizUserPermissionEnum.UserManage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany currentCompany = (EbizCompany) session.getAttribute("currentCompany");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>�����û���Ϣ</h3>
			</div>
			<%
				int userID = Integer.parseInt(request.getParameter("userID"));
				EbizUser tempUser = EbizSql.getInstance().findUser(userID);
				String introducer = "";
				if (tempUser.getParameter(EbizUserParaMeterEnum.Introducer.getName()) != null) {
					introducer = user.getParameter(EbizUserParaMeterEnum.Introducer.getName());
				}
			%>
			<form name="updateNurseInforForm" method="post"
				action="UpdateNurseInforServlet" id="updateNurseInforForm">
				<div
					style="width: 30%; float: left; padding-top: 3px; line-height: 20px;">
					<div
						style="float: left; width: 80px; font-size: 18px; padding-top: 1px;">������Ϣ
					</div>
					<div>
						<table class="tablewithoutline">
							<tr>
								<td class="labelRightAlign" style="width: 80px;">�û�ID��</td>
								<td><input name="userID" type="text" id="userID" required
									style="width: 150px; height: 20px;" value="<%=tempUser.UID%>"
									readonly /></td>
							</tr>
							
							<tr>
								<td class="labelRightAlign" style="width: 80px;">��¼����</td>
								<td><input name="username" type="text" id="username"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.userName%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">�û��ࣺ</td>
								<td><input name="userType" type="text" id="userType"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.getUserType()%>" readonly /></td>
							</tr>

							<tr>
								<td class="labelRightAlign" style="width: 80px;">�գ�</td>
								<td><input name="lastName" type="text" id="lastName"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.lastName%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">����</td>
								<td><input name="firstName" type="text" id="firstName"
									required style="width: 150px; height: 20px;"
									value="<%=tempUser.firstName%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">��ϵ�绰��</td>
								<td><input name="contactPhone" type="text"
									id="contactPhone" required style="width: 150px; height: 20px;"
									value="<%=tempUser.phoneNumber%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">���䣺</td>
								<td><input name="contactEmail" type="text"
									id="contactEmail" required style="width: 150px; height: 20px;"
									value="<%=tempUser.getEmail()%>" readonly /></td>
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">��ַ��</td>
								<td><input name="address" type="text" id="address" required
									style="width: 150px; height: 20px;"
									value="<%=tempUser.address%>" readonly />
							</tr>
							<tr>
								<td class="labelRightAlign" style="width: 80px;">��˾���ƣ�</td>
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
						style="width: 48%; float: left; padding-top: 0px; line-height: 20px;">
						<div
							style="float: left; width: 80px; font-size: 18px; padding-top: 1px;">�޸���Ϣ
						</div>
						<div>
							<table class="tablewithoutline">
								<tr>
									<td class="labelRightAlign" style="width: 80px;">�����ˣ�</td>
									<td><input name="introducer" type="text" id="introducer"
										style="width: 150px; height: 20px;" value="<%=introducer%>" /></td>
								</tr>
								<tr>
									<td class="labelRightAlign" style="width: 80px;">���룺</td>
									<td><input name="password" type="text" id="password"
										style="width: 150px; height: 20px;" /></td>
								</tr>
								<tr>
									<td class="labelRightAlign" style="width: 80px;">�û�����</td>
									<td><select id="usertype" name="usertype"
										style="height: 35px; width: 160px; font-size: 12px">
											<option selected="selected" value="0">Please Chose</option>
											<%
												for (EbizUserTypeEnum type : EbizUserTypeEnum.values()) {
													if (type.getName().toLowerCase().contains("doctor")) {
														continue;
													}
													if (!currentCompany.getPermissions().contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())
															&& type.getName().equals(EbizUserTypeEnum.WarehouseNurse.getName())) {
														continue;
													}
													if (!currentCompany.getPermissions().contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())
															&& type.getName().equals(EbizUserTypeEnum.Oversea_Buyer.getName())) {
														continue;
													}

													if (tempUser.getUserType().equals(type.getName())) {
											%>
											<option selected="selected" value="<%=type.getName()%>"><%=type.getName()%></option>
											<%
												} else {
											%>
											<option value="<%=type.getName()%>"><%=type.getName()%></option>
											<%
												}
												}
											%>

									</select></td>
								</tr>
								<tr>
									<td class="labelRightAlign" style="width: 80px;">�û�״̬</td>
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
									<td class="labelRightAlign" style="width: 80px;">��</td>
									<td><input name="balance" type="number" id="balance"
										value="<%=tempUser.balance%>"
										style="width: 150px; height: 20px;" /></td>
								</tr>
								<tr>
									<td class="labelRightAlign" style="width: 80px;">���˱������ƣ�</td>
									<td><input name="PersonalLimit" type="number" id="PersonalLimit"
										value="<%=tempUser.personalLimit%>" min="0" step="1"
										style="width: 150px; height: 20px;" /></td>
								</tr>
							</table>
						</div>
					</div>
					<div
						style="width: 50%; float: left; padding-top: 3px; line-height: 20px;">
						<div
							style="float: left; width: 180px; font-size: 18px; padding-top: 1px;">�������˻�ҵ��Χ

						</div>
						<div class="clear"></div>
						<%
							if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.SystemDealSubscriptionManager.getName())
									|| EbizUserPermissionEnum.SystemDealSubscriptionManager.getRole().equals("DoctorDefault")
									|| currentCompany.getPermissions().contains(EbizUserPermissionEnum.DealMarketManager.getName())
									|| EbizUserPermissionEnum.DealMarketManager.getRole().equals("DoctorDefault")) {
						%>
						<div style="width: 100%; height: 22px; padding-top: 3px;">Deal ����</div>
						<%
							}
						%>
						<div style="float: left">
							<%
								if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.SystemDealSubscriptionManager.getName())
										|| EbizUserPermissionEnum.SystemDealSubscriptionManager.getRole().equals("DoctorDefault")) {
							%>
							<input
								id="<%=EbizUserPermissionEnum.SystemDealSubscriptionManager.getName()%>"
								value="<%=EbizUserPermissionEnum.SystemDealSubscriptionManager.getName()%>"
								type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions()
						.contains(EbizUserPermissionEnum.SystemDealSubscriptionManager.getName())) {%>
								checked <%}%> /><label
								for="<%=EbizUserPermissionEnum.SystemDealSubscriptionManager.getName()%>">&nbsp;
								<%=EbizUserPermissionEnum.SystemDealSubscriptionManager.getChinese()%></label><br>
							<%
								for (EbizUserPermissionEnum permision : EbizUserPermissionEnum.values()) {
										if (!permision.getGroup().equals("System Deal"))
											continue;
										if (permision.getName().equals(EbizUserPermissionEnum.SystemDealSubscriptionManager.getName()))
											continue;
										if (permision.getName().equals(EbizUserPermissionEnum.AutoEmailDealSubscription.getName())) {
											EbizCompany company = EbizSql.getInstance().findCompany(tempUser.companyName);
											if (company.getPermissions().contains(permision.getName())) {
							%>
							<input style="margin-left: 30px;" id="<%=permision.getName()%>"
								value="<%=permision.getName()%>" type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(permision.getName())) {%>
								checked <%}%>
								<%if (permision.getRole().equals("NurseDefault")) {%>
								onclick="event.preventDefault();" <%}%> /><label
								for="<%=permision.getName()%>">&nbsp;<%=permision.getChinese()%></label><br>
							<%
								}
										}
										if (!permision.getName().equals(EbizUserPermissionEnum.AutoEmailDealSubscription.getName())) {
							%>
							<input style="margin-left: 30px;" id="<%=permision.getName()%>"
								value="<%=permision.getName()%>" type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(permision.getName())) {%>
								checked <%}%>
								<%if (permision.getRole().equals("NurseDefault")) {%>
								onclick="event.preventDefault();" <%}%> /><label
								for="<%=permision.getName()%>">&nbsp;<%=permision.getChinese()%></label><br>
							<%
								}
							%>
							<%
								}
							%>
							<%
								}
							%>
						</div>

						<div style="float: left; margin-left: 20px;">
							<%
								if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.DealMarketManager.getName())
										|| EbizUserPermissionEnum.DealMarketManager.getRole().equals("DoctorDefault")) {
							%>
							<input
								id="<%=EbizUserPermissionEnum.DealMarketManager.getName()%>"
								value="<%=EbizUserPermissionEnum.DealMarketManager.getName()%>"
								type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(EbizUserPermissionEnum.DealMarketManager.getName())) {%>
								checked <%}%> /><label
								for="<%=EbizUserPermissionEnum.DealMarketManager.getName()%>">&nbsp;
								<%=EbizUserPermissionEnum.DealMarketManager.getChinese()%></label><br>
							<%
								for (EbizUserPermissionEnum permision : EbizUserPermissionEnum.values()) {
										if (!permision.getGroup().equals("Deal Market"))
											continue;
										if (permision.getName().equals(EbizUserPermissionEnum.DealMarketManager.getName()))
											continue;
										if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.DealMarketManager.getName())) {
							%>
							<input style="margin-left: 30px;" id="<%=permision.getName()%>"
								value="<%=permision.getName()%>" type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(permision.getName())) {%>
								checked <%}%>
								<%if (permision.getRole().equals("NurseDefault")) {%>
								onclick="event.preventDefault();" <%}%> /><label
								for="<%=permision.getName()%>">&nbsp;<%=permision.getChinese()%></label><br>
							<%
								}
							%>
							<%
								}
							%>
							<%
								}
							%>
						</div>





						<div class="clear"></div>
						<%
							if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.CompanyInforManager.getName())
									|| EbizUserPermissionEnum.CompanyInforManager.getRole().equals("DoctorDefault")
									|| currentCompany.getPermissions().contains(EbizUserPermissionEnum.CompanyProductManager.getName())
									|| EbizUserPermissionEnum.CompanyProductManager.getRole().equals("DoctorDefault")) {
						%>
						<div style="width: 100%; height: 22px; padding-top: 3px;">��˾����</div>
						<%
							}
						%>
						<div style="float: left;">
							<%
								if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.CompanyInforManager.getName())
										|| EbizUserPermissionEnum.CompanyInforManager.getRole().equals("DoctorDefault")) {
							%>
							<input
								id="<%=EbizUserPermissionEnum.CompanyInforManager.getName()%>"
								value="<%=EbizUserPermissionEnum.CompanyInforManager.getName()%>"
								type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(EbizUserPermissionEnum.CompanyInforManager.getName())) {%>
								checked <%}%> /><label
								for="<%=EbizUserPermissionEnum.CompanyInforManager.getName()%>">&nbsp;
								<%=EbizUserPermissionEnum.CompanyInforManager.getChinese()%></label><br>
							<%
								for (EbizUserPermissionEnum permision : EbizUserPermissionEnum.values()) {
										if (!permision.getGroup().equals("Company Information"))
											continue;
										if (permision.getName().equals(EbizUserPermissionEnum.CompanyInforManager.getName()))
											continue;
										if (currentCompany.getPermissions()
												.contains(EbizUserPermissionEnum.CompanyInforManager.getName())) {
							%>
							<input style="margin-left: 30px;" id="<%=permision.getName()%>"
								value="<%=permision.getName()%>" type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(permision.getName())) {%>
								checked <%}%>
								<%if (permision.getRole().equals("NurseDefault")) {%>
								onclick="event.preventDefault();" <%}%> /><label
								for="<%=permision.getName()%>">&nbsp;<%=permision.getChinese()%></label><br>
							<%
								}
							%>
							<%
								}
							%>
							<%
								}
							%>
						</div>



						<div style="float: left; margin-left: 20px;">
							<%
								if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.CompanyProductManager.getName())
										|| EbizUserPermissionEnum.CompanyProductManager.getRole().equals("DoctorDefault")) {
							%>
							<input
								id="<%=EbizUserPermissionEnum.CompanyProductManager.getName()%>"
								value="<%=EbizUserPermissionEnum.CompanyProductManager.getName()%>"
								type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(EbizUserPermissionEnum.CompanyProductManager.getName())) {%>
								checked <%}%> /><label
								for="<%=EbizUserPermissionEnum.CompanyProductManager.getName()%>">&nbsp;
								<%=EbizUserPermissionEnum.CompanyProductManager.getChinese()%></label><br>
							<%
								for (EbizUserPermissionEnum permision : EbizUserPermissionEnum.values()) {
										if (!permision.getGroup().equals("Company Product"))
											continue;
										if (permision.getName().equals(EbizUserPermissionEnum.CompanyProductManager.getName()))
											continue;
										if (currentCompany.getPermissions()
												.contains(EbizUserPermissionEnum.CompanyProductManager.getName())) {
							%>
							<input style="margin-left: 30px;" id="<%=permision.getName()%>"
								value="<%=permision.getName()%>" type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(permision.getName())) {%>
								checked <%}%>
								<%if (permision.getRole().equals("NurseDefault")) {%>
								onclick="event.preventDefault();" <%}%> /><label
								for="<%=permision.getName()%>">&nbsp;<%=permision.getChinese()%></label><br>
							<%
								}
							%>
							<%
								}
							%>
							<%
								}
							%>
						</div>
						<div class="clear"></div>
						<%
							if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.NursePackageManager.getName())
									|| EbizUserPermissionEnum.NursePackageManager.getRole().equals("DoctorDefault")
									|| currentCompany.getPermissions().contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())
									|| EbizUserPermissionEnum.OverSeaBusinessManager.getRole().equals("DoctorDefault")) {
						%>


						<div style="width: 100%; height: 22px; padding-top: 3px;">�������</div>
						<%
							}
						%>
						<div style="float: left">

							<%
								if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.NursePackageManager.getName())
										|| EbizUserPermissionEnum.NursePackageManager.getRole().equals("DoctorDefault")) {
							%>
							<input
								id="<%=EbizUserPermissionEnum.NursePackageManager.getName()%>"
								value="<%=EbizUserPermissionEnum.NursePackageManager.getName()%>"
								type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(EbizUserPermissionEnum.NursePackageManager.getName())) {%>
								checked <%}%> /><label
								for="<%=EbizUserPermissionEnum.NursePackageManager.getName()%>">&nbsp;
								<%=EbizUserPermissionEnum.NursePackageManager.getChinese()%></label><br>
							<%
								for (EbizUserPermissionEnum permision : EbizUserPermissionEnum.values()) {
										if (!permision.getGroup().equals("Nurse Package"))
											continue;
										if (permision.getName().equals(EbizUserPermissionEnum.NursePackageManager.getName()))
											continue;
										if (currentCompany.getPermissions()
												.contains(EbizUserPermissionEnum.NursePackageManager.getName())) {
							%>
							<input style="margin-left: 30px;" id="<%=permision.getName()%>"
								value="<%=permision.getName()%>" type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(permision.getName())) {%>
								checked <%}%> /><label
								for="<%=permision.getName()%>">&nbsp;<%=permision.getChinese()%></label><br>
							<%
								}
							%>
							<%
								}
							%>
							<%
								}
							%>
						</div>




						<div style="float: left; margin-left: 20px;">
							<%
								if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())
										|| EbizUserPermissionEnum.OverSeaBusinessManager.getRole().equals("DoctorDefault")) {
							%>
							<input
								id="<%=EbizUserPermissionEnum.OverSeaBusinessManager.getName()%>"
								value="<%=EbizUserPermissionEnum.OverSeaBusinessManager.getName()%>"
								type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())) {%>
								checked <%}%> /><label
								for="<%=EbizUserPermissionEnum.OverSeaBusinessManager.getName()%>">&nbsp;
								<%=EbizUserPermissionEnum.OverSeaBusinessManager.getChinese()%></label><br>
							<%
								for (EbizUserPermissionEnum permision : EbizUserPermissionEnum.values()) {
										if (!permision.getGroup().equals("OverSea Business"))
											continue;
										if (permision.getName().equals(EbizUserPermissionEnum.OverSeaBusinessManager.getName()))
											continue;
										if (currentCompany.getPermissions()
												.contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())) {
							%>
							<input style="margin-left: 30px;" id="<%=permision.getName()%>"
								value="<%=permision.getName()%>" type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(permision.getName())) {%>
								checked <%}%>
								<%if (permision.getRole().equals("NurseDefault")) {%>
								onclick="event.preventDefault();" <%}%> /><label
								for="<%=permision.getName()%>">&nbsp;<%=permision.getChinese()%></label><br>
							<%
								}
							%>
							<%
								}
							%>
							<%
								}
							%>
						</div>

						<div class="clear"></div>

						<%
							if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())
									|| EbizUserPermissionEnum.WareHouseAccountManager.getRole().equals("DoctorDefault")) {
						%>
						<div style="width: 100%; height: 22px; padding-top: 3px;">�ֿ����</div>
						<%
							}
						%>

						<div>
							<%
								if (currentCompany.getPermissions().contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())
										|| EbizUserPermissionEnum.WareHouseAccountManager.getRole().equals("DoctorDefault")) {
							%>
							<input
								id="<%=EbizUserPermissionEnum.WareHouseAccountManager.getName()%>"
								value="<%=EbizUserPermissionEnum.WareHouseAccountManager.getName()%>"
								type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())) {%>
								checked <%}%> /><label
								for="<%=EbizUserPermissionEnum.WareHouseAccountManager.getName()%>">&nbsp;
								<%=EbizUserPermissionEnum.WareHouseAccountManager.getChinese()%></label><br>
							<%
								for (EbizUserPermissionEnum permision : EbizUserPermissionEnum.values()) {
										if (!permision.getGroup().equals("WareHouse"))
											continue;
										if (permision.getName().equals(EbizUserPermissionEnum.WareHouseAccountManager.getName()))
											continue;
										if (currentCompany.getPermissions()
												.contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())) {
							%>
							<input style="margin-left: 30px;" id="<%=permision.getName()%>"
								value="<%=permision.getName()%>" type="checkbox" name="checkbox"
								<%if (tempUser.getUserPermissions().contains(permision.getName())) {%>
								checked <%}%>
								<%if (permision.getRole().equals("NurseDefault")) {%>
								onclick="event.preventDefault();" <%}%> /><label
								for="<%=permision.getName()%>">&nbsp;<%=permision.getChinese()%></label><br>
							<%
								}
							%>
							<%
								}
							%>
							<%
								}
							%>
						</div>


					</div>
				</div>
				<div class="clear"></div>
				
				
				User Note: <br>
				<%
				String note=tempUser.note;
				if(note==null){
					note="";
				}
				
				%>
				<input style="width: 100%;" id="userNote"
								value="<%=note %>" type="text" name="userNote" />
				
				
				<div style="width: 100%; text-align: center;">
					<input type="button" id="modify" name="modify" value="�޸�"
						style="width: 300px; height: 30px;" onclick="updatePackage()"/>
				</div>
			</form>
		</div>
	</div>


	<jsp:include page="doctorBody.jsp" />

	<!--jquery��Ҫ������ļ�-->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.js"></script>

	<!--ajax�ύ����Ҫ����jquery.form.js-->
	<script type="text/javascript"
		src="http://malsup.github.io/jquery.form.js"></script>

	<script type="text/javascript">
		function updatePackage() {
			$("#updateNurseInforForm").ajaxSubmit({
				beforeSubmit : function() {
					 //alert("�����ύ��֮ǰ�����ã�");
				},
				success : function(data) {
					alert(data);
					if (data.includes("Nothing Updated")) {

					} else {

						location.href = "nurseUserManage.jsp";
					}
				}
			});

		}
	</script>

</body>
</html>