package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.*;
import ebizTools.GeneralMethod;
import nameEnum.EbizPackagePayStatusEnum;
import nameEnum.EbizCompanyAddressEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/ReceivedPackageServlet")
public class ReceivedPackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceivedPackageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String trackingNumber = (String) request.getParameter("trackingNumber").trim();
		String recipient = (String) request.getParameter("recipient").trim();
		String companyName = (String) request.getParameter("CompanyName").trim();
		String condition1 = (String) request.getParameter("condition1").trim();
		String quantity1 = (String) request.getParameter("quantity1").trim();
		String UPC1 = (String) request.getParameter("UPC1").trim();
		String serialNumber1=(String) request.getParameter("serialNumber1").trim();
		String productname1 = (String) request.getParameter("productname1").trim();
		String condition2 = (String) request.getParameter("condition2").trim();
		String quantity2 = (String) request.getParameter("quantity2").trim();
		String serialNumber2=(String) request.getParameter("serialNumber2").trim();
		String UPC2 = (String) request.getParameter("UPC2").trim();
		String productname2 = (String) request.getParameter("productname2").trim();
		String condition3 = (String) request.getParameter("condition3").trim();
		String quantity3 = (String) request.getParameter("quantity3").trim();
		String serialNumber3=(String) request.getParameter("serialNumber3").trim();
		String UPC3 = (String) request.getParameter("UPC3").trim();
		String productname3 = (String) request.getParameter("productname3").trim();
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

		String message="";

			if (UPC1!=null&&UPC1.length()!=0){
				EbizPackage package1=new EbizPackage();
				package1.trackingNumber=trackingNumber;
				package1.UPC=UPC1;
				package1.company=companyName;
				package1.condition=condition1;
				package1.productName=productname1;
				package1.quantity=Integer.parseInt(quantity1);
				package1.serialNumber=serialNumber1;
				package1.receiver=currentUser.userName;
				package1.recipient=recipient;
				package1.price=-1.0;
				package1.basePrice=-1.0;
				package1.promPrice=-1.0;
				if(EbizSql.getInstance().addReceivedPackage(currentUser, currentCompany, package1)){
					message=message+"Package 1 Added\n";
				}
			}
			if (UPC2!=null&&UPC2.length()!=0){
				EbizPackage package1=new EbizPackage();
				package1.trackingNumber=trackingNumber;
				package1.UPC=UPC2;
				package1.company=companyName;
				package1.condition=condition2;
				package1.productName=productname2;
				package1.quantity=Integer.parseInt(quantity2);
				package1.serialNumber=serialNumber2;
				package1.receiver=currentUser.userName;
				package1.recipient=recipient;
				package1.price=-1.0;
				package1.basePrice=-1.0;
				package1.promPrice=-1.0;
				if(EbizSql.getInstance().addReceivedPackage(currentUser, currentCompany, package1)){
					message=message+"Package 2 Added\n";
				}
			}
			if (UPC3!=null&&UPC3.length()!=0){
				EbizPackage package1=new EbizPackage();
				package1.trackingNumber=trackingNumber;
				package1.UPC=UPC3;
				package1.company=companyName;
				package1.condition=condition3;
				package1.productName=productname3;
				package1.quantity=Integer.parseInt(quantity3);
				package1.serialNumber=serialNumber3;
				package1.receiver=currentUser.userName;
				package1.recipient=recipient;
				package1.price=-1.0;
				package1.basePrice=-1.0;
				package1.promPrice=-1.0;
				if(EbizSql.getInstance().addReceivedPackage(currentUser, currentCompany, package1)){
					message=message+"Package 3 Added\n";
				}
			}

		

		if (message.length()== 0) {
			message=message+"No Package Added\n";

		} else {

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