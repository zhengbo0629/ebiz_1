package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.EbizCompany;
import ebizConcept.EbizUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/EditPacksCreditCardServlet")
public class EditPacksCreditCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditPacksCreditCardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message="";
		int failednumber=0;
		String uIDsString = (String) request.getParameter("unPaidPackagesUid");
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		if(uIDsString.endsWith(";")){
			uIDsString=uIDsString.substring(0,uIDsString.length()-1);
		}
		String[] uidStrings=uIDsString.split(";");
		ArrayList<Integer> UIDList=new ArrayList<>();
		for(int i=0;i<uidStrings.length;i++){
			UIDList.add(Integer.parseInt(uidStrings[i]));
		}
		Collections.sort(UIDList);
		Collections.reverse(UIDList);
		String creditcardInform = (String) request.getParameter("creditcard");
		int maxuid=0;
		for(int i=0;i<UIDList.size();i++){
			if(maxuid==0){
				maxuid=UIDList.get(i);
			}else{
				creditcardInform="Same As UID "+maxuid;
			}
			boolean update=EbizSql.getInstance().updatePackageCreditCard(currentUser,currentCompany,UIDList.get(i),creditcardInform);
			if(update){
				message=message+"UID "+UIDList.get(i)+" Update Sucessfully \n";
			}else{
				message=message+"UID "+UIDList.get(i)+" Update Failed \n";
				failednumber++;
			}
			
		}
		if (failednumber!=0){
			if(failednumber==1){
				message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button)";
			}else {
				message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button)";
			}
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