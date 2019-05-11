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
@WebServlet("/UpdateCompanyUserManualServlet")
public class UpdateCompanyUserManualServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateCompanyUserManualServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String manual = (String) request.getParameter("userManual").trim();


		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		boolean update = false;
		String message = "";
		if(currentCompany.getUserManual().length()!=0||manual.length()!=0){
		if (!manual.equals(currentCompany.getUserManual())) {
			
			update = EbizSql.getInstance().updateCompanyUserMaunal(currentUser,currentCompany,currentCompany.UID,manual);
			if (update) {
				currentCompany.setUserManual(manual);
				message = message + "User Manual Update Sucessfully \n";
			} else {
				message = message + "User Manual Update Failed,Please Try Again \n";
			}
		}
			if (message.length() == 0) {
				message = "Nothing Updated";
			}
			response.getWriter().print(message);
		}else{
		message = "Please Input a User Manual";
		response.getWriter().print(message);
		
	}
		
		
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