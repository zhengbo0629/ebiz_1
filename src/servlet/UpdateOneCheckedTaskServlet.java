package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.EbizCompany;
import ebizConcept.EbizPackage;
import ebizConcept.EbizUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/UpdateOneCheckedTaskServlet")
public class UpdateOneCheckedTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateOneCheckedTaskServlet() {
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
		int sucessednumber=0;
		String uIDsString = (String) request.getParameter("packageid");
		String status = (String) request.getParameter("onePackageStatus");
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
		
		
		for(int i=0;i<UIDList.size();i++){

				boolean update = EbizSql.getInstance().finishCheckTask(currentUser, currentCompany, UIDList.get(i),status);
				if (update) {
					message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
					sucessednumber++;
				} else {
					message = message + "UID " + UIDList.get(i) + " Update Failed \n";
					failednumber++;
				}	
		}
		if (failednumber!=0){
			if(failednumber==1){
				message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
			}else {
				message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
			}
		}
		int currentTaskNumber=EbizSql.getInstance().getCheckTaskCount(currentUser,currentCompany);
		message=message+"You have finished "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";

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