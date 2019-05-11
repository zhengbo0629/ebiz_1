<%@page import="nameEnum.EbizPackagePayStatusEnum"%>
<%@page import="ebizConcept.EbizCompany"%>
<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="ebizConcept.EbizPackage"%>
<%@page import="nameEnum.EbizCompanyAddressEnum"%>
<%@page import="java.util.ArrayList"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>
<%@page import="java.util.List"%>
<%@page import="nameEnum.EbizUserPermissionEnum"%>
<%@page import="ebizConcept.EbizUser"%>
<%@page import="ebizConcept.EbizProduct"%>
<%@page import="dataCenter.EbizSql"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
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
	EbizCompany currentCompany=(EbizCompany) session.getAttribute("currentCompany");
	if (user == null || !user.isSelfEmployedDoctor()&&!user.isDoctor()&&!user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())) {
		response.sendRedirect("index.jsp");
		return;
	}

		String packageIDStrings = request.getParameter("sendPackagesUID");
		
		String[] uidStrings=packageIDStrings.split(";");
		String uidNewString="";
		List<Integer> uidList=new ArrayList<Integer>();
		for(int i=0;i<uidStrings.length;i++){
			if(uidStrings[0].length()>0){
				uidList.add(Integer.parseInt(uidStrings[i]));
				uidNewString=uidNewString+uidStrings[i]+" ";
			}
		}
		uidNewString=uidNewString.substring(0, uidNewString.length()-1);
		List<EbizPackage> packs = EbizSql.getInstance().findPackages(uidList);
		String userName="";
		String email="";
		if(packs.size()>0){
			userName=packs.get(0).userName;
			email=packs.get(0).email;
		}
		double value=0;
		HashMap<String,EbizProduct> productMap=new HashMap<String,EbizProduct>();
		String packageListInfor="";
		String trackingInfor="";
		String shipID="";
		for (int i=0;i<packs.size();i++){
			String SKU="";
			if(productMap.get(packs.get(i).modelNumber)==null){
				EbizProduct product=EbizSql.getInstance().findProduct(currentCompany.companyName, packs.get(i).modelNumber);
				if(product!=null){
					productMap.put(packs.get(i).modelNumber, product);
				}
				
			}
			if(productMap.get(packs.get(i).modelNumber)!=null){
				SKU=productMap.get(packs.get(i).modelNumber).SKU;
			}
			packageListInfor=packageListInfor+"UID: "+packs.get(i).UID+", SKU: "+SKU+", "+packs.get(i).quantity+" units, "+packs.get(i).modelNumber+", "+packs.get(i).productName+"\n";
			packageListInfor=packageListInfor+packs.get(i).note+"\n";
			trackingInfor=trackingInfor+packs.get(i).trackingNumber.replace("\\?", "\n")+"\n";
			shipID=shipID+packs.get(i).shipID+"\n";	
		}

		EbizUser thisUser=EbizSql.getInstance().findUser(userName);
		String shippingAddress=thisUser.address;
		String lastName=thisUser.lastName;
		String firstName=thisUser.firstName;

		EbizCompany company=(EbizCompany) session.getAttribute("currentCompany");
		request.setCharacterEncoding("GBK");
		request.getSession().setAttribute("packList", packs);
		request.getSession().setAttribute("opUser", thisUser);

		//List<EbizProduct> productLists = new ArrayList<EbizProduct>();
	%>
	<jsp:include page="doctorHead.jsp" />

	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>发送label</h3>

			</div>
			<h4 style="padding-bottom: 0px">
				Package UID:&nbsp;
				<%=uidNewString%>&emsp;&emsp;UserName:&nbsp;<%=userName%>&emsp;&emsp;Email:&nbsp;<%=email%></h4>

					<form name="edit" id="edit" action="sendLabeltoOneRecipientServlet" method="post" enctype="multipart/form-data">
						<br>
				<table class="tablewithoutline">
					<tr>
						<td class="alignRight" style="width: 80px">邮寄地址：</td>
						<td style="font-size: 16px"><input type="text"
							id="balance" name="balance" readonly
							value="<%=shippingAddress%>"
							style="width: 300px" required />&emsp;电子邮件:<input
							type="text" id="productValue" name="productValue" readonly
							value="<%=email%>" 
							style="width: 220px" required />
							&emsp;Last Name:<input
							type="text" id="shouldPay" name="shouldPay" readonly
							value="<%=lastName%>" 
							style="width: 100px" required />
							&emsp;First Name:<input
							type="text" id="nowPay" name="nowPay"
							value="<%=firstName%>"
							style="width: 100px" required /></td>
							
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">包裹信息：</td>
						<td style="font-size: 14px"><textarea id="packagesInfor"
								name="packagesInfor" style="resize: none; font-size: 13px"
								rows="6" cols="150"
								placeholder="Packages Information"><%=packageListInfor%></textarea></td>
					</tr>
					
					<tr>
						<td class="alignRight" style="width: 80px">邮件内容：</td>
						<td style="font-size: 14px"><textarea id="emailContent"
								name="emailContent" style="resize: none; font-size: 13px"
								rows="3" cols="150"
								placeholder="email Content"></textarea></td>
					</tr>
					
					<tr>
						<td class="alignRight" style="width: 80px">Tracking Number：</td>
						<td style="font-size: 14px"><textarea id="trackingInfor"
								name="trackingInfor" style="resize: none; font-size: 13px"
								rows="5" cols="150"
								placeholder="Tracking Number"><%=trackingInfor%></textarea></td>
					</tr>


					<tr>
						<td class="alignRight" style="width: 80px">Ship ID：</td>
						<td style="font-size: 14px"><textarea id="shipId"
								name="shipId" style="resize: none; font-size: 13px"
								rows="3" cols="150"
								placeholder="Ship ID"><%=shipID%></textarea></td>
					</tr>
					<tr>
						<td class="alignRight" style="width: 80px">Labels：</td>
						<td style="font-size: 14px"><input type="file" id="files" name="files[]" multiple />
