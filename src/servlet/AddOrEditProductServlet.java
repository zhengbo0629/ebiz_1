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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/AddOrEditProductServlet")
public class AddOrEditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddOrEditProductServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String message = "";
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		boolean isNewProduct = false;
		String idString = request.getParameter("productID");
		int uid = 0;
		if (idString != null && idString.length() != 0) {
			uid = Double.valueOf(idString).intValue();
		} else {
			isNewProduct = true;
		}
		String UPC = request.getParameter("productUPC").replace(",", " ").replace(";", " ");
		String ASIN = request.getParameter("productASIN").replace(",", " ").replace(";", " ");
		String SKU = request.getParameter("productSKU").replace(",", " ").replace(";", " ");
		String model = request.getParameter("model").replace(",", " ").replace(";", " ");
		if (model == null || model.length() == 0) {
			message = message + "Model is required \n";
		}
		String status = request.getParameter("status");
		if (status == null || status.length() == 0 || status == "0") {
			message = message + "Please choose a status \n";
		}
		String productName = request.getParameter("productName").replace(",", " ").replace(";", " ");
		if (productName == null || productName.length() == 0) {
			message = message + "ProductName is required \n";
		}
		String Brand = request.getParameter("productBrand").replace(",", " ").replace(";", " ");
		if (Brand == null || Brand.length() == 0) {
			message = message + "Brand is required \n";
		}
		String url = request.getParameter("webAddress");
		String ticketString = request.getParameter("productTickets");
		int tickets = 0;
		if (ticketString != null && ticketString.length() != 0) {
			tickets = Double.valueOf(ticketString).intValue();
		}
		String personalLimitString = request.getParameter("personalLimit");
		int personlimits = 0;
		if (personalLimitString != null && personalLimitString.length() != 0) {
			personlimits = Double.valueOf(personalLimitString).intValue();
		}
		if (message.length() > 0) {
			response.getWriter().print(message);
		} else {
			message = "";
			double weight = Double.parseDouble(request.getParameter("productWeight"));
			double length = Double.parseDouble(request.getParameter("productLength"));
			double width = Double.parseDouble(request.getParameter("productWidth"));
			double height = Double.parseDouble(request.getParameter("productHeight"));
			double price = Double.parseDouble(request.getParameter("productPrice"));
			double promotprice = Double.parseDouble(request.getParameter("promotPrice"));
			int promotQuantity = Double.valueOf(request.getParameter("promotQuantity")).intValue();
			double warehouseprice = Double.parseDouble(request.getParameter("productWarehousePrice"));
			int warehousepromotquantity = Double.valueOf(request.getParameter("warehousePromotQuantity")).intValue();
			double warehousepromotprice = Double.parseDouble(request.getParameter("warehousePromotPrice"));
			String userNote = request.getParameter("userNote");
			String timeString = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
			EbizProduct product = null;
			if (isNewProduct) {
				product = new EbizProduct();
				product.UPC = UPC;
				product.Asin = ASIN;
				product.brand = Brand;
				product.company = currentCompany.companyName;
				product.model = model;
				product.SKU = SKU;
				product.status = status;
				product.productName = productName;
				product.uRI = url;
				product.tickets = tickets;
				product.limitPerPerson = personlimits;
				product.weight = weight;
				product.length = length;
				product.width = width;
				product.height = height;
				product.price = price;
				product.promotQuantity = promotQuantity;
				product.promotPrice = promotprice;
				product.warehousePrice = warehouseprice;
				product.warehousePromotQuantity = warehousepromotquantity;
				product.warehousePromotePrice = warehousepromotprice;
				product.setUserNote(userNote);
				product.createdTime = timeString;
				product.updateTime=timeString;
				product.operationRecord = "created by " + currentUser.userName + " on " + timeString;
				EbizProduct producttemp=EbizSql.getInstance().findProduct(product.company, model);
				if(producttemp!=null){
					message = "Model is already exist. Please use the exsting product \n";
				}else{
				// id>=0 means added sucessfully
				int id = EbizSql.getInstance().addProduct(currentUser,currentCompany,product);
				if (id >= 0) {
					message = "Product Added. \n";
				} else {
					message = "Product Added Failed, Please Try Again. \n";
				}
				}
				response.getWriter().print(message);
			} else {
				boolean update=false;
				product=EbizSql.getInstance().findProduct(uid);
				if (!product.UPC.equals(UPC)) {

					update = EbizSql.getInstance().updateProductUPC(currentUser,currentCompany,product.UID, UPC);
					if (update) {
						product.UPC = UPC;
						message = message + "UPC Update Sucessfully \n";
					} else {
						message = message + "UPC Update Failed,Please Try Again \n";
					}
				}
				if (!product.Asin.equals(ASIN)) {

					update = EbizSql.getInstance().updateProductASIN(currentUser,currentCompany,product.UID, ASIN);
					if (update) {
						product.Asin = ASIN;
						message = message + "ASIN Update Sucessfully \n";
					} else {
						message = message + "ASIN Update Failed,Please Try Again \n";
					}
				}
				if (!product.brand.equals(Brand)) {

					update = EbizSql.getInstance().updateProductBrand(currentUser,currentCompany,product.UID, Brand);
					if (update) {
						product.brand = Brand;
						message = message + "Brand Update Sucessfully \n";
					} else {
						message = message + "Brand Update Failed,Please Try Again \n";
					}
				}
				if (!product.model.equals(model)) {
					update = EbizSql.getInstance().updateProductModel(currentUser,currentCompany,product.UID, model);
					if (update) {
						product.model = model;
						message = message + "Model Update Sucessfully \n";
					} else {
						message = message + "Model Update Failed,Please Try Again \n";
					}
				}
				if (!product.SKU.equals(SKU)) {
					update = EbizSql.getInstance().updateProductSKU(currentUser,currentCompany,product, SKU);
					if (update) {
						product.SKU = SKU;
						message = message + "SKU Update Sucessfully \n";
					} else {
						message = message + "SKU Update Failed,Please Try Again \n";
					}
				}
				if (!product.status.equals(status)) {
					update = EbizSql.getInstance().updateProductStatus(currentUser,currentCompany,product.UID, status);
					if (update) {
						product.status = status;
						message = message + "Status Update Sucessfully \n";
					} else {
						message = message + "Status Update Failed,Please Try Again \n";
					}
				}
				if (!product.productName.equals(productName)) {
					update = EbizSql.getInstance().updateProductName(currentUser,currentCompany,product.UID, productName);
					if (update) {
						product.productName = productName;
						message = message + "Product name Update Sucessfully \n";
					} else {
						message = message + "Product name Update Failed,Please Try Again \n";
					}
				}
				if (!product.uRI.equals(url)) {
					update = EbizSql.getInstance().updateProductURL(currentUser,currentCompany,product.UID, url);
					if (update) {
						product.uRI = url;
						message = message + "Product webaddress Update Sucessfully \n";
					} else {
						message = message + "Product webaddress Update Failed,Please Try Again \n";
					}
				}
				if (product.tickets!=tickets) {
					update = EbizSql.getInstance().updateProductTicket(currentUser,currentCompany,product.UID, tickets);
					if (update) {
						product.tickets = tickets;
						message = message + "Product Ticket Update Sucessfully \n";
					} else {
						message = message + "Product Ticket Update Failed,Please Try Again \n";
					}
				}
				if (product.limitPerPerson!=personlimits) {
					update = EbizSql.getInstance().updateProductLimitPerPerson(currentUser,currentCompany,product.UID, personlimits);
					if (update) {
						product.limitPerPerson = personlimits;
						message = message + "Product LimitPerPerson Update Sucessfully \n";
					} else {
						message = message + "Product LimitPerPerson Update Failed,Please Try Again \n";
					}
				}

				if (product.weight!=weight) {
					update = EbizSql.getInstance().updateProductWeight(currentUser,currentCompany,product.UID, weight);
					if (update) {
						product.weight = weight;
						message = message + "Product Weight Update Sucessfully \n";
					} else {
						message = message + "Product Weight Update Failed,Please Try Again \n";
					}
				}
				if (product.length!=length) {
					update = EbizSql.getInstance().updateProductLength(currentUser,currentCompany,product.UID, length);
					if (update) {
						product.length = length;
						message = message + "Product Length Update Sucessfully \n";
					} else {
						message = message + "Product Length Update Failed,Please Try Again \n";
					}
				}
				if (product.width!=width) {
					update = EbizSql.getInstance().updateProductWidth(currentUser,currentCompany,product.UID, width);
					if (update) {
						product.width = width;
						message = message + "Product Width Update Sucessfully \n";
					} else {
						message = message + "Product Width Update Failed,Please Try Again \n";
					}
				}
				if (product.height!=height) {
					update = EbizSql.getInstance().updateProductHeight(currentUser,currentCompany,product.UID, height);
					if (update) {
						product.height = height;
						message = message + "Product Height Update Sucessfully \n";
					} else {
						message = message + "Product Height Update Failed,Please Try Again \n";
					}
				}
				if (product.price!=price) {
					update = EbizSql.getInstance().updateProductPrice(currentUser,currentCompany,product.UID, price);
					if (update) {
						product.price = price;
						message = message + "Product Price Update Sucessfully \n";
					} else {
						message = message + "Product Price Update Failed,Please Try Again \n";
					}
				}
				if (product.promotQuantity!=promotQuantity) {
					update = EbizSql.getInstance().updateProductPromotQuantity(currentUser,currentCompany,product.UID, promotQuantity);
					if (update) {
						product.promotQuantity = promotQuantity;
						message = message + "Product PromotQuantity Update Sucessfully \n";
					} else {
						message = message + "Product PromotQuantity Update Failed,Please Try Again \n";
					}
				}
				if (product.promotPrice!=promotprice) {
					update = EbizSql.getInstance().updateProductPromotPrice(currentUser,currentCompany,product.UID, promotprice);
					if (update) {
						product.promotPrice = promotprice;
						message = message + "Product PromotPrice Update Sucessfully \n";
					} else {
						message = message + "Product PromotPrice Update Failed,Please Try Again \n";
					}
				}
				if (product.warehousePrice!=warehouseprice) {
					update = EbizSql.getInstance().updateProductWarehousePrice(currentUser,currentCompany,product.UID, warehouseprice);
					if (update) {
						product.warehousePrice = warehouseprice;
						message = message + "Product WarehousePrice Update Sucessfully \n";
					} else {
						message = message + "Product WarehousePrice Update Failed,Please Try Again \n";
					}
				}
				if (product.warehousePromotQuantity!=warehousepromotquantity) {
					update = EbizSql.getInstance().updateProductWarehousePromotQuantity(currentUser,currentCompany,product.UID, warehousepromotquantity);
					if (update) {
						product.warehousePromotQuantity = warehousepromotquantity;
						message = message + "Product WarehousePromotQuantity Update Sucessfully \n";
					} else {
						message = message + "Product WarehousePromotQuantity Update Failed,Please Try Again \n";
					}
				}	
				if (product.warehousePromotePrice!=warehousepromotprice) {
					update = EbizSql.getInstance().updateProductWarehousePromotePrice(currentUser,currentCompany,product.UID, warehousepromotprice);
					if (update) {
						product.warehousePromotePrice = warehousepromotprice;
						message = message + "Product WarehousePromotePrice Update Sucessfully \n";
					} else {
						message = message + "Product WarehousePromotePrice Update Failed,Please Try Again \n";
					}
				}
				if (!product.getUserNote().equals(userNote)) {
					update = EbizSql.getInstance().updateProductUserNote(currentUser,currentCompany,product.UID, userNote);
					if (update) {
						product.setUserNote(userNote);
						message = message + "Product UserNote Update Sucessfully \n";
					} else {
						message = message + "Product UserNote Update Failed,Please Try Again \n";
					}
				}
				if (message.length() > 0) {
					response.getWriter().print(message);
				} else {
					response.getWriter().print("Nothing Updated");
				}
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