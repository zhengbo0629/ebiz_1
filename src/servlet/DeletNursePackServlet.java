package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.EbizCompany;
import ebizConcept.EbizPackage;
import ebizConcept.EbizProduct;
import ebizConcept.EbizUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/DeletNursePackServlet")
public class DeletNursePackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeletNursePackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int uid = Integer.parseInt(request.getParameter("deletPackage"));
		EbizPackage pack=EbizSql.getInstance().findPackage(uid);
		EbizCompany currentCompany=(EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizUser currentUser=(EbizUser) request.getSession().getAttribute("currentUser");
		EbizProduct product=EbizSql.getInstance().findProduct(currentCompany.companyName, pack.modelNumber);
		int newQuantity=product.tickets+pack.quantity;
		if (EbizSql.getInstance().deletePackage(currentUser,currentCompany,uid)) {
			//tickets returnd back to product list
			EbizSql.getInstance().updateProductTicket(currentUser,currentCompany,product.UID, newQuantity);
			//email both here
			response.getWriter().print("Your Package Has Been Deleted Sucessfully");
			
			//request.setAttribute("info", info);// 保存错误信息
			//request.getRequestDispatcher("unReceivedPac.jsp").forward(request, response);// 跳转
		}else{
			response.getWriter().print("Your Package Has Not Been Deleted, Please Try Again");
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