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
@WebServlet("/ReportPackageServlet")
public class ReportPackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportPackageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productString = (String) request.getParameter("product");
		String[] productArray = productString.split(",");
		int productUID = Integer.parseInt(productArray[0]);
		int limitPerperson = Integer.parseInt(productArray[2]);
		String model = productArray[3];
		// int productPersonalLimit = Integer.parseInt(productArray[2]);
		int reportingQuantity = Integer.parseInt(request.getParameter("quantity"));
		int ticket = Integer.parseInt(request.getParameter("ticket"));
		
		String creditCard = (String) request.getParameter("CreditCardNumber");
		String address = (String) request.getParameter("address");
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		String username = currentUser.getUserName();
		limitPerperson=currentUser.personalLimit>limitPerperson?limitPerperson:currentUser.personalLimit;
		int reportedQuantity=EbizSql.getInstance().reportedNnumberInresentTwodaysForUser(currentUser, model);
		ticket=limitPerperson-reportedQuantity;
		List<String> info = new ArrayList<String>();
		String message="";
		if (productUID == 0) {
			message=message+"Please Chose A Product;\n";
		} else {
			EbizProduct selectedProduct = EbizSql.getInstance().findProduct(productUID);
			if (selectedProduct == null) {
				message=message+"Can Not Find Product In DataBase;\n";
				
			} else if (selectedProduct.tickets == 0) {
				message=message+"No unit is needed, please try other product;\n";
			} else if (reportingQuantity == 0) {
				message=message+"Can not report 0 units, Please try again;\n";
			} else {
				String modelNumber = selectedProduct.getModel();

				if (reportingQuantity > ticket) {
					
				} else if (selectedProduct.tickets >= reportingQuantity) {
					int leftTickets = selectedProduct.tickets - reportingQuantity;
					if (!EbizSql.getInstance().updateProductTicket(currentUser ,currentCompany ,selectedProduct.UID,leftTickets)) {
						message=message+"Please check you connection or Try Again;\n";
					} else {
						selectedProduct.tickets = leftTickets;
						String timestring = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
						double price = selectedProduct.price;
						int promQuantity = 0;
						double basePrice = 0;
						double promPrice = 0;

						if (isWarehouseAddress(address)) {
							promQuantity = selectedProduct.warehousePromotQuantity;
							promPrice = selectedProduct.warehousePromotePrice;
							basePrice = selectedProduct.warehousePrice;

						} else {
							promQuantity = selectedProduct.promotQuantity;
							promPrice = selectedProduct.promotPrice;
							basePrice = selectedProduct.price;
						}
						if (reportingQuantity >= promQuantity) {
							price = promPrice;
						} else {
							price = basePrice;
						}
						String email = currentUser.getEmail();
						String paystatus=EbizPackagePayStatusEnum.UnPaid.getName();
						EbizPackage tempPackage = new EbizPackage(selectedProduct.company, "", "", modelNumber,
								selectedProduct.getProductName(), "", selectedProduct.getUpc(),
								selectedProduct.getAsin(), selectedProduct.getSKU(), selectedProduct.getBrand(), price,
								basePrice, promPrice, reportingQuantity, promQuantity, "", username, address, email,
								currentUser.phoneNumber, "", "", timestring, timestring, creditCard, "UnReceived", paystatus);
						

						if (EbizSql.getInstance().addPackage(currentUser,currentCompany,tempPackage) > 0) {
							message=message+"Package Added Successfully;\n";
							
						}else{
							message=message+"Package Added Failed,Please Try Again;\n";
						}
						if (message.length() > 0) {
							response.getWriter().print(message);
						}
					}

				} else {
					info.add("Do not have enough Tickets Left, Please lower the quantity and Try Again");
				}
			}
		}

		if (info.size() == 0) {

		} else {
			request.setAttribute("info", info);// 保存错误信息
			request.getRequestDispatcher("reportPackage.jsp").forward(request, response);// 跳转
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

	private boolean isWarehouseAddress(String address) {
		return EbizCompanyAddressEnum.isCompanyAddress(address);
	}

}