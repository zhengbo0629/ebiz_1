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

	<div id="wrapper">

		<div
			style="width: 84%; float: left; border: 0px solid #d7d7d7; min-height: 400px;">

			<div
				style="clear: both; float: left; padding-bottom: 5px; width: 100%;">
				仓库：<select
					name="warehouse"
					id="warehouse">
					<option selected="selected" value="0">请选择</option>
					<option value="10">OR-A</option>
					<option value="40">DE-A</option>

				</select> 运单号：<input
					name="tracking"
					type="text"
					id="tracking" />
				预报人：<select
					name="yubaoren"
					id="yubaoren">
					<option selected="selected" value="">请选择</option>
					<option value="5082">mary</option>
				</select> UPC：<input
					name="upccode"
					type="text"
					id="upccode" />
				入库标签：<input
					name="tag"
					type="text"
					id="tag" />
				<br /> 商品：<select
					name="product"
					id="product">
					<option selected="selected" value="">请选择</option>
					<option value="24939">KRWATCHMV1，Walmart apple watch
						Series 1 38mm Sport white，190198131515，$156.00</option>

				</select> <input type="submit"
					name="search"
					value="查询"
					id="search"
					class="btnType" /> <input type="submit"
					name="output"
					value="导出"
					id="output"
					class="btnType" />
			</div>

			<table style="margin-top: 10px;" class="table_border">

				<tr class="table_row_highlighted">
					<td>209162 <input type="hidden"
						name="hsid"
						id="hsid"
						value="209162" />
					</td>
					<td><span style="color: red;">OR-A</span><br />
						TBA110965590000003</td>
					<td><span style="color: red;">C202SA-YS01</span><br /> ASUS
						Chromebook 11.6" (Celeron 2GB, 16GB eMMC, Dark Blue)</td>
					<td>889349335246 <br /> ASUS
					</td>
					<td></td>
					<td>130.00</td>
					<td>1</td>
					<td>130.00</td>
					<td><br /> 2018/7/22 9:33:02<br /> <span style="color: red;">2018/7/22
							9:33:01</span></td>
				</tr>

				<tr>
					<td colspan="10" class="alignRight">
						<div style="float: left;"></div>
						<div style="float: right;">

							<div style="text-align: right; clear: both; padding: 2px;">
								共2534条<span style='color: #ccc;'>，</span> <a
									id="firstpage"
									href="firstpage">首页</a>
								<span style='color: #ccc;'>|</span> <a
									id="previousepage"
									href="previousepage">上一页</a>
								<span style='color: #ccc;'>|</span> 转到：<!--  <select
									name="topage"
									onchange="topage 0)"
									id="topage"
									style="width: 70px;">
									<option selected="selected" value="1">1</option>
									<option value="2">2</option>

								</select> --> / 26 <a
									id="nextpage"
									href="nextpage">下一页</a>
								<span style='color: #ccc;'>|</span> <a
									id="lastpage"
									href="lastpage">末页</a>
							</div>

						</div>

					</td>
				</tr>
			</table>
		</div>
		<div
			style="width: 13%; float: right; border-left: 1px solid #808080; border-right: 1px solid #808080; color: #5c5c5c; background-color: #f1f1f1;">

			<div
				style="clear: both; float: left; line-height: 26px; width: 100%; min-height: 600px;">

				<div
					style="height: 91px; width: 100%; line-height: 26px; border-bottom: 1px solid #dcdada;"
					class="menu_Title">
					<div
						style="clear: both; float: left; width: 100%; text-align: center; color: #ff0200;">
						gonewlife</div>
					<div
						style="clear: both; float: left; width: 100%; text-align: center;"
						class="menu_Title_sub">公司编码：TVWSO</div>
					<div
						style="clear: both; float: left; width: 100%; text-align: center;">
						<input type="button"
							name="center"
							value="会员中心"
							onclick=""
							class="btnType" />
						<input type="button"
							name="logout"
							value="退出"
							onclick=""
							id="logout"
							class="btnType" />
					</div>
				</div>

				<div
					style="width: 100%; line-height: 30px; padding-top: 20px; font-size: 14px;">
					<div
						style="clear: both; float: left; clear: both; height: 29px; width: 130px; background-color: #c2ccd3; margin-left: 5px; border-radius: 5px;">
						<div
							style="float: left; color: #ff0200; padding-left: 5px; padding-bottom: 5px;">业务管理</div>
					</div>
					<div
						style="float: left; clear: both; padding-top: 5px; padding-bottom: 5px;">

						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/ReadyInputAdd.aspx'>入库预报</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/ReadyInputList.aspx'>预报列表</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/WaitGetPackageList.aspx'>待确认包裹</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/PackageList.aspx'>在库包裹</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/ReportDailyInput.aspx'>每天收货报告</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/ReadySendAdd.aspx'>提交预发货</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/ReadySendList.aspx'>预发货管理</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/SendList.aspx'>已出库管理</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/PackageReadyPayList.aspx'>待结算包裹</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/PackagePayList.aspx'>结算明细</a>
						</div>
					</div>
				</div>

				<div
					style="width: 100%; line-height: 30px; padding-top: 20px; font-size: 14px;">
					<div
						style="clear: both; float: left; clear: both; height: 29px; width: 130px; background-color: #c2ccd3; margin-left: 5px; border-radius: 5px;">
						<div
							style="float: left; color: #ff0200; padding-left: 5px; padding-bottom: 5px;">财务管理</div>
					</div>
					<div
						style="float: left; clear: both; padding-top: 5px; padding-bottom: 5px;">
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/FinanceList.aspx'>财务明细</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/FinanceAdd.aspx'>在线充值</a>
						</div>

					</div>
				</div>
				<div
					style="width: 100%; line-height: 30px; padding-top: 20px; font-size: 14px;">
					<div
						style="clear: both; float: left; clear: both; height: 29px; width: 130px; background-color: #c2ccd3; margin-left: 5px; border-radius: 5px;">

						<div
							style="float: left; color: #ff0200; padding-left: 5px; padding-bottom: 5px;">信息管理</div>
					</div>
					<div
						style="float: left; clear: both; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;">
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/CompanyInfo.aspx'>公司信息</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/WHList.aspx'>仓库查看</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/MyInfo.aspx'>账户信息</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/ChangePasswd.aspx'>修改密码</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/AccountList.aspx'>子帐号管理</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/OrderList.aspx'>商品库存与管理</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/OrderMoveOutList.aspx'>卖出管理</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/OrderMoveInList.aspx'>买进管理</a>
						</div>
						<div style="float: left; clear: both; padding-left: 21px;">
							<a href='/Member/OrderListWHFee.aspx'>仓储费用明细</a>
						</div>
					</div>
				</div>


			</div>
		</div>

	</div>


</body>
</html>
