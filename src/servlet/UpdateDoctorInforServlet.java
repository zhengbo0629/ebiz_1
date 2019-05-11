package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.imap.protocol.UID;

import dataCenter.*;
import ebizConcept.*;
import nameEnum.EbizUserParaMeterEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/UpdateDoctorInforServlet")
public class UpdateDoctorInforServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateDoctorInforServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userID = Integer.parseInt(request.getParameter("userID").trim());
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizUser tempUser = EbizSql.getInstance().findUser(userID);
		EbizCompany tempCompany = EbizSql.getInstance().findCompany(tempUser.companyName);
		EbizCompany currentCompany=(EbizCompany) request.getSession().getAttribute("currentCompany");
		String introducer = (String) request.getParameter("introducer").trim();

		String password = (String) request.getParameter("password").trim();
		String status = (String) request.getParameter("status").trim();
		double balance = Double.parseDouble(request.getParameter("balance").trim());

		String permissions[] = request.getParameterValues("checkbox");
		String permissionsString = "";
		if (permissions != null) {
			for (int i = 0; i < permissions.length; i++) {
				permissionsString = permissionsString + permissions[i] + ",";
			}
			permissionsString = permissionsString.substring(0, permissionsString.length() - 1);
		}
		boolean update = false;
		String message = "";
		String orginalIntroducerString = tempUser.getParameter(EbizUserParaMeterEnum.Introducer.getName());
		if (orginalIntroducerString == null) {
			orginalIntroducerString = "";
		}
		if (!orginalIntroducerString.equals(introducer)) {

			tempUser.setParameter(EbizUserParaMeterEnum.Introducer.getName(), introducer);

			String parameterString = tempUser.getParametersString();
			update = EbizSql.getInstance().updateUserParameter(currentUser,currentCompany,tempUser.UID, parameterString);
			if (update) {

				message = message + "User Introducer Update Sucessfully \n";
			} else {
				tempUser.setParameter(EbizUserParaMeterEnum.Introducer.getName(), orginalIntroducerString);
				message = message + "User Introducer Update Failed,Please Try Again \n";
			}
		}

		if (password != null && password.length() != 0 && !tempUser.passWord.equals(password)) {

			update = EbizSql.getInstance().updateUserPassword(currentUser,currentCompany,tempUser.UID, password);
			if (update) {
				tempUser.passWord = password;
				message = message + "User passWord Update Sucessfully \n";
			} else {
				message = message + "User passWord Update Failed,Please Try Again \n";
			}
		}

		if (tempUser.getStatus() == null || !tempUser.getStatus().equals(status)) {

			update = EbizSql.getInstance().updateUserStatus(currentUser,currentCompany,tempUser.UID, status);
			if (update) {
				tempUser.setStatus(status);
				message = message + "User Status Update Sucessfully \n";
			} else {
				message = message + "User Status Update Failed,Please Try Again \n";
			}
		}
		if (tempUser.balance != balance) {

			update = EbizSql.getInstance().updateUserBalance(currentUser,currentCompany,tempUser.UID, balance);
			if (update) {
				tempUser.balance = balance;
				message = message + "User Balance Update Sucessfully \n";
			} else {
				message = message + "User Balance Update Failed,Please Try Again \n";
			}
		}

		if (tempUser.getPermissionString() == null || !tempUser.getPermissionString().equals(permissionsString)) {

			update = EbizSql.getInstance().updateUserPermission(currentUser,currentCompany,tempUser.UID, permissionsString);
			if (update) {
				tempUser.setUserPermissions(permissionsString);
				message = message + "User Permissions Update Sucessfully \n";
			} else {
				message = message + "User Permissions Update Failed,Please Try Again \n";
			}
		}

		if (tempCompany.getPermissionString() == null || !tempCompany.getPermissionString().equals(permissionsString)) {

			update = EbizSql.getInstance().updateCompanyPermission(currentUser,currentCompany,tempCompany.UID, permissionsString);
			if (update) {
				tempCompany.setPermissions(permissionsString);
				message = message + "Company Permissions Update Sucessfully \n";
			} else {
				message = message + "Company Permissions Update Failed,Please Try Again \n";
			}
		}

		if (message.length() == 0) {
			message = "Nothing Updated";
		}
		response.getWriter().print(message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}