package ebizConcept;
import java.util.Comparator;

import dataCenter.EbizSql;

public class EbizWarehouseOrder {
	public Integer UID;
	public String company;
	public String orderNumber;
	public String modelNumber;
	public String productName;
	public String condition;
	public String UPC;
	public String ASIN;
	public String SKU;
	public String brand;
	public Double price;
	public Integer quantity;
	public Integer shipped;
	public String storeName;
	public String userName; //last name and first name of a person
	public String receiverName;
	public String shippingAddress;
	public String email;
	public String phoneNumber;
	public String createTime;
	public String updateTime;
	public String note;
	public String warehouse;
	public Integer packages;
	public String status;
	public String picture;
	
	public String dataFileName=System.getProperty("user.dir") +"\\allFiles\\snFile\\sn.txt";
	public String dataBackupFilename=System.getProperty("user.dir") +"\\backUpAllFiles\\snFile\\sn.txt";
	
/**
 * 
 * @param orderNumber
 * @param modelNumber
 * @param productName
 * @param condition
 * @param uPC
 * @param aSIN
 * @param sKU
 * @param brand
 * @param price
 * @param quantity
 * @param shipped
 * @param storeName
 * @param userName
 * @param reciverName
 * @param shippingAddress
 * @param email
 * @param phoneNumber
 * @param createTime
 * @param updateTime
 * @param note
 * @param packages
 * @param warehouse
 * @param status
 */
	public EbizWarehouseOrder(String company,String orderNumber,String modelNumber, String productName, String condition, String uPC, String aSIN,
			String sKU, String brand,Double price, Integer quantity,Integer shipped,String storeName,String userName,
			String reciverName,String shippingAddress,String email, String phoneNumber, String createTime, String updateTime, String note,Integer packages,
			String warehouse, String status) {
		super();
		this.company=company;
		this.modelNumber = modelNumber;
		this.orderNumber=orderNumber;
		this.productName = productName;
		this.condition = condition;
		UPC = uPC;
		ASIN = aSIN;
		SKU = sKU;
		this.brand = brand;
		this.price=price;
		this.quantity = quantity;
		this.shipped=shipped;
		this.storeName=storeName;
		this.userName=userName;
		this.receiverName=reciverName;
		this.shippingAddress=shippingAddress;
		this.email=email;
		this.phoneNumber=phoneNumber;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.note = note;
		this.packages=packages;
		this.warehouse = warehouse;
		this.status = status;
	}

/**
 * 
 * @param uID
 * @param company
 * @param orderNumber
 * @param modelNumber
 * @param productName
 * @param condition
 * @param uPC
 * @param aSIN
 * @param sKU
 * @param brand
 * @param price
 * @param quantity
 * @param shipped
 * @param storeName
 * @param userName
 * @param reciverName
 * @param shippingAddress
 * @param email
 * @param phoneNumber
 * @param createTime
 * @param updateTime
 * @param note
 * @param packages
 * @param warehouse
 * @param status
 */
	public EbizWarehouseOrder(Integer uID, String company,String orderNumber, String modelNumber,String productName, String condition, String uPC,
			String aSIN, String sKU, String brand,Double price, Integer quantity,Integer shipped,String storeName,String userName,
			String reciverName,String shippingAddress,String email, String phoneNumber, String createTime, String updateTime, String note, Integer packages,
			String warehouse, String status) {
		super();
		UID = uID;
		this.company=company;
		this.orderNumber=orderNumber;
		this.modelNumber = modelNumber;
		this.productName = productName;
		this.condition = condition;
		UPC = uPC;
		ASIN = aSIN;
		SKU = sKU;
		this.brand = brand;
		this.price=price;
		this.quantity = quantity;
		this.shipped=shipped;
		this.storeName=storeName;
		this.userName=userName;
		this.receiverName=reciverName;
		this.shippingAddress=shippingAddress;
		this.email=email;
		this.phoneNumber=phoneNumber;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.note = note;
		this.packages=packages;
		this.warehouse = warehouse;
		this.status = status;
	}


	public static Comparator<EbizPackage> UID_COMPARATOR = new Comparator<EbizPackage>() {
		@Override
		public int compare(final EbizPackage o1, final EbizPackage o2) {
			return Integer.valueOf(o2.UID).compareTo(o1.UID);
		}
	};

	public String getModel() {
		// TODO Auto-generated method stub
		return modelNumber;
	}
	public String getSN(){
		
		
		
		return (EbizSql.getInstance().readSNForOneWareHouseOrder(UID));
		
	}
	public boolean saveSN(String SN){
		
		//GeneralMethod.writeString(dataFileName, SN, true);
		//GeneralMethod.writeString(dataBackupFilename, SN, true);
		//LocalSql.getInstance().addSN(UID, SN);
		return(EbizSql.getInstance().addSN(UID, SN));
		
	}
	@Override
	public String toString() {


		String string=UID+";"+company+";"+orderNumber+";"+modelNumber+";"+productName+";"+condition+";"+UPC+";"+ASIN+";"+SKU+";"+brand+";"+price+";"+quantity+";"+
						shipped+";"+storeName+";"+userName+";"+receiverName+";"+shippingAddress+";"+email+";"+phoneNumber+";"+createTime+";"+
				updateTime+";"+note+";"+warehouse+";"+status+";"+picture;
		
		
		
		return string;
	}

}
