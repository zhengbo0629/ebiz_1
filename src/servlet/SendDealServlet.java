package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import nameEnum.EbizNurseGroupTypeEnum;
import nameEnum.EbizStatusEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/SendDealServlet")
public class SendDealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendDealServlet() {
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
		String idString = request.getParameter("productID");
		int uid = Double.valueOf(idString).intValue();

		String UPC = request.getParameter("productUPC");
		String ASIN = request.getParameter("productASIN");
		String SKU = request.getParameter("productSKU");
		String model = request.getParameter("model");
		if (model == null || model.length() == 0) {
			message = message + "Model is required \n";
		}
		String status = request.getParameter("status");
		if (status == null || status.length() == 0 || status == "0") {
			message = message + "Please choose a status \n";
		}
		String productName = request.getParameter("productName");
		if (productName == null || productName.length() == 0) {
			message = message + "ProductName is required \n";
		}
		String Brand = request.getParameter("productBrand");
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
			String emailContent = request.getParameter("emailContent");

			String addressName[] = request.getParameterValues("addressCheckbox");
			String userGroupName[] = request.getParameterValues("userGroupCheckbox");

			EbizProduct product = null;

			if (addressName == null || addressName.length == 0) {
				message = message
						+ "Send Goup Email Failed; You need chose at least one address to send a group email; \n";
			}
			if (userGroupName == null || userGroupName.length == 0) {
				message = message
						+ "Send Goup Email Failed; You need chose at least one group to send a group email; \n";
			}
			if (currentCompany.getEmail() == null || currentCompany.getEmail().length() == 0
					|| currentCompany.getEmailPassword() == null || currentCompany.getEmailPassword().length() == 0) {
				message = "Send Goup Email Failed; You can not send deal by email throught system since You did not setupt your company email and password yet. \n";
			}
			if (message.length() != 0) {
				response.getWriter().print(message);
			} else {
				boolean update = false;
				product = EbizSql.getInstance().findProduct(uid);
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
				if (!product.status.equals(status) && (status.equals(EbizStatusEnum.Active.getName())
						|| status.equals(EbizStatusEnum.LiveDeal.getName()))) {
					update = EbizSql.getInstance().updateProductStatus(currentUser,currentCompany,product.UID, status);
					if (update) {
						product.status = status;
						message = message + "Status Update Sucessfully \n";
					} else {
						message = message + "Status Update Failed,Please Try Again \n";
					}
				} else if (!product.status.equals(status)
						&& status.equals(EbizStatusEnum.UnActive.getName())) {
				} else if (product.status.equals(status)
						&& status.equals(EbizStatusEnum.UnActive.getName())) {
					update = EbizSql.getInstance().updateProductStatus(currentUser,currentCompany,product.UID,
							EbizStatusEnum.Active.getName());
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
				if (product.tickets != tickets) {
					update = EbizSql.getInstance().updateProductTicket(currentUser,currentCompany,product.UID, tickets);
					if (update) {
						product.tickets = tickets;
						message = message + "Product Ticket Update Sucessfully \n";
					} else {
						message = message + "Product Ticket Update Failed,Please Try Again \n";
					}
				}
				if (product.limitPerPerson != personlimits) {
					update = EbizSql.getInstance().updateProductLimitPerPerson(currentUser,currentCompany,product.UID, personlimits);
					if (update) {
						product.limitPerPerson = personlimits;
						message = message + "Product LimitPerPerson Update Sucessfully \n";
					} else {
						message = message + "Product LimitPerPerson Update Failed,Please Try Again \n";
					}
				}

				if (product.weight != weight) {
					update = EbizSql.getInstance().updateProductWeight(currentUser,currentCompany,product.UID, weight);
					if (update) {
						product.weight = weight;
						message = message + "Product Weight Update Sucessfully \n";
					} else {
						message = message + "Product Weight Update Failed,Please Try Again \n";
					}
				}
				if (product.length != length) {
					update = EbizSql.getInstance().updateProductLength(currentUser,currentCompany,product.UID, length);
					if (update) {
						product.length = length;
						message = message + "Product Length Update Sucessfully \n";
					} else {
						message = message + "Product Length Update Failed,Please Try Again \n";
					}
				}
				if (product.width != width) {
					update = EbizSql.getInstance().updateProductWidth(currentUser,currentCompany,product.UID, width);
					if (update) {
						product.width = width;
						message = message + "Product Width Update Sucessfully \n";
					} else {
						message = message + "Product Width Update Failed,Please Try Again \n";
					}
				}
				if (product.height != height) {
					update = EbizSql.getInstance().updateProductHeight(currentUser,currentCompany,product.UID, height);
					if (update) {
						product.height = height;
						message = message + "Product Height Update Sucessfully \n";
					} else {
						message = message + "Product Height Update Failed,Please Try Again \n";
					}
				}
				if (product.price != price) {
					update = EbizSql.getInstance().updateProductPrice(currentUser,currentCompany,product.UID, price);
					if (update) {
						product.price = price;
						message = message + "Product Price Update Sucessfully \n";
					} else {
						message = message + "Product Price Update Failed,Please Try Again \n";
					}
				}
				if (product.promotQuantity != promotQuantity) {
					update = EbizSql.getInstance().updateProductPromotQuantity(currentUser,currentCompany,product.UID, promotQuantity);
					if (update) {
						product.promotQuantity = promotQuantity;
						message = message + "Product PromotQuantity Update Sucessfully \n";
					} else {
						message = message + "Product PromotQuantity Update Failed,Please Try Again \n";
					}
				}
				if (product.promotPrice != promotprice) {
					update = EbizSql.getInstance().updateProductPromotPrice(currentUser,currentCompany,product.UID, promotprice);
					if (update) {
						product.promotPrice = promotprice;
						message = message + "Product PromotPrice Update Sucessfully \n";
					} else {
						message = message + "Product PromotPrice Update Failed,Please Try Again \n";
					}
				}
				if (product.warehousePrice != warehouseprice) {
					update = EbizSql.getInstance().updateProductWarehousePrice(currentUser,currentCompany,product.UID, warehouseprice);
					if (update) {
						product.warehousePrice = warehouseprice;
						message = message + "Product WarehousePrice Update Sucessfully \n";
					} else {
						message = message + "Product WarehousePrice Update Failed,Please Try Again \n";
					}
				}
				if (product.warehousePromotQuantity != warehousepromotquantity) {
					update = EbizSql.getInstance().updateProductWarehousePromotQuantity(currentUser,currentCompany,product.UID,
							warehousepromotquantity);
					if (update) {
						product.warehousePromotQuantity = warehousepromotquantity;
						message = message + "Product WarehousePromotQuantity Update Sucessfully \n";
					} else {
						message = message + "Product WarehousePromotQuantity Update Failed,Please Try Again \n";
					}
				}
				if (product.warehousePromotePrice != warehousepromotprice) {
					update = EbizSql.getInstance().updateProductWarehousePromotePrice(currentUser,currentCompany,product.UID, warehousepromotprice);
					if (update) {
						product.warehousePromotePrice = warehousepromotprice;
						message = message + "Product WarehousePromotePrice Update Sucessfully \n";
					} else {
						message = message + "Product WarehousePromotePrice Update Failed,Please Try Again \n";
					}
				}
				if (message.length() == 0) {
					message = message + "Nothing Updated; \n";
				}
				// 如果产品信息跟新不成功就暂时不发deal
				if (!message.contains("Update Failed")) {

					HashSet<String> userEmailList = new HashSet<>();
					HashSet<String> userGroupNameSet = new HashSet<>();
					HashMap<String, String> userEmailMap = EbizSql.getInstance()
							.readAllActiveNurseNameEmailForCompany(currentCompany.companyName);

					for (int i = 0; i < userGroupName.length; i++) {
						userGroupNameSet.add(userGroupName[i]);
					}

					boolean readySendEmail = false;

					if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.AllUser.getName())) {
						userEmailList.addAll(userEmailMap.values());
						readySendEmail = true;
					}
					if (!readySendEmail) {

						if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.NewUser.getName())) {
							List<String> newUserEmaillist = EbizSql.getInstance()
									.readActiveNurseEmailRegistratedInlastTwoMonthForCompany(
											currentCompany.companyName);
							userEmailList.addAll(newUserEmaillist);
						}
						if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.OnePackagesUser.getName())) {
							userGroupNameSet.remove(EbizNurseGroupTypeEnum.FivePackagesUser.getName());
							userGroupNameSet.remove(EbizNurseGroupTypeEnum.TenPackagesUser.getName());
							List<String> userNameList = EbizSql.getInstance()
									.readPackagesUserNameInLastTwoMonthForCompany(currentCompany.companyName);
							for (int i = 0; i < userNameList.size(); i++) {
								userEmailList.add(userEmailMap.get(userNameList.get(i)));
							}
						}

						if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.FivePackagesUser.getName())) {
							userGroupNameSet.remove(EbizNurseGroupTypeEnum.TenPackagesUser.getName());

							List<String> userNameList = EbizSql.getInstance()
									.readMoreThanFivePackagesUserNameInLastTwoMonthForCompany(
											currentCompany.companyName);
							for (int i = 0; i < userNameList.size(); i++) {
								userEmailList.add(userEmailMap.get(userNameList.get(i)));
							}
						}
						if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.TenPackagesUser.getName())) {
							List<String> userNameList = EbizSql.getInstance()
									.readMoreThanTenPackagesUserNameInLastTwoMonthForCompany(
											currentCompany.companyName);
							for (int i = 0; i < userNameList.size(); i++) {
								userEmailList.add(userEmailMap.get(userNameList.get(i)));
							}
						}
						if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.TrustedUser.getName())) {
							List<String> trustedUserEmailList = EbizSql.getInstance()
									.readAllActiveTrustedNurseNameEmailForCompany(currentCompany.companyName);
							userEmailList.addAll(trustedUserEmailList);
						}
						if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.UnTrustedUser.getName())) {
							List<String> trustedUserEmailList = EbizSql.getInstance()
									.readAllActiveUnTrustedNurseNameEmailForCompany(currentCompany.companyName);
							userEmailList.addAll(trustedUserEmailList);
						}

					}
					List<String> emailList = new ArrayList<String>();
					userEmailList.remove(null);
					int i = 0;
					for (String string : userEmailList) {
						String[] temp = string.split("\n");
						emailList.add(temp[temp.length - 1]);
						System.out.println(i + ":" + temp[temp.length - 1]);
						i++;
					}
					// send email to emailList next;
					//emailList.clear();
					//emailList.add("miketian668@gmail.com");
					//emailList.add("tgxfff@hotmail.com");
					boolean send = sendDealEmail(product, currentUser,currentCompany, addressName, emailList, emailContent);
					if (send) {
						message = message + "Email Has Been Send To Chosen Group With "+emailList.size()+" Users; \n";
					} else {
						message = message + "Send Email Failed, Please Check; \n";
					}
				} else {
					message = message + "Send Goup Email Failed Since Product Update Failed, Please Try Again; \n";
				}

				addDealToDataBase(currentUser,currentCompany,product);
				response.getWriter().print(message);
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

	private void addDealToDataBase(EbizUser user,EbizCompany company,EbizProduct product) {

		if (product == null) {
			return;
		}

		EbizSql.getInstance().addDeal(user,company,product);
		// dispose();

	}

	private boolean sendDealEmail(EbizProduct product, EbizUser currentUser,EbizCompany company, String[] addressName,
			List<String> emailList, String emailContent) {
		HashSet<String> addressSet = new HashSet<>();
		for (int i = 0; i < addressName.length; i++) {
			addressSet.add(addressName[i]);
		}
		EbizProduct tempEbizProduct = product;
		if (tempEbizProduct == null)
			return false;
		String eastebizlinkString = "<a href=http://eastebiz.com/>www.eastebiz.com</a> <br>";

		String emailContentString = "Dear All: \n";
		emailContentString = emailContentString + "Product we want:\n";

		String[] uris = tempEbizProduct.uRI.split("\n");
		String uuuString = "<a href=" + uris[0] + ">" + tempEbizProduct.getProductName() + "</a> <br>";
		if (uris.length > 1) {
			for (int i = 1; i < uris.length; i++) {
				uuuString = uuuString + "or<br>";
				if (uris[i].length() >= 5) {
					uuuString = uuuString + "<a href=" + uris[i] + ">" + tempEbizProduct.getProductName() + "</a> <br>";
				} else {
					uuuString = uuuString + tempEbizProduct.getProductName() + "</a> <br>";
				}
			}
		}
		String contentfromPanel = emailContent;
		emailContentString = emailContentString + uuuString;
		emailContentString = emailContentString + "\n";
		contentfromPanel = contentfromPanel.replace("\n\n", "");
		if (!contentfromPanel.endsWith("\n")) {
			contentfromPanel = contentfromPanel + "\n";
		}
		if (contentfromPanel.length() > 5) {
			emailContentString = emailContentString + contentfromPanel + "\n";
		}
		String shippingpreString = "Accept order shipped to ";
		boolean tt = false;
		if (addressSet.contains("Home")) {
			tt = true;
			shippingpreString = shippingpreString + "Home ";
		}
		if (addressSet.contains(company.getAddress1Name()) && company.getAddress1Name().length() > 0) {
			if (tt) {
				shippingpreString = shippingpreString + ",";
			}
			tt = true;
			shippingpreString = shippingpreString + company.getAddress1Name() + " ";
		}
		if (addressSet.contains(company.getAddress2Name()) && company.getAddress2Name().length() > 0) {
			if (tt) {
				shippingpreString = shippingpreString + ",";
			}
			tt = true;
			shippingpreString = shippingpreString + company.getAddress2Name() + " ";
		}
		if (addressSet.contains(company.getAddress3Name()) && company.getAddress3Name().length() > 0) {
			if (tt) {
				shippingpreString = shippingpreString + ",";
			}
			tt = true;
			shippingpreString = shippingpreString + company.getAddress3Name() + " ";
		}
		if (shippingpreString.endsWith(" ")) {
			shippingpreString = shippingpreString.substring(0, shippingpreString.length() - 1);
		}

		emailContentString = emailContentString + shippingpreString + ".\n";

		if (addressSet.contains("Home")) {

			if (tempEbizProduct.price == tempEbizProduct.promotPrice) {
				emailContentString = emailContentString + "Ship to home price $" + tempEbizProduct.getPrice() + "\n";
			} else {
				emailContentString = emailContentString + " Ship to home price $" + tempEbizProduct.getPrice();
				emailContentString = emailContentString + " and promotional price $" + tempEbizProduct.promotPrice
						+ " for " + tempEbizProduct.promotQuantity + " or more Units.\n";
			}
		}
		boolean haswareHouseAddress = addressSet.contains(company.getAddress1Name())
				&& company.getAddress1Name().length() > 0
				|| addressSet.contains(company.getAddress2Name()) && company.getAddress2Name().length() > 0
				|| addressSet.contains(company.getAddress2Name()) && company.getAddress2Name().length() > 0;
		if (haswareHouseAddress) {
			if (tempEbizProduct.warehousePrice == tempEbizProduct.warehousePromotePrice) {
				emailContentString = emailContentString + "Ship to warehouse price $" + tempEbizProduct.warehousePrice
						+ "\n";
			} else {
				emailContentString = emailContentString + "Ship to warehouse price $" + tempEbizProduct.warehousePrice;
				emailContentString = emailContentString + " and promotional price $"
						+ tempEbizProduct.warehousePromotePrice + " for " + tempEbizProduct.warehousePromotQuantity
						+ " or more units.\n";
			}
		}
		emailContentString = emailContentString + "\n";
		emailContentString = emailContentString + "Please get tickets or report your products on\n";
		emailContentString = emailContentString + eastebizlinkString;
		emailContentString = emailContentString + "\n";

		String warehouseAddressPreString = "WareHouse address:\n";

		if (haswareHouseAddress) {
			emailContentString = emailContentString + warehouseAddressPreString;
		}
		boolean temp = false;
		if (addressSet.contains(company.getAddress1Name()) && company.getAddress1Name().length() > 0) {
			emailContentString = emailContentString + company.getAddress1Name() + ":\n" + company.getAddress1();
			temp = true;
		}
		if (addressSet.contains(company.getAddress2Name()) && company.getAddress2Name().length() > 0) {
			if (temp) {
				emailContentString = emailContentString + "\nOr\n ";
			}
			emailContentString = emailContentString + company.getAddress2Name() + ":\n" + company.getAddress2();
			temp = true;
		}

		if (addressSet.contains(company.getAddress3Name()) && company.getAddress3Name().length() > 0) {
			if (temp) {
				emailContentString = emailContentString + "\nOr\n ";
			}
			emailContentString = emailContentString + company.getAddress2Name() + ":\n" + company.getAddress2();
			temp = true;
		}
		emailContentString = emailContentString + "\n";
		emailContentString = emailContentString + "Regards\n" + currentUser.userName+"("+company.companyName+")" + "\n";

		emailContentString = emailContentString.replace("\n", "<br>");

		if (emailList == null) {
			return false;
		}
		// System.out.println(emailAddressList.size());
		// if(true){
		// return;
		// }

		String emailTitleString = "We Want: " + tempEbizProduct.getProductName();

		if (emailList == null || emailList.size() == 0) {
			return false;
		}

		//emailList.clear();
		///emailList.add("miketian668@gmail.com");
		//emailList.add("tgxfff@hotmail.com");
		return EmailSenderCenter.getInstance().sendEmailtoMultipleReciForCompany(company, emailList, emailTitleString,
				emailContentString, null);

	}

}