<output id="list"></output>

						<textarea id="fileNames" name="fileNames"
							style="background-color: transparent; resize: none; font-size: 13px;" rows="5"
							cols="58" placeholder="File Names"></textarea></td>
					</tr>
				</table>

						<div align=center>
							<input type="button" name="submit" value="Send Email"
								onClick="sendEmail()" />
						</div>
				
				<input style="width: 200px" type="hidden" id="packageId"
					name="packageId" value="<%=packageIDStrings%>"
					readonly="readonly" />
			</form>

		</div>
	</div>
	<jsp:include page="doctorBody.jsp" />
	<!--jquery需要引入的文件-->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.js"></script>

	<!--ajax提交表单需要引入jquery.form.js-->
	<script type="text/javascript"
		src="http://malsup.github.io/jquery.form.js"></script>

	<script type="text/javascript">

	  function handleFileSelect(evt) {
		    var files = evt.target.files; // FileList object
		    // files is a FileList of File objects. List some properties.
		    var output = [];
		    var fileNames="";
		    for (var i = 0, f; f = files[i]; i++) {
		      output.push('<li><strong>',f.name, '</li>');
		      var temp="";
		      if(fileNames.length!=0){
		    	  temp="\n";
		      }
		      fileNames=fileNames.concat(temp,f.name);
		      
		    }
		    document.getElementById('fileNames').value=fileNames;
		  //  document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
		   
		  }
		  document.getElementById('files').addEventListener('change', handleFileSelect, false);
		function sendEmail() {
			  var tracking=document.getElementById('trackingInfor').value;
			    if(tracking.length<=5){
			    	alert("Please input a Good Tracking Number, Click OK button to Complete the payment");
			    	return;
			    }
			 var shipIDString=document.getElementById('shipId').value;
				 if(shipIDString.length<=5){
				    alert("Please input a Good ship ID, Click OK button to Complete the payment");
				    return;
				 }
			    var fileNames=document.getElementById('fileNames').value;

			    if(fileNames.length==0){
					 var msg = "Are you sure want to send email without attachement？\n\n Please confirm！"; 
					 if (confirm(msg)==false){ 
					  return; 
					 }
			    }
				$("#edit")
						.ajaxSubmit(
								{
									beforeSubmit : function() {
										// alert("我在提交表单之前被调用！");
									},
									success : function(data) {
										alert(data);
										if(!data.includes("Failed")){
											location.href = "allCurrentLabelingTasksForUser.jsp";
										}else{
											alert("Something failed, please try it again");
										}
									}
								});

			}
	</script>

</body>
</html>