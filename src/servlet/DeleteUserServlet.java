package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.*;
import nameEnum.EbizStatusEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUserServlet() {
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
		EbizCompany currentCompany=(EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizUser currentUser=(EbizUser) request.getSession().getAttribute("currentUser");
		boolean update = false;
		String message = "";
		update = EbizSql.getInstance().updateUserStatus(currentUser,currentCompany,userID, EbizStatusEnum.Deleted.getName());
		if (update) {
			message = message + "User Deleted Sucessfully \n";
		} else {
			message = message + "User Deleted Failed,Please Try Again \n";
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