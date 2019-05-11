package ebizConcept;

public class EbizInventory{
	public EbizProduct product;
	public int UID;
	public String company;
	public String modelNumber;
	public String productName;
	public String condition;
	public String UPC;
	public String ASIN;
	public String SKU;
	public String brand;
	public Double price;
	public int reported;
	public int incoming;
	public int received;
	public int inStock;
	public int quantityPreSend;
	public int quantityShipping;
	public int quantityShipped;
	public String createdTime;
	public String updatedTime;
	public String note;
	public String warehouse;
	public String status;


	
	/**
	 * 
	 * 
	 * @param uID
	 * @param modelNumber
	 * @param productName
	 * @param condition
	 * @param uPC
	 * @param aSIN
	 * @param sKU
	 * @param brand
	 * @param price
	 * @param reported
	 * @param incoming
	 * @param received
	 * @param inStock
	 * @param quantityPreSend
	 * @param quantityShipping
	 * @param quantityShipped
	 * @param quantityLeft
	 * @param createdTime
	 * @param updatedTime
	 * @param note
	 * @param warehouse
	 */
	public EbizInventory(int uID, String company,String modelNumber, String productName, String condition,
			String uPC, String aSIN, String sKU, String brand, Double price, int reported,int incoming, int received, int inStock,
			int quantityPreSend,int quantityShipping, int quantityShipped, String createdTime, String updatedTime,
			String note, String warehouse) {
		super();
		UID = uID;
		this.company=company;
		this.modelNumber = modelNumber;
		this.productName = productName;
		this.condition = condition;
		UPC = uPC;
		ASIN = aSIN;
		SKU = sKU;
		this.brand = brand;
		this.price = price;
		this.reported=reported;
		this.incoming=incoming;
		this.received = received;
		this.inStock = inStock;
		this.quantityPreSend = quantityPreSend;
		this.quantityShipping=quantityShipping;
		this.quantityShipped = quantityShipped;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.note = note;
		this.warehouse = warehouse;
	}
	/**
	 * 
	 * @param modelNumber
	 * @param productName
	 * @param condition
	 * @param uPC
	 * @param aSIN
	 * @param sKU
	 * @param brand
	 * @param price
	 * @param reported
	 * @param incoming
	 * @param received
	 * @param inStock
	 * @param quantityPreSend
	 * @param quantityShipping
	 * @param quantityShipped
	 * @param createdTime
	 * @param updatedTime
	 * @param note
	 * @param warehouse
	 */
	public EbizInventory(String company,String modelNumber, String productName, String condition,
			String uPC, String aSIN, String sKU, String brand, Double price,int reported,int incoming, int received, int inStock,
			int quantityPreSend, int quantityShipping,int quantityShipped, String createdTime, String updatedTime,
			String note, String warehouse) {
		super();
		this.company=company;
		this.modelNumber = modelNumber;
		this.productName = productName;
		this.condition = condition;
		UPC = uPC;
		ASIN = aSIN;
		SKU = sKU;
		this.brand = brand;
		this.price = price;
		this.reported=reported;
		this.incoming=incoming;
		this.received = received;
		this.inStock = inStock;
		this.quantityPreSend = quantityPreSend;
		this.quantityShipping=quantityShipping;
		this.quantityShipped = quantityShipped;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.note = note;
		this.warehouse = warehouse;
	}








	public String getKeyString(){
		return modelNumber+company+condition+warehouse;
	}









	public void setProduct(EbizProduct ebizProduct) {
		// TODO Auto-generated method stub
		this.product=ebizProduct;
		
	}
	@Override
	public String toString() {

		
		String string=UID+";"+company+";"+modelNumber+";"+productName+";"+condition+";"+UPC+";"+ASIN+";"+SKU+";"+brand+";"+price+";"+
						received+";"+inStock+";"+quantityPreSend+";"+quantityShipping+";"+quantityShipped+";"+createdTime+";"+
				updatedTime+";"+note+";"+warehouse;	
		return string;	
		
	}


}
