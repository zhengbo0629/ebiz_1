package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/UpdateUserProfileServlet")
public class UpdateUserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String password = (String) request.getParameter("password").trim();
		String email = (String) request.getParameter("email").replace(";", "").replace(",", "").trim();
		String address = (String) request.getParameter("address").trim();
		String phoneNumber = (String) request.getParameter("phoneNumber").replace(";", "").replace(",", "").trim();
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

		String message = "";
		boolean update = false;
		if (!currentUser.getEmail().equals(email)) {
			String emailString=currentUser.getEmailString()+"\n"+email;
			update = EbizSql.getInstance().updateUserEmail(currentUser,currentCompany,currentUser.UID,emailString);
			if (update) {
				currentUser.setEmail(email);
				message = message + "User Email Update Sucessfully \n";
			} else {
				message = message + "User Email Update Failed,Please Try Again \n";
			}
		}
		if (!currentUser.phoneNumber.equals(phoneNumber)) {
			
			update = EbizSql.getInstance().updateUserPhoneNumber(currentUser,currentCompany,currentUser.UID,phoneNumber);
			if (update) {
				currentUser.phoneNumber = phoneNumber;
				message = message + "User Phone Number Update Sucessfully \n";
			} else {
				message = message + "User Phone Number Update Failed,Please Try Again \n";
			}
		}
		if (!currentUser.passWord.equals(password)) {
			
			update = EbizSql.getInstance().updateUserPassword(currentUser,currentCompany,currentUser.UID,password);
			if (update) {
				currentUser.passWord = password;
				message = message + "User passWord Update Sucessfully \n";
			} else {
				message = message + "User passWord Update Failed,Please Try Again \n";
			}
		}

		if (!currentUser.getAddress().equals(address)) {
			
			update = EbizSql.getInstance().updateUserAddress(currentUser,currentCompany,currentUser.UID,address);
			if (update) {
				currentUser.address = address;
				message = message + "User Address Update Sucessfully \n";
			} else {
				message = message + "User Address Update Failed,Please Try Again \n";
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