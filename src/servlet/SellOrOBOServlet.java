package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.*;
import ebizTools.GeneralMethod;
import nameEnum.EbizPackagePayStatusEnum;
import nameEnum.EbizPackageStatusEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/SellOrOBOServlet")
public class SellOrOBOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SellOrOBOServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productModel = (String) request.getParameter("productModel").trim();

		String productName = (String) request.getParameter("productName").replace(";", "").replace(",", "").trim();

		String productBrand = (String) request.getParameter("productBrand").trim();
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		double price = Double.parseDouble(request.getParameter("price"));
		String address = (String) request.getParameter("address").trim();
		String creditCard = (String) request.getParameter("CreditCardNumber").trim();
		String status[] = request.getParameterValues("checkbox");
		String statusString = "";
		for (int i = 0; i < status.length; i++) {
			System.out.println(status[i]);
			statusString = status[i];
		}
		if (statusString.equals("orderUnplaced")) {
			statusString = EbizPackageStatusEnum.UnConfirmedUnPlaced.getColumnName();
		} else if (statusString.equals("orderPlaced")) {
			statusString = EbizPackageStatusEnum.UnConfirmedUnReceived.getColumnName();
		} else if (statusString.equals("instock")) {
			statusString = EbizPackageStatusEnum.UnConfirmedInStock.getColumnName();
		}

		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		String username = currentUser.getUserName();

		String timestring = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
		int promQuantity = 0;
		double basePrice = 0;
		double promPrice = 0;

		promQuantity = 0;
		promPrice = price;
		basePrice = price;

		String email = currentUser.getEmail();
		String paystatus = EbizPackagePayStatusEnum.UnPaid.getName();

		String uidString = request.getParameter("packageId");

		if (uidString != null) {
			// means update existing package
			int uid = Integer.parseInt(uidString);
			EbizPackage pack = EbizSql.getInstance().findPackage(uid);
			boolean update = false;
			String message = "";
			if (pack.getStatus().toLowerCase().contains("unconfirmed")) {
				if (!pack.getModel().equals(productModel)) {

					update = EbizSql.getInstance().updatePackageModel(currentUser,currentCompany,pack.UID, productModel);
					if (update) {
						pack.modelNumber = productModel;
						message = message + "ProductModel Update Sucessfully \n";
					} else {
						message = message + "ProductModel Update Failed,Please Try Again \n";
					}
				}
				if (!pack.brand.equals(productBrand)) {

					update = EbizSql.getInstance().updatePackageBrand(currentUser,currentCompany,pack.UID, productBrand);
					if (update) {
						pack.brand = productBrand;
						message = message + "Product Brand Update Sucessfully \n";
					} else {
						message = message + "Product Brand Update Failed,Please Try Again \n";
					}
				}
				if (!pack.productName.equals(productName)) {

					update = EbizSql.getInstance().updatePackageName(currentUser,currentCompany,pack.UID, productName);
					if (update) {
						pack.productName = productName;
						message = message + "Product Name Update Sucessfully \n";
					} else {
						message = message + "Product Name Update Failed,Please Try Again \n";
					}
				}
				if (pack.quantity != quantity) {

					update = EbizSql.getInstance().updatePackageQuantity(currentUser,currentCompany,pack.UID, quantity);
					if (update) {
						pack.quantity = quantity;
						message = message + "Product Quantity Update Sucessfully \n";
					} else {
						message = message + "Product Quantity Update Failed,Please Try Again \n";
					}
				}
				if (pack.price != price) {

					update = EbizSql.getInstance().updatePackagePrice(currentUser,currentCompany,pack.UID, price);
					if (update) {
						pack.price = price;
						message = message + "Product Price Update Sucessfully \n";
					} else {
						message = message + "Product Price Update Failed,Please Try Again \n";
					}
				}
				if (!pack.creditcardNumber.equals(creditCard)) {

					update = EbizSql.getInstance().updatePackageCreditCard(currentUser,currentCompany,pack.UID, creditCard);
					if (update) {
						pack.creditcardNumber = creditCard;
						message = message + "CreditCard Information Update Sucessfully \n";
					} else {
						message = message + "CreditCard Information Update Failed,Please Try Again \n";
					}
				}
				if (!pack.shippingAddress.equals(address)) {

					update = EbizSql.getInstance().updatePackageAddress(currentUser,currentCompany,pack.UID, address);
					if (update) {
						pack.shippingAddress = address;
						message = message + "Address Update Sucessfully \n";
					} else {
						message = message + "Address Update Failed,Please Try Again \n";
					}
				}
				if (!pack.getStatus().equals(statusString)) {

					update = EbizSql.getInstance().updatePackageStatus(currentUser,currentCompany,pack.UID, statusString);
					if (update) {
						pack.setStatus(statusString);
						message = message + "Status Update Sucessfully \n";
					} else {
						message = message + "Status Update Failed,Please Try Again \n";
					}
				}
				if (message.length() == 0) {
					message = "Nothing Updated";
				}

			} else {
				message = message
						+ "Package has been confirmed, you can not update it here, please update it through other way; \n";
			}
			response.getWriter().print(message);
		} else {
			// create a new package

			EbizPackage tempPackage = new EbizPackage(currentUser.companyName, "", "", productModel, productName, "",
					"", "", "", productBrand, price, basePrice, promPrice, quantity, promQuantity, "", username,
					address, email, currentUser.phoneNumber, "", "", timestring, timestring, creditCard, statusString,
					paystatus);

			if (EbizSql.getInstance().addPackage(currentUser,currentCompany,tempPackage) > 0) {

				// email both here
				response.getWriter().print("Your Package Has Been Added Sucessfully");

				// request.setAttribute("info", info);// 保存错误信息
				// request.getRequestDispatcher("unReceivedPac.jsp").forward(request,
				// response);// 跳转
			} else {
				response.getWriter().print("Your Package Has Not Been Added, Please Try Again");
			}
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