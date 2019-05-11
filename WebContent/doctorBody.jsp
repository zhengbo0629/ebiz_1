
<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
<%@page import="dataCenter.EbizSql"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>

<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="ebizConcept.EbizUser"%>


<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; GBK" />
<title></title>
<link rel="stylesheet"
	href="styles.css?v=<%=System.currentTimeMillis()%>" type="text/css"
	charset="GBK" />
</head>

<body>
	<%
		EbizUser user = (EbizUser) session.getAttribute("currentUser");
		if (user == null) {
			response.sendRedirect("index.jsp");
			//return;
		}
		EbizCompany currentCompany=(EbizCompany) session.getAttribute("currentCompany");
	%>
	<div id="wrapper">
		<div id="wrapperSideBar">

			<%
					boolean isAdministrator = false;
					boolean isDoctor = false;
					boolean isSelfEmployedDoctor = false;
					boolean isNurse = false;
					boolean isOverseaUser = false;
					//如果账户拥有管理员权限
					//if (user != null && user.getUserPermissions().contains(EbizUserPermissionEnum.Administrator.getName())) {

					if (user != null && EbizSql.getInstance().isAdministrator(user)) {
						isAdministrator = true;
					}
					if (user != null && user.getUserType().equals(EbizUserTypeEnum.Doctor.getName())) {
						isDoctor = true;
					}
					if (user != null
							&& user.getUserType().toLowerCase().contains(EbizUserTypeEnum.Nurse.getName().toLowerCase())) {
						isNurse = true;
					}
					if (user != null && user.getUserType().equals(EbizUserTypeEnum.SelfEmployedDoctor.getName())) {
						isSelfEmployedDoctor = true;
					}
					if (user != null && user.getUserType().equals(EbizUserTypeEnum.Oversea_Buyer.getName())) {
						isOverseaUser = true;
					}
				%>
			<%
					if (isAdministrator) {
				%>



			<div class="bodySideTitle">网站业务管理</div>



			<%
							if (isAdministrator
										|| user.getUserPermissions().contains(EbizUserPermissionEnum.DoctorAccountManage.getName())) {
						%>
			<div class="bodySideContent" class="bodySideContent">
				<a href='doctorUserManage.jsp'><%=EbizUserPermissionEnum.DoctorAccountManage.getChinese()%></a>
			</div>

			<%
							}
						%>
			<%
							if (isAdministrator
										|| user.getUserPermissions().contains(EbizUserPermissionEnum.ChargeManage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='doctorUserBalanceManage.jsp'><%=EbizUserPermissionEnum.ChargeManage.getChinese()%></a>
			</div>
			<%
							}
						%>


			<%
					}
				%>
			<%
					if (isAdministrator || isDoctor || isSelfEmployedDoctor||isNurse
							) {
				%>
			<%
					if (isAdministrator || isDoctor || user.getUserPermissions()
								.contains(EbizUserPermissionEnum.SystemDealSubscriptionManager.getName())) {
				%>


			<div class="bodySideTitle"><%=EbizUserPermissionEnum.SystemDealSubscriptionManager.getChinese()%></div>



			<%
							if (isAdministrator || isDoctor) {
						//医生能看到这个icon，但是如果没订阅的话，点击进去会出现你没有订阅，请先订阅
						%>
			<div class="bodySideContent">
				<a href='autoEmailSystemDeal.jsp'><%=EbizUserPermissionEnum.AutoEmailDealSubscription.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.HotProductList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.HotProductList.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator || isDoctor || user.getUserPermissions()
											.contains(EbizUserPermissionEnum.RecommendedProductList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.RecommendedProductList.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.AllProductList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.AllProductList.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator || isDoctor || user.getUserPermissions()
											.contains(EbizUserPermissionEnum.MyProductSubscriptionList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.MyProductSubscriptionList.getChinese()%></a>
			</div>
			<%
							}
						%>

			<%
					}
				%>
			<%
					if (isAdministrator
								|| user.getUserPermissions().contains(EbizUserPermissionEnum.DealMarketManager.getName())) {
				%>

			<div class="bodySideTitle"><%=EbizUserPermissionEnum.DealMarketManager.getChinese()%></div>

			<%
							if (isAdministrator ||isDoctor|| user.getUserPermissions()
											.contains(EbizUserPermissionEnum.MyPublishedDealList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.MyPublishedDealList.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator ||isDoctor|| user.getUserPermissions()
											.contains(EbizUserPermissionEnum.AllPublishedDealList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.AllPublishedDealList.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator ||isDoctor|| user.getUserPermissions()
											.contains(EbizUserPermissionEnum.MyDealPriceInforSetup.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.MyDealPriceInforSetup.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator ||isDoctor|| user.getUserPermissions()
											.contains(EbizUserPermissionEnum.MyDealSubscriptionUserList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.MyDealSubscriptionUserList.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator ||isDoctor|| user.getUserPermissions()
											.contains(EbizUserPermissionEnum.SubscriptionMyDealUserList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.SubscriptionMyDealUserList.getChinese()%></a>
			</div>
			<%
							}
						%>

			<%
							if (isAdministrator ||isDoctor|| user.getUserPermissions()
											.contains(EbizUserPermissionEnum.MyDealSubscriptionDealList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.MyDealSubscriptionDealList.getChinese()%></a>
			</div>
			<%
							}
						%>

			<%
					}
				%>

			<%
					if (isAdministrator || isDoctor || isSelfEmployedDoctor
								|| user.getUserPermissions().contains(EbizUserPermissionEnum.CompanyInforManager.getName())) {
				%>

			<div class="bodySideTitle"><%=EbizUserPermissionEnum.CompanyInforManager.getChinese()%></div>

			<%
							if (isAdministrator || isDoctor || isSelfEmployedDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.CompanyInforManage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='companyAccountSetting.jsp'><%=EbizUserPermissionEnum.CompanyInforManage.getChinese()%></a>
			</div>
			<%
							}
						%>

			<%
							if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.CompanyFinancial.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='/Member/FinanceList.aspx'><%=EbizUserPermissionEnum.CompanyFinancial.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.PayCompanyBill.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='/Member/FinanceList.aspx'><%=EbizUserPermissionEnum.PayCompanyBill.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.UserManage.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='nurseUserManage.jsp'><%=EbizUserPermissionEnum.UserManage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.DeleteUser.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='deleteUser.jsp'><%=EbizUserPermissionEnum.DeleteUser.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.UserAnalysis.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='/Member/FinanceList.aspx'><%=EbizUserPermissionEnum.UserAnalysis.getChinese()%></a>
			</div>
			
			<%
							}}
						%>

			<%
					if (isAdministrator || isDoctor || isSelfEmployedDoctor
								|| user.getUserPermissions().contains(EbizUserPermissionEnum.CompanyProductManager.getName())) {
				%>

			<div class="bodySideTitle"><%=EbizUserPermissionEnum.CompanyProductManager.getChinese()%></div>

			<%
							if (isAdministrator || isDoctor || isSelfEmployedDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.ProductManage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='allProductManage.jsp'><%=EbizUserPermissionEnum.ProductManage.getChinese()%></a>
			</div>
			<%
							}
						%>

			<%
							if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.DealManage.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='allDealSendToNurseManage.jsp'><%=EbizUserPermissionEnum.DealManage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
				if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.PackageManage.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='allPackForCompany.jsp'><%=EbizUserPermissionEnum.PackageManage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
				if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.InventoryManage.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='inventory.jsp'><%=EbizUserPermissionEnum.InventoryManage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
						if (isAdministrator || isDoctor || isSelfEmployedDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.CheckPackage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='allUnCheckedPackForCompany.jsp'><%=EbizUserPermissionEnum.CheckPackage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator || isDoctor || isSelfEmployedDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.MakeLabel.getName())) {
						%>
			<div class="bodySideContent">
				<a href='allUnLabeledPackForCompany.jsp'><%=EbizUserPermissionEnum.MakeLabel.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator || isDoctor || isSelfEmployedDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.PayPackage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='allUnPaidPackForCompany.jsp'><%=EbizUserPermissionEnum.PayPackage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
											|| user.getUserPermissions().contains(EbizUserPermissionEnum.SendEmail.getName()))) {
						%>
			<div class="bodySideContent">
				<a href='sendEmail.jsp'><%=EbizUserPermissionEnum.SendEmail.getChinese()%></a>
			</div>
			<%
							}}
								
						%>

			<%
					}
				%>
			<%
					if (isAdministrator || isDoctor || isSelfEmployedDoctor
							|| isNurse||user.getUserPermissions().contains(EbizUserPermissionEnum.NursePackageManager.getName())) {
				%>


			<div class="bodySideTitle"><%=EbizUserPermissionEnum.NursePackageManager.getChinese()%></div>


			<div class="bodySideContent">
				<a href='reportPackage.jsp'>领票/预报</a>
			</div>
			<div class="bodySideContent">
				<a href='sellorobo.jsp'>求收购/议价</a>
			</div>
			<div class="bodySideContent">
				<a href='unReceivedPacAndUnConfirmed.jsp'>更新包裹信息</a>
			</div>
			<div class="bodySideContent">
				<a href='shippedPackOrUnMatchedPack.jsp'>修改 Tracking Number</a>
			</div>
			<div class="bodySideContent">
				<a href='unPaidPac.jsp'>更新信用卡信息</a>
			</div>
			<div class="bodySideContent">
				<a href='packingPac.jsp'>打包包裹</a>
			</div>
			<%
							if (isAdministrator || isDoctor || isSelfEmployedDoctor
										|| user.getUserPermissions().contains(EbizUserPermissionEnum.LiveDeal.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.LiveDeal.getChinese()%></a>
			</div>
			<%
							}
						%>
			

			<div class="bodySideTitle">查看包裹</div>

			<div class="bodySideContent">
				<a href='allPack.jsp'>所有包裹</a>
			</div>
			<div class="bodySideContent">
				<a href='unConfirmedSellOrOBOPack.jsp'>未确认包裹</a>
			</div>
			<div class="bodySideContent">
				<a href='unReceivedPac.jsp'>未发货包裹</a>
			</div>
			<div class="bodySideContent">
				<a href='shippedPack.jsp'>途中包裹</a>
			</div>
			<div class="bodySideContent">
				<a href='instockPack.jsp'>在家包裹</a>
			</div>
			<div class="bodySideContent">
				<a href='unPaidPac.jsp'>待结算包裹</a>
			</div>
			<div class="bodySideContent">
				<a href='paidPac.jsp'>已结算包裹</a>
			</div>
			<div class="bodySideContent">
				<a href='shipCompletePac.jsp'>邮寄完成包裹</a>
			</div>
			<div class="bodySideContent">
				<a href='unMatchPac.jsp'>不匹配包裹</a>
			</div>

			<%
				}
			%>
			<%
				if (isAdministrator
						|| currentCompany.getPermissions()
								.contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())
						|| user.getUserPermissions().contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())) {
			%>


			<div class="bodySideTitle"><%=EbizUserPermissionEnum.OverSeaBusinessManager.getChinese()%></div>

<%
							if (isAdministrator||isDoctor
										|| user.getUserPermissions().contains(EbizUserPermissionEnum.AcceptPublicTask.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.AcceptPublicTask.getChinese()%></a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>已接受任务</a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>已完成任务</a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator||isDoctor || user.getUserPermissions()
										.contains(EbizUserPermissionEnum.RecommendedProductsList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.RecommendedProductsList.getChinese()%></a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>所有推荐的产品</a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>自己推荐的产品</a>
			</div>

			<%
							}
						%>

			<%
							if (isAdministrator ||isDoctor||isOverseaUser|| user.getUserPermissions()
										.contains(EbizUserPermissionEnum.CheckRecommendedProductsList.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.CheckRecommendedProductsList.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator
										|| isDoctor||isOverseaUser||user.getUserPermissions().contains(EbizUserPermissionEnum.PublishTask.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.PublishTask.getChinese()%></a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>所有个人任务</a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>已发布的任务</a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>已被接受任务</a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>已被完成任务</a>
			</div>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'>个人账户充值</a>
			</div>
			<%
							}
						%>

			<%
					}
				%>

			<%
					if (isAdministrator
							|| currentCompany.getPermissions().contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())) {
				%>

			<%
							if (isDoctor||user.getUserPermissions().contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())) {
						%>
			<div class="bodySideTitle"><%=EbizUserPermissionEnum.WareHouseAccountManager.getChinese()%></div>

			<%
							}
						%>

			<%
							if (isAdministrator
										|| isDoctor||user.getUserPermissions().contains(EbizUserPermissionEnum.ReceivePackage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='receivePackageWeb.jsp'><%=EbizUserPermissionEnum.ReceivePackage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator
										||isDoctor|| user.getUserPermissions().contains(EbizUserPermissionEnum.PackPackage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.PackPackage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator
										||isDoctor|| user.getUserPermissions().contains(EbizUserPermissionEnum.InventoryManage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.InventoryManage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator
										||isDoctor|| user.getUserPermissions().contains(EbizUserPermissionEnum.WareHouseOrderManage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.WareHouseOrderManage.getChinese()%></a>
			</div>
			<%
							}
						%>
			<%
							if (isAdministrator
										|| isDoctor||user.getUserPermissions().contains(EbizUserPermissionEnum.BuyerPackageManage.getName())) {
						%>
			<div class="bodySideContent">
				<a href='livingDeal.jsp'><%=EbizUserPermissionEnum.BuyerPackageManage.getChinese()%></a>
			</div>
			<%
							}
						%>

			<%
					}
				%>

		</div>

	</div>


</body>
</html>
