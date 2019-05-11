<%@page import="nameEnum.EbizPackageStatusEnum"%>
<%@page import="nameEnum.EbizNurseGroupTypeEnum"%>
<%@page import="ebizConcept.EbizCompany"%>
<%@page import="ebizConcept.EbizProduct"%>
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
		if (user == null || !user.isSelfEmployedDoctor() && !user.isDoctor()
				&& !user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())) {
			response.sendRedirect("index.jsp");
			return;
		}
		EbizCompany company = (EbizCompany) session.getAttribute("currentCompany");
		request.setCharacterEncoding("GBK");
	%>
	<jsp:include page="doctorHead.jsp" />
	<div id="wrapper">
		<div id="wrapperContent">
			<div align="center" style="padding-bottom: 1px; padding-top: 10px;">
				<h3>发送邮件</h3>
			</div>

			<%
				String idString = request.getParameter("packageid");
				EbizPackage pack = null;
				String UID = "";
				String UPC = "";
				String ASIN = "";
				String SKU = "";
				String Brand = "";
				String model = "";
				String productName = "";
				String status = "";
				String userName="";
				double price = 0;
				if (idString != null && idString.length() != 0) {
					int productID = Integer.parseInt(idString);
					pack = EbizSql.getInstance().findPackage(productID);
				}
				if (pack != null) {
					UID = pack.UID.toString();
					UPC = pack.UPC;
					ASIN = pack.ASIN;
					SKU = pack.SKU;
					Brand = pack.brand;
					model = pack.modelNumber;
					productName = pack.productName;
					status = pack.getStatus();
					price = pack.price;
					userName=pack.userName;
				}
			%>


			<div align=center>
				<div align=left
					style="width: 99%; float: left; border: 0px solid #d7d7d7; min-height: 400px; font-size: 14px">
					<form name="edit" id="edit" action="sendEmailtoOneRecipientServlet" method="post" enctype="multipart/form-data">
						<br>
						User Name: <input style="width: 80px; height: 25px" type="text"
							id="username" name="username" value="<%=userName%>"
							readonly="readonly" />&emsp;
						<%
							if (UID.length() > 0) {
						%>
						Product UID: <input style="width: 60px; height: 25px" type="text"
							id="productID" name="productID" value="<%=UID%>"
							readonly="readonly" />&emsp;
						<%
							}
						%>

						Model:&nbsp;<input style="width: 100px; height: 25px" type="text"
							id="model" name="model" value="<%=model%>" placeholder="required"
							<%if (model.length() > 0) {%> readonly="readonly" <%}%> />&emsp;Status:&nbsp;
						<select id="status" name="status"
							style="height: 35px; width: 150px;">
							<option selected="selected" value="0">Please Chose</option>
							<%
								for (EbizPackageStatusEnum statustype : EbizPackageStatusEnum.values()) {

									if (status.equals(statustype.getColumnName())) {
							%>
							<option selected="selected"
								value="<%=statustype.getColumnName()%>"><%=statustype.getColumnName()%></option>
							<%
								} else {
							%>
							<option value="<%=statustype.getColumnName()%>"><%=statustype.getColumnName()%></option>
							<%
								}
								}
							%>
						</select> Brand:&nbsp;<input style="width: 120px; height: 25px"
							type="text" id="productBrand" name="productBrand"
							placeholder="required" value="<%=Brand%>" />
							<br> Product Name:
						<textarea id="productName" name="productName"
							style="background-color: transparent; resize: none;" rows="2"
							cols="58" placeholder="required"><%=productName%></textarea>
						 <br> <br>

						<div class="clear"></div>
						EmailAddress: <input style="width: 20%; height: 25px"
							type="text" id="emailAddress" name="emailAddress"
							value="<%=pack.email%>" placeholder="Emial Address" />&emsp;
						EmailTitle: <input style="width: 50%; height: 25px"
							type="text" id="emailTitle" name="emailTitle"
							value="UID <%=UID%>" placeholder="Emial Title" /><br> <br>
						EmailContent(添加邮件内容) 可以在这里写一些注意事项:<br>
						<textarea id="emailContent" name="emailContent"
							style="background-color: transparent; resize: none;" rows="10"
							cols="58" placeholder="Emial Content"></textarea>
						<br><br>
						
						AttachedFile:<br>

<input type="file" id="files" name="files[]" multiple />
<output id="list"></output>

						<textarea id="fileNames" name="fileNames"
							style="background-color: transparent; resize: none;" rows="5"
							cols="58" placeholder="File Names"></textarea>
						<div align=center>
							<input type="button" name="submit" value="Send Email"
								onClick="sendEmail()" />
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
			$("#edit")
					.ajaxSubmit(
							{
								beforeSubmit : function() {
									// alert("我在提交表单之前被调用！");
								},
								success : function(data) {
									alert(data);
									history.go(-1);
									
								}
							});

		}
	</script>

</body>
</html>