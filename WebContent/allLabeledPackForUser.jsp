<%@page import="nameEnum.EbizPackageCheckStatusEnum"%>
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
				&& !user.getUserPermissions().contains(EbizUserPermissionEnum.MakeLabel.getName())) {
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
			List<EbizPackage> shippedPackages = EbizSql.getInstance().readAllLabeledkagesForUser(user, company,offset,
					pagesize);
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
				<h3 style="margin-top: 5px;">������ɵ�Label����(All Finished Label
					Tasks)</h3>
			</div>

			<div id="threeBox">
				<div class="alignleft">

				</div>
				<div class="aligncenter">

				</div>
				<div class="alignright">
					<div style="height: 8px"></div>
					<h5>
						<input onclick="unTakenTasks()" type="button" value="δ��ȡ����"
							name="updateAll"
							style="margin-top: 0px; width: 90px; font-size: 12px; height: 30px; background-color: #ffcebf">
						<input onclick="labelingTasks()" type="button" value="����ȡ����"
							name="updateAll"
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
					<col width=70px>
					<col width=38px>
					<col width=85px>
					<col width=120px>
					<col width=143px>
					<tr>
						<th style="white-space: nowrap;"><input id="checkAll"
							type="checkbox" name="checkAll" onclick="toggle(this)" /><label
							for="checkAll">UID</label></th>
						<th style="line-height: 16px;">�û���
						<th style="line-height: 16px;">�ͺ�<br>��Ʒ��
						</th>
						<th style="font-size: 12px; line-height: 16px;">Brand:UPC:<br>SKU:ASIN
						</th>
						<th>����<br>�۸�
						</th>
						<th style="font-size: 12px; line-height: 16px;">��ַ<br>�ʼ�״̬
						</th>

						<th style="font-size: 12px; line-height: 16px;">��������<br>Ship
							Id
						</th>
						<th>״̬:����</th>
					</tr>
					<%
						if (shippedPackages != null) {
							for (int i = 0; i < shippedPackages.size(); i++) {
								EbizPackage row = shippedPackages.get(i);
					%>
					<tr>
						<td style="white-space: nowrap;"><input
							id="check<%=row.UID%>" type="checkbox" name="checkbox"
							value="<%=row.UID%>" /><label for="check<%=row.UID%>"><%=row.UID%></label></td>
							<td style='font-size: 11px; line-height: 14px'><%=row.userName%></td>
						<td style='font-size: 11px; line-height: 14px'><%=row.getModel()%><br><%=row.productName%></td>
						<td style='font-size: 11px; line-height: 13px'><%=row.brand%><br><%=row.UPC%><br><%=row.SKU%><br><%=row.ASIN%></td>
						<td><%=row.quantity%><br><%=row.price%></td>


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
						<%}%>'><%=row.getStatus()%><br>Home</td>
						<%
							}
						%>

						<td
							style='font-size: 12px; line-height: 15px; padding-top: 5px; padding-bottom: 5px'><%=trackingString%></td>


						<td>
						</td>

						<%
							}
							}
						%>
					
				</table>
			</div>

			<div style="text-align: right; margin-right: 0px">
				<br>
				<h4>
					<a
						href='allLabeledPackForUser.jsp?PageNumber=<%=prevpage%>'>PrevPage</a><a>
					</a><a
						href='allLabeledPackForUser.jsp?PageNumber=<%=nextpage%>'>NextPage</a>
				</h4>
			</div>
		</div>
	</div>

	<jsp:include page="doctorBody.jsp" />
	<!--jquery��Ҫ������ļ�-->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.js"></script>

	<!--ajax�ύ����Ҫ����jquery.form.js-->
	<script type="text/javascript"
		src="http://malsup.github.io/jquery.form.js"></script>

	<script type="text/javascript">

		function unTakenTasks() {
			location.href = "allUnLabeledPackForCompany.jsp";
		}
		function labelingTasks() {
			location.href = "allCurrentLabelingTasksForUser.jsp";
		}
	</script>

</body>
</html>