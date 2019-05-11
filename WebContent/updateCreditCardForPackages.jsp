<%@page import="ebizConcept.EbizPackage"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
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
		if (user == null || !user.isSelfEmployedDoctor()&&!user.isDoctor()&&!user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div
			id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>更新信用卡信息</h3>
			</div>

			<%
				String uidStrings = (String) request.getParameter("unPaidPackagesUid");
				if(uidStrings.endsWith(";")){
					uidStrings=uidStrings.substring(0,uidStrings.length()-1);
				}
				String[] uidPares=uidStrings.split(";");
				String newUidStrings="";
				double totalValue=0;
				for (int i=0;i<uidPares.length;i++){
					String[] temp=uidPares[i].split(",");
					String uid=temp[0];
					int quantity=Integer.parseInt(temp[1]);
					double price=Double.parseDouble(temp[2]);
					newUidStrings=newUidStrings+uid+";";
					totalValue=totalValue+quantity*price;
				}
			%>
			<div align=center>
			<div align=left style="width: 50%; float: center; border: 0px solid #d7d7d7; min-height: 400px;">
		<form name="edit" id="edit" action="EditPacksCreditCardServlet" method="post">
					<br>
					<h4 style="padding-bottom: 0px">
						Package UID:</h4>
					<br>
					<input
						style="width: 420px; border: none; background-color: transparent;font-size:15px"
						type="text" id="unPaidPackagesUid" name="unPaidPackagesUid"
						value="<%=newUidStrings%>" readonly="readonly" /> <br>
					<br>
					<h4 style="padding-bottom: 0px">Total Value is:</h4>
					<input
						style="width: 420px; border: none; background-color: transparent;"
						type="text" id="totalvalue" name="totalvalue"
						value="$<%=totalValue%>" readonly="readonly" /> <br>
	<br>
					<h4 style="padding-bottom: 0px">Please Write CreditCard Information:</h4>
					<br>
					提交后写在这里的内容将会替换掉所有选中包裹的信用卡信息：
					<br>
					第一次使用的信用卡请在信用卡号后面填写开卡银行和银行payment 地址信息：
					<br>
					多张卡的话请在这里计算分配好金额后再提交：
					<br><br>
					<textarea id="creditcard" name="creditcard" style="resize: none;"
						rows="12" cols="58"></textarea><br><br>
		 <br>
			<br><div align=center> <input type="button" name="submit" value="submit" onClick="updatePackage()" /></div>

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
	function updatePackage(){
		
		var creditcardinfor=document.getElementById('creditcard').value;
		if(creditcardinfor.length<1){
			alert("Please input a good credit card information");
			return;
		}


	$("#edit").ajaxSubmit({
		beforeSubmit : function() {
			// alert("我在提交表单之前被调用！");
		},
		success : function(data) {
			alert(data);
			if(data.includes("Update Failed")){
				
			}else {
				
				location.href = "unPaidPac.jsp";
			}
		}
	});

}
	
	
	
	
	
	</script>

</body>
</html>