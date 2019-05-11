package servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.*;
import ebizTools.EmailSenderCenter;
import ebizTools.GeneralMethod;
import nameEnum.EbizCompanyAddressEnum;
import nameEnum.EbizPackagePayStatusEnum;
import nameEnum.EbizPackageStatusEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/ConfirmPackServlet")
public class ConfirmPackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConfirmPackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int uid = Integer.parseInt(request.getParameter("packID"));
		EbizPackage pack = EbizSql.getInstance().findPackage(uid);
		String statusString=pack.getStatus();
		if (pack.getStatus().equals(EbizPackageStatusEnum.UnConfirmedUnPlaced.getColumnName())) {
			statusString = EbizPackageStatusEnum.UnReceived.getColumnName();
		} else if (pack.getStatus().equals(EbizPackageStatusEnum.UnConfirmedUnReceived.getColumnName())) {
			statusString = EbizPackageStatusEnum.UnReceived.getColumnName();
		} else if (pack.getStatus().equals(EbizPackageStatusEnum.UnConfirmedInStock.getColumnName())) {
			statusString = EbizPackageStatusEnum.InStock.getColumnName();
		}
		EbizCompany company=(EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		boolean update = false;
		String message = "";

		update = EbizSql.getInstance().updatePackageStatus(currentUser,company,pack.UID, statusString);
		if (update) {
			
			String emailTitleString="You Offered Package "+pack.UID+" Has Been Confirmed";
			
			String emailContentString="Dear "+pack.userName+",\n\n You offered package "+pack.UID+" has been confirmed;\n";
			
			emailContentString=emailContentString+"Model: "+pack.modelNumber+"\n";
			emailContentString=emailContentString+"ProductName: "+pack.productName+"\n";
			emailContentString=emailContentString+"Unit: "+pack.quantity+"\n";
			emailContentString=emailContentString+"Unit Price: "+pack.price+"\n";
			emailContentString=emailContentString+"Total Value: "+pack.quantity*pack.price+"\n";
			
			if (pack.getStatus().equals(EbizPackageStatusEnum.UnConfirmedUnPlaced.getColumnName())){
				emailContentString=emailContentString+"Please make a purchase to fulfill the package report;\n";
			}
			if (pack.getStatus().equals(EbizPackageStatusEnum.UnConfirmedUnReceived.getColumnName())&&EbizCompanyAddressEnum.isCompanyAddress(pack.shippingAddress)){
				emailContentString=emailContentString+"Please input your tracking information and update the package in your account; \n";
			}
			if (pack.getStatus().equals(EbizPackageStatusEnum.UnConfirmedUnReceived.getColumnName())&&!EbizCompanyAddressEnum.isCompanyAddress(pack.shippingAddress)){
				emailContentString=emailContentString+"Please update your package to instock status once it delivered to your home address; \n";
			}
			
			if (pack.getStatus().equals(EbizPackageStatusEnum.UnConfirmedInStock.getColumnName())){
				emailContentString=emailContentString+"Please wait for more information;\n";
			}
			emailContentString = emailContentString + "\n";
			emailContentString = emailContentString + "Regards\n" + currentUser.userName+"("+company.companyName+")"+ "\n";
			emailContentString=emailContentString.replace("\n", "<br>");
			SendEmailThread newThread=new SendEmailThread(company, pack.email, emailTitleString,emailContentString);
			newThread.start();
			pack.setStatus(statusString);
			message = message + "Package Confirm Sucessfully \n";
		} else {
			message = message + "Package Confirm Failed,Please Try Again \n";
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
	public class SendEmailThread extends Thread
	{
	   private EbizCompany company;
	   private String emailAddress;
	   private String emailTitle;
	   private String emailContent;

	   public SendEmailThread(EbizCompany company,String emailAddress,String emailTitle,String emailContent)
	   {
	      this.company = company;
	      this.emailAddress = emailAddress;
	      this.emailTitle = emailTitle;
	      this.emailContent = emailContent;
	   }

	   @Override
	   public void run()
	   {
			EmailSenderCenter.getInstance().sendEmailtoOneRecipientFromCompany(company, emailAddress, emailTitle,
					emailContent, null);
	   }
	}


}