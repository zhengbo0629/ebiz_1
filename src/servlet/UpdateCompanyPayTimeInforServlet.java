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
@WebServlet("/UpdateCompanyPayTimeInforServlet")
public class UpdateCompanyPayTimeInforServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateCompanyPayTimeInforServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String period = (String) request.getParameter("period").trim();
		if(!period.equals("0")){
		int year = Integer.parseInt( request.getParameter("year").trim());
		int month = Integer.parseInt( request.getParameter("month").trim());
		int day = Integer.parseInt(request.getParameter("day").trim());
		String paytimeinfor=period+":"+year+"-"+month+"-"+day;

		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		boolean update = false;
		String message = "";
		if (!paytimeinfor.equals(currentCompany.getPayTimeInfor())) {
			
			update = EbizSql.getInstance().updateCompanyPayTimeInfor(currentUser,currentCompany,currentCompany.UID,paytimeinfor);
			if (update) {
				currentCompany.setPayTimeInfor(paytimeinfor);
				message = message + "PayTime Information Update Sucessfully \n";
			} else {
				message = message + "PayTime Information Update Failed,Please Try Again \n";
			}
		}

		if (message.length() == 0) {
			message = "Nothing Updated";
		}
		response.getWriter().print(message);

	}else{
		String message = "Please Chose A Pay Time Period";
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