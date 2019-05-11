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



			<div align=center>
				<div align=left
					style="width: 99%; float: left; border: 0px solid #d7d7d7; min-height: 400px; font-size: 14px">
					<form name="edit" id="edit" action="sendEmailtoEmailAddressServlet" method="post" enctype="multipart/form-data">

						 <br> <br>

						<div class="clear"></div>
						Email Address: <input style="width: 20%; height: 25px"
							type="text" id="emailAddress" name="emailAddress"
							value="" placeholder="Emial Address" />&emsp;	 
							 
						<br> <br> 或者选择发送群体：
						<div class='container'>
							<%
								for (EbizNurseGroupTypeEnum group : EbizNurseGroupTypeEnum.values()) {
							%>

							<input type="checkbox" name="userGroupCheckbox"
								value="<%=group.getName()%>" id="group<%=group.ordinal()%>"
								<%if (group.ordinal() != 0 && group.ordinal() != 6) {%>
								checked="checked" <%}%> onclick="toggle(this)"><label
								for="<%=group.getName()%>">&nbsp; :<%=group.getChinese()%></label>&emsp;&emsp;
							<%
								}
							%>

						</div>
						<br> <br>
							 
						Email Title: &emsp;&emsp;<input style="width: 50%; height: 25px"
							type="text" id="emailTitle" name="emailTitle"
							value="" placeholder="Emial Title" /><br>  <br> <br>
						Email Content:<br>
						<textarea id="emailContent" name="emailContent"
							style="background-color: transparent; resize: none;" rows="15"
							cols="58" placeholder="Emial Content">Dear</textarea>
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