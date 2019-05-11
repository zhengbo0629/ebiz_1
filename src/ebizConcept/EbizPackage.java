package ebizConcept;

import java.util.Comparator;

public class EbizPackage {
	public Integer UID;
	public String company;
	public String trackingNumber;
	public String shipID;
	public String modelNumber;
	public String productName;
	public String condition;
	public String UPC;
	public String ASIN;
	public String SKU;
	public String brand;
	public Double price;
	public Double basePrice;
	public Double promPrice;
	public int quantity;
	public String serialNumber;
	public int promQuantity;
	public Double totalPrice;
	public String storeName;
	public String userName; // last name and first name of a person
	public String shippingAddress;
	public String email;
	public String phoneNumber;
	public String receiver; // people in warehouse who recived this package
	public String recipient;
	public String note;
	public String createdTime;
	public String updateTime;
	public String creditcardNumber;
	private String status;
	private String checkStatus;
	private String checker;
	private String labelStatus;
	private String labeler;
	private String payStatus;
	private String payer;
	private String userNote;
	/**
	 * 
	 * @param uID
	 * @param company
	 * @param trackingNumber
	 * @param shipID
	 * @param modelNumber
	 * @param productName
	 * @param condition
	 * @param uPC
	 * @param aSIN
	 * @param sKU
	 * @param brand
	 * @param price
	 * @param basePrice
	 * @param promPrice
	 * @param quantity
	 * @param promQuantity
	 * @param storeName
	 * @param userName
	 * @param shippingAddress
	 * @param email
	 * @param phoneNumber
	 * @param reciever
	 * @param note
	 * @param reportTime
	 * @param updateTime
	 * @param creditcardNumber
	 * @param status
	 * @param payStatus
	 */
	public EbizPackage(Integer uID, String company, String trackingNumber, String shipID, String modelNumber,
			String productName, String condition, String uPC, String aSIN, String sKU, String brand, Double price,
			Double basePrice, Double promPrice, Integer quantity, Integer promQuantity, String storeName,
			String userName, String shippingAddress, String email, String phoneNumber, String reciever, String note,
			String createdTime, String updateTime, String creditcardNumber, String status, String payStatus) {
		super();
		UID = uID;
		this.company = company;
		this.trackingNumber = trackingNumber;
		this.shipID = shipID;
		this.modelNumber = modelNumber;
		this.productName = productName;
		this.condition = condition;
		this.UPC = uPC;
		this.ASIN = aSIN;
		this.SKU = sKU;
		this.brand = brand;
		this.price = price;
		this.basePrice = basePrice;
		this.promPrice = promPrice;
		this.quantity = quantity;
		this.promQuantity = promQuantity;
		this.totalPrice = price * quantity;
		this.storeName = storeName;
		this.userName = userName;
		this.shippingAddress = shippingAddress;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.receiver = reciever;
		this.note = note;
		this.createdTime = createdTime;
		this.updateTime = updateTime;
		this.creditcardNumber = creditcardNumber;
		this.status = status;
		this.payStatus = payStatus;
	}

	/**
	 * 
	 * @param company
	 * @param trackingNumber
	 * @param shipID
	 * @param modelNumber
	 * @param productName
	 * @param condition
	 * @param uPC
	 * @param aSIN
	 * @param sKU
	 * @param brand
	 * @param price
	 * @param basePrice
	 * @param promPrice
	 * @param quantity
	 * @param promQuantity
	 * @param storeName
	 * @param userName
	 * @param shippingAddress
	 * @param email
	 * @param phoneNumber
	 * @param reciever
	 * @param note
	 * @param reportTime
	 * @param updateTime
	 * @param creditcardNumber
	 * @param status
	 * @param payStatus
	 */
	public EbizPackage(String company, String trackingNumber, String shipID, String modelNumber, String productName,
			String condition, String uPC, String aSIN, String sKU, String brand, Double price, Double basePrice,
			Double promPrice, Integer quantity, Integer promQuantity, String storeName, String userName,
			String shippingAddress, String email, String phoneNumber, String reciever, String note, String createdTime,
			String updateTime, String creditcardNumber, String status, String payStatus) {

		super();
		this.company = company;
		this.trackingNumber = trackingNumber;
		this.shipID = shipID;
		this.modelNumber = modelNumber;
		this.productName = productName;
		this.condition = condition;
		this.UPC = uPC;
		this.ASIN = aSIN;
		this.SKU = sKU;
		this.brand = brand;
		this.price = price;
		this.basePrice = basePrice;
		this.promPrice = promPrice;
		this.quantity = quantity;
		this.promQuantity = promQuantity;
		this.totalPrice = price * quantity;
		this.storeName = storeName;
		this.userName = userName;
		this.shippingAddress = shippingAddress;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.receiver = reciever;
		this.note = note;
		this.createdTime = createdTime;
		this.updateTime = updateTime;
		this.creditcardNumber = creditcardNumber;
		this.status = status;
		this.payStatus = payStatus;
	}

	public EbizPackage() {
	}



	
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getChecker() {
		if(checker==null){
			return "";
		}
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getLabelStatus() {
		return labelStatus;
	}

	public void setLabelStatus(String labelStatus) {
		this.labelStatus = labelStatus;
	}

	public String getLabeler() {
		if(labeler==null){
			return "";
		}
		return labeler;
	}

	public void setLabeler(String labeler) {
		this.labeler = labeler;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayer() {
		if(payer==null){
			return "";
		}
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}







	public static Comparator<EbizPackage> UID_COMPARATOR = new Comparator<EbizPackage>() {
		@Override
		public int compare(final EbizPackage o1, final EbizPackage o2) {
			return Integer.valueOf(o2.UID).compareTo(o1.UID);
		}
	};

	public String getModel() {
		return modelNumber;
	}

	@Override
	public String toString() {

		String string = UID + ";" + company + ";" + trackingNumber + ";" + shipID + ";" + modelNumber + ";"
				+ productName + ";" + condition + ";" + UPC + ";" + ASIN + ";" + SKU + ";" + brand + ";" + price + ";"
				+ quantity + ";" + totalPrice + ";" + storeName + ";" + userName + ";" + shippingAddress + ";" + email
				+ ";" + phoneNumber + ";" + receiver + ";" + note + ";" + createdTime + ";" + updateTime + ";"
				+ creditcardNumber + ";" + status;
		return string;
	}

}
