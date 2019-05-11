package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.*;
import nameEnum.EbizPackageStatusEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/EditUnreceivedPackServlet")
public class EditUnreceivedPackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUnreceivedPackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int quantity =Integer.parseInt(request.getParameter("quantity"));
		String creditcard = (String) request.getParameter("creditcard");
		int uid = Integer.parseInt( request.getParameter("packageId"));
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizPackage pack=EbizSql.getInstance().findPackage(uid);
		boolean myCheckBox = request.getParameter( "InStock" ) != null;
		String statusString="";
	    if(myCheckBox){
	    	statusString=EbizPackageStatusEnum.InStock.getColumnName();
	    }else{
	    	statusString=EbizPackageStatusEnum.UnReceived.getColumnName();
	    }
		double basePrice=Double.parseDouble(request.getParameter("basePrice"));
		double promPrice=Double.parseDouble( request.getParameter("promPrice"));
		int promQuantity=Integer.parseInt(request.getParameter("promQuantity"));
		double price=basePrice;
		if (quantity>=promQuantity){
		    price=promPrice;
		}
		boolean update=false;
		String message="";
		if(pack.quantity!=quantity){

			update=EbizSql.getInstance().updatePackageQuantityAndPrice(currentUser,currentCompany,pack,quantity,price);
			if(update){
				pack.quantity=quantity;
				pack.price=price;
				message=message+"Quantity Update Sucessfully \n";
			}else{
				message=message+"Quantity Update Failed,Please Try Again \n";
			}
		}
		if(!pack.creditcardNumber.equals(creditcard)){
			
			update=EbizSql.getInstance().updatePackageCreditCard(currentUser,currentCompany,pack.UID,creditcard);
			if(update){
				pack.creditcardNumber=creditcard;
				message=message+"CreditCard Information Update Sucessfully \n";
			}else{
				message=message+"CreditCard Information Update Failed,Please Try Again \n";
			}
		}
		if(!pack.getStatus().equals(statusString)&&statusString.equals(EbizPackageStatusEnum.InStock.getColumnName())){
			
			update=EbizSql.getInstance().updatePackageStatus(currentUser,currentCompany,pack.UID,statusString);
			if(update){
				pack.setStatus(statusString);
				message=message+"Status Update Sucessfully \n";
			}else{
				message=message+"Status Update Failed,Please Try Again \n";
			}
		}
		if(message.length()>0){
			response.getWriter().print(message);
		}else{
			response.getWriter().print("Nothing Updated");
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