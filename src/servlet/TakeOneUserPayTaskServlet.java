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
@WebServlet("/TakeOneUserPayTaskServlet")
public class TakeOneUserPayTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TakeOneUserPayTaskServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int checkTaskLimit=50;
		String message="";
		int failednumber=0;
		int sucessednumber=0;
		String pickedUserName = (String) request.getParameter("packageUserName");
		System.out.println(pickedUserName);
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		
		List<EbizPackage> packages=EbizSql.getInstance().readNeedPaidPackForUser(currentUser,currentCompany,pickedUserName);
	
		int currentTaskNumber=EbizSql.getInstance().getPayTaskCount(currentUser,currentCompany);
		for(int i=0;i<packages.size();i++){
			if (currentTaskNumber < checkTaskLimit) {
				boolean update = EbizSql.getInstance().takePayTasks(currentUser, currentCompany, packages.get(i).UID);
				if (update) {
					message = message + "UID " + packages.get(i).UID + " Update Sucessfully \n";
					sucessednumber++;
					currentTaskNumber++;
				} else {
					message = message + "UID " + packages.get(i).UID + " Update Failed \n";
					failednumber++;
				}
			}else{
				message=message+"Totally you have add "+sucessednumber+" tasks; \n";
				message=message+"You have reached the maxmum task number, please finish your task before you take more; \n";	
			}
			
		}
		if (failednumber!=0){
			if(failednumber==1){
				message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
			}else {
				message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
			}
		}else{
			message=message+"You have taken "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
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