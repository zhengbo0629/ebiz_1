<%@page import="ebizConcept.EbizUser"%>
<%@page import="nameEnum.EbizUserTypeEnum"%>
<%
EbizUser user=(EbizUser)session.getAttribute("currentUser");
if(user.getUserType().equals(EbizUserTypeEnum.Nurse.getName())
		||user.getUserType().equals(EbizUserTypeEnum.UnTrustedNurse.getName())
		||user.getUserType().equals(EbizUserTypeEnum.TrustedNurse.getName())){
	response.sendRedirect("reportPackage.jsp");
	
}else if(user.getUserType().equals(EbizUserTypeEnum.Doctor.getName())){
	response.sendRedirect("allPackForCompany.jsp");
}else if(user.getUserType().equals(EbizUserTypeEnum.SelfEmployedDoctor.getName())){
	response.sendRedirect("reportPackage.jsp");
}

%>