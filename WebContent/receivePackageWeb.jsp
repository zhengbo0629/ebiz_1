<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="ebizConcept.EbizCompany"%>
<%@page import="java.util.ArrayList"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>
<%@page import="java.util.List"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="nameEnum.ProductConditionColumnEnum"%>
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
		if (user == null || !user.isSelfEmployedDoctor()&&!user.isDoctor()&&!user.getUserPermissions().contains(EbizUserPermissionEnum.ReceivePackage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany currentCompany = (EbizCompany) session.getAttribute("currentCompany");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>接收包裹(Receive Package)</h3>
			</div>
				<div id="threeBox">
				<div class="alignleft">

				</div>
				<div class="aligncenter">

				</div>
				<div class="alignright">
					<div style="height: 8px"></div>
					<h5>
						<input onclick="location.href='receivedPackageForUser.jsp';" type="button" value="已经收取包裹"
							name="updateAll"
							style="margin-top: 0px; width: 90px; font-size: 12px; height: 30px; background-color: #ffcebf">
						<input onclick="location.href='receivePackageWeb.jsp';" type="button" value="接收包裹"
							name="updateAll"
							style="margin-top: 0px; width: 90px; font-size: 12px; height: 30px; background-color: #ffcebf">
					</h5>
				</div>
			</div>
			<div style="width: 100%; overflow: auto;margin-left: 250px" align="left">
<br>

<form name="packageinfor" id="packageinfor" action="ReceivedPackageServlet" method="post">
<input type="text" name="trackingNumber" id="trackingNumber" onkeydown="trackingchange(event)" placeholder="Tracking Number" style="width: 300px;" />
<input type="text" name="recipient"id="recipient"  onkeydown="recipientchange(event)" placeholder="recipient Name" style="width: 150px;" required />
<input type="text" name="CompanyName" id="CompanyName"  onkeydown="CompanyNameChange(event)" placeholder="Company Name" style="width: 150px;" />

<br>
<br>

<input type="number" name="UPC1" id="UPC1" placeholder="UPC1" onkeydown="upc1change(event)" style="width: 300px;"/>

								<label for="condition1">Condition:</label>
							<select name="condition1" id="condition1"
								style="font-size: 12px;width: 120px; height: 36px; margin-top: 2px; border: 1px solid #f0bfca;">
								<option selected="selected" value="<%=ProductConditionColumnEnum.New.getName()%>"><%=ProductConditionColumnEnum.New.getName()%></option>
								<%
								for(ProductConditionColumnEnum temp:ProductConditionColumnEnum.values()){
									if(!temp.getName().equals(ProductConditionColumnEnum.New.getName())){
									%>
									<option value='<%=temp.getName()%>'><%=temp.getName()%></option>
									<%
								}}
								
								%>

							</select> 


<input type="number" name="quantity1" id="quantity1" onkeydown="quantity1change(event)" placeholder="quantity1" style="width: 100px;" />
<br>
<textarea name="serialNumber1" id="serialNumber1"
								placeholder="Serial Number for package 1"
								style="width: auto;" rows="6" cols="40" /></textarea>
<textarea name="productname1" id="productname1" onkeydown="productName1change(event)" placeholder="productname1" style="width: auto;" rows="6" cols="40"/></textarea>
<br>
<br>


<input type="number" name="UPC2" id="UPC2" placeholder="UPC2" onkeydown="upc2change(event)" style="width: 300px;" />

								<label for="condition2">Condition:</label>
							<select name="condition2" id="condition2"
								style="font-size: 12px;width: 120px; height: 36px; margin-top: 2px; border: 1px solid #f0bfca;">
								<option selected="selected" value="<%=ProductConditionColumnEnum.New.getName()%>"><%=ProductConditionColumnEnum.New.getName()%></option>
								<%
								for(ProductConditionColumnEnum temp:ProductConditionColumnEnum.values()){
									if(!temp.getName().equals(ProductConditionColumnEnum.New.getName())){
									%>
									<option value='<%=temp.getName()%>'><%=temp.getName()%></option>
									<%
								}}
								
								%>

							</select> 
<input type="number" name="quantity2" id="quantity2" onkeydown="quantity2change(event)" placeholder="quantity2" style="width: 100px;"/>
<br>
<textarea name="serialNumber2" id="serialNumber2"
								placeholder="Serial Number for package 2"
								style="width: auto;" rows="3" cols="40" /></textarea>
<textarea name="productname2" id="productname2" onkeydown="productName2change(event)" placeholder="productname2" style="width: auto;" rows="3" cols="40"/></textarea>
<br>
<br>

<input type="number" name="UPC3" id="UPC3" onkeydown="upc3change(event)" placeholder="UPC3"  style="width: 300px;"/>
								<label for="condition3">Condition:</label>
							<select name="condition3" id="condition3"
								style="font-size: 12px;width: 120px; height: 36px; margin-top: 2px; border: 1px solid #f0bfca;">
								<option selected="selected" value="<%=ProductConditionColumnEnum.New.getName()%>"><%=ProductConditionColumnEnum.New.getName()%></option>
								<%
								for(ProductConditionColumnEnum temp:ProductConditionColumnEnum.values()){
									if(!temp.getName().equals(ProductConditionColumnEnum.New.getName())){
									%>
									<option value='<%=temp.getName()%>'><%=temp.getName()%></option>
									<%
								}}
								
								%>

							</select> 
<input type="number" name="quantity3" id="quantity3" onkeydown="quantity3change(event)" placeholder="quantity3" style="width: 100px;"/>
<br>
<textarea name="serialNumber3" id="serialNumber3"
								placeholder="Serial Number for package 3"
								style="width: auto;" rows="3" cols="40" /></textarea>
<textarea name="productname3" id="productname3" onkeydown="productName3change(event)" placeholder="productname3" style="width: auto;" rows="3" cols="40"/></textarea>
<br>
<br>
</form>



			</div>
<div align="center" style="text-align: center;">
<input type="button" name="submit" value="submit" onClick="checkprocess()"/>
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
function trackingchange(event){
	if (event.key == 'Enter')
	document.getElementById("recipient").focus();
}
function recipientchange(event){
	if (event.key == 'Enter')
	document.getElementById("CompanyName").focus();
}
function CompanyNameChange(event){
	if (event.key == 'Enter')
	document.getElementById("UPC1").focus();
}
function productName1change(event){
	if (event.key == 'Enter')
	document.getElementById("UPC2").focus();
}
function productName2change(event){
	if (event.key == 'Enter')
	document.getElementById("UPC3").focus();
}
function productName3change(){
	//document.getElementById("UPC4").focus();
}

function quantity1change(event){
	//if (document.getElementById("productname1").value.length==0){
	//	document.getElementById("productname1").focus();
	//	alert('please input a product name for upc1');
	//}else{
		if (event.key == 'Enter'){
			var upc=document.getElementById("UPC1").value;
			var quantity1=document.getElementById("quantity1").value;
			if(upc.length>0 &&quantity1.length==0){
				alert('please input a good quantity1');
				return;
			}
			document.getElementById("serialNumber1").focus();
		}
	//}
	
}
	function quantity2change(event){
		
		if (event.key == 'Enter'){
			var upc=document.getElementById("UPC2").value;
			var quantity2=document.getElementById("quantity2").value;
			if(upc.length>0 &&quantity2.length==0){
				alert('please input a good quantity2');
				return;
			}
			document.getElementById("serialNumber2").focus();
		}

		
	}
		function quantity3change(event){
			if (event.key == 'Enter'){
				var upc=document.getElementById("UPC3").value;
				var quantity3=document.getElementById("quantity3").value;
				if(upc.length>0 &&quantity3.length==0){
					alert('please input a good quantity3');
					return;
				}
			}
			document.getElementById("serialNumber3").focus();
			
		}

function upc1change(event){
	if (event.key == 'Enter'){
	var upc=document.getElementById("UPC1").value;

	if(upc.length>0 && upc.length!=12){
		alert('please input a good upc1');
		return;
	}
	
	document.getElementById("quantity1").focus();	
}
}
	function upc2change(event){
		if (event.key == 'Enter'){
		var upc=document.getElementById("UPC2").value;
		if(upc.length>0 && upc.length!=12){
			alert('please input a good upc2');
			return;
		}

		document.getElementById("quantity2").focus();
		}
	}
	function upc3change(event){
		if (event.key == 'Enter'){
		var upc=document.getElementById("UPC3").value;

		if(upc.length>0 && upc.length!=12){
			alert('please input a good upc3');
			return;
		}

		document.getElementById("quantity3").focus();	
		}
	}

function checkprocess(){

	var trackingnumber=document.getElementById("trackingNumber").value;
	if(trackingnumber.length<=5){
		alert('please input a good tracking number');
		return;
	}
	var recipient=document.getElementById("recipient").value;
	if(recipient.length<2){
		alert('please input recipient name');
		return;
	}
	var CompanyName=document.getElementById("CompanyName").value;
	if(CompanyName.length==0){
		alert('please input Identification code');
		return;
	}
	
	var upc1=document.getElementById("UPC1").value;
	//var isNumber = /^\d+\.\d+$/.test(upc1);
	//alert(upc1);
	//alert(upc1.length);
	if(upc1.length!=12){
		alert('please input a good upc1');
		return;
	}
	var quantity1=document.getElementById("quantity1").value;

	if(quantity1.length==0){
		alert('please input a good quantity1');
		return;
	}
	//if(document.getElementById("productname1").value.length==0){
	//	alert('please input a product name for upc1');
	//}	
	
	var upc2=document.getElementById("UPC2").value;
	var quantity2=document.getElementById("quantity2").value;


	if(upc2.length>0 && upc2.length!=12){
		alert('please input a good upc2');
		return;
	}else if (upc2.length==12 && quantity2.length==0){
		
		alert('please input a good quantity2');
		return;
	}else if (upc2.length==12){
		if (upc1===upc2){
			alert('upc1 equals upc2, please check');
			return;
		}
		//if(document.getElementById("productname2").value.length==0){
		//	alert('please input a product name for upc2');
		//}
	}
	var upc3=document.getElementById("UPC3").value;
	var quantity3=document.getElementById("quantity3").value;

	if(upc3.length>0 && upc3.length!=12){
		alert('please input a good upc3');
		return;
	}else if(upc3.length==12 && quantity3.length==0){

		alert('please input a good quantity3');
		return;
	}else if (upc3.length==12){
		if (upc1===upc3){
			alert('upc1 equals upc3, please check');
			return;
		}
		if (upc2===upc3){
			alert('upc2 equals upc3, please check');
			return;
		}
		//if(document.getElementById("productname3").value.length==0){
		//	alert('please input a product name for upc3');
		//}
	}

	$("#packageinfor").ajaxSubmit({
		beforeSubmit : function() {
			// alert("我在提交表单之前被调用！");
		},
		success : function(data) {
			alert(data);
			if(data.includes("No Package Added")){
				
			}else {
				location.href = "receivePackageWeb.jsp";
			}
		}
	});

	
}
</script>
</body>
</html>





