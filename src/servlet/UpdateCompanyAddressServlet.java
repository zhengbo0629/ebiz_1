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
@WebServlet("/UpdateCompanyAddressServlet")
public class UpdateCompanyAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateCompanyAddressServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String address1Name = (String) request.getParameter("address1name").trim();

		String address2Name = (String) request.getParameter("address2name").trim();
		String address3Name = (String) request.getParameter("address3name").trim();
		String address1 = (String) request.getParameter("address1").trim();

		String address2 = (String) request.getParameter("address2").trim();
		String address3 = (String) request.getParameter("address3").trim();
		String email = (String) request.getParameter("email").trim();
		String password = (String) request.getParameter("emailPassword").trim();
		String emailAndPassword=("email="+email+" "+"password="+password).trim();
		String phoneNumber= (String) request.getParameter("phoneNumber").trim();
		//加两个空格所以splid with "="的时候就不会为null了
		String address1String="address::1="+address1Name+" = "+address1;
		String address2String="address::2="+address2Name+" = "+address2;
		String address3String="address::3="+address3Name+" = "+address3;

		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		boolean update = false;
		String message = "";
		String temp=currentCompany.getAddressString();
		if (!address1String.equals(currentCompany.getAddress1String())) {
			currentCompany.setAddress1String(address1String);
			update = EbizSql.getInstance().updateCompanyAddress(currentUser,currentCompany,currentCompany.UID,currentCompany.getAddressString());
			if (update) {
				message = message + "Company Address1 Update Sucessfully \n";
				
			} else {
				currentCompany.setAddressString(temp);
				message = message + "Company Address1 Update Failed,Please Try Again \n";
			}
		}
		if (!address2String.equals(currentCompany.getAddress2String())) {
			currentCompany.setAddress2String(address2String);
			update = EbizSql.getInstance().updateCompanyAddress(currentUser,currentCompany,currentCompany.UID,currentCompany.getAddressString());
			if (update) {
				message = message + "Company Address2 Update Sucessfully \n";
			} else {
				currentCompany.setAddressString(temp);
				message = message + "Company Address2 Update Failed,Please Try Again \n";
			}
		}
		if (!address3String.equals(currentCompany.getAddress3String())) {
			currentCompany.setAddress3String(address3String);
			update = EbizSql.getInstance().updateCompanyAddress(currentUser,currentCompany,currentCompany.UID,currentCompany.getAddressString());
			if (update) {
				message = message + "Company Address3 Update Sucessfully \n";
			} else {
				currentCompany.setAddressString(temp);
				message = message + "Company Address3 Update Failed,Please Try Again \n";
			}
		}
		if (!emailAndPassword.equals(currentCompany.getEmailAndPasswordString())) {
			
			update = EbizSql.getInstance().updateCompanyEmail(currentUser,currentCompany,currentCompany.UID,emailAndPassword);
			if (update) {
				message = message + "Company Email Update Sucessfully \n";
				currentCompany.setEmailAndPasswordString(emailAndPassword);
			} else {
				message = message + "Company Email Update Failed,Please Try Again \n";
			}
		}
		if (!phoneNumber.equals(currentCompany.getPhoneNumber())) {
			
			update = EbizSql.getInstance().updateCompanyPhoneNumber(currentUser,currentCompany,currentCompany.UID,phoneNumber);
			if (update) {
				message = message + "Company Phone Number Update Sucessfully \n";
				currentCompany.setPhoneNumber(phoneNumber);
			} else {
				message = message + "Company Phone Number Update Failed,Please Try Again \n";
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