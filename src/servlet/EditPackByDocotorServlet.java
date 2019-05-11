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
import nameEnum.EbizPackageLabelStatusEnum;
import nameEnum.EbizPackagePayStatusEnum;
import nameEnum.EbizPackageStatusEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/EditPackByDocotorServlet")
public class EditPackByDocotorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditPackByDocotorServlet() {
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
		String ASIN = (String) request.getParameter("productASIN").trim();
		String UPC = (String) request.getParameter("productUPC").trim();
		String SKU = (String) request.getParameter("productSKU").trim();
		String trackingNumber = (String) request.getParameter("trackingNumber").trim();
		String shipID = (String) request.getParameter("shipID").trim();
		String note=(String) request.getParameter("note").trim();
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		double price = Double.parseDouble(request.getParameter("price"));
		String address = (String) request.getParameter("address").trim();
		String creditCard = (String) request.getParameter("CreditCardNumber").trim();
		String shipstatus = (String) request.getParameter("packageStatus");
		String payStatus = (String) request.getParameter("payStatus");

		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		String uidString = request.getParameter("packageId");

		int uid = Integer.parseInt(uidString);
		EbizPackage pack = EbizSql.getInstance().findPackage(uid);
		boolean update = false;
		String message = "";

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
		if (!pack.getPayStatus().equals(payStatus)) {

			update = EbizSql.getInstance().updatePackagePayStatus(currentUser,currentCompany,pack.UID, payStatus);
			if (update) {
				pack.setPayStatus(payStatus);
				message = message + "Pay Status Update Sucessfully \n";
			} else {
				message = message + "Pay Status Update Failed,Please Try Again \n";
			}
		}
		if (!pack.getStatus().equals(shipstatus)) {

			update = EbizSql.getInstance().updatePackageStatus(currentUser,currentCompany,pack.UID, shipstatus);
			if (update) {
				if ((pack.getLabelStatus()!=null&&pack.getLabelStatus().equals(EbizPackageLabelStatusEnum.MadeLabel.getName()))
						&& (pack.getStatus().equals(EbizPackageStatusEnum.EmailedLabel.getColumnName())
								|| pack.getStatus().equals(EbizPackageStatusEnum.Complete.getColumnName()))
						&& shipstatus.equals(EbizPackageStatusEnum.InStock.getColumnName())) {
					update = EbizSql.getInstance().updatePackageLabelStatus(currentUser, currentCompany, pack.UID,
							EbizPackageLabelStatusEnum.UnMadeLabel.getName());
					if (update) {
						pack.setLabelStatus(EbizPackageLabelStatusEnum.UnMadeLabel.getName());
						message = message + "Label Status Update Sucessfully \n";
					} else {
						message = message + "Label Status Update Failed,Please Try Again \n";
					}
				}
				pack.setStatus(shipstatus);
				message = message + "Status Update Sucessfully \n";
			} else {
				message = message + "Status Update Failed,Please Try Again \n";
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
		
		if (!pack.SKU.equals(SKU)) {

			update = EbizSql.getInstance().updatePackageSKU(currentUser,currentCompany,pack.UID, SKU);
			if (update) {
				pack.SKU = SKU;
				message = message + "SKU Information Update Sucessfully \n";
			} else {
				message = message + "SKU Information Update Failed,Please Try Again \n";
			}
		}
		if (!pack.UPC.equals(UPC)) {

			update = EbizSql.getInstance().updatePackageUPC(currentUser,currentCompany,pack.UID, UPC);
			if (update) {
				pack.UPC = UPC;
				message = message + "UPC Information Update Sucessfully \n";
			} else {
				message = message + "UPC Information Update Failed,Please Try Again \n";
			}
		}
		if (!pack.ASIN.equals(ASIN)) {

			update = EbizSql.getInstance().updatePackageUPC(currentUser,currentCompany,pack.UID, ASIN);
			if (update) {
				pack.ASIN = ASIN;
				message = message + "ASIN Information Update Sucessfully \n";
			} else {
				message = message + "ASIN Information Update Failed,Please Try Again \n";
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
		if (!pack.trackingNumber.equals(trackingNumber)) {

			update = EbizSql.getInstance().updatePackageTracking(currentUser,currentCompany,pack.UID, trackingNumber);
			if (update) {
				pack.trackingNumber = trackingNumber;
				message = message + "tracking Number Update Sucessfully \n";
			} else {
				message = message + "tracking Number Update Failed,Please Try Again \n";
			}
		}
		if (!pack.shipID.equals(shipID)) {

			update = EbizSql.getInstance().updatePackageShipId(currentUser,currentCompany,pack.UID, shipID);
			if (update) {
				pack.shipID = shipID;
				message = message + "Ship ID Update Sucessfully \n";
			} else {
				message = message + "Ship ID Update Failed,Please Try Again \n";
			}
		}
		if (!pack.note.equals(note)) {

			update = EbizSql.getInstance().updatePackageNote(currentUser,currentCompany,pack.UID, note);
			if (update) {
				pack.note = note;
				message = message + "Note Update Sucessfully \n";
			} else {
				message = message + "Note Update Failed,Please Try Again \n";
			}
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

}