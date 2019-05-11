package ebizConcept;

import java.util.Comparator;

public class EbizProduct{
	public Integer UID;
	public String company;
	public String model;
	public String productName;
	public String UPC;
	public String Asin;
	public String SKU;
	public String brand;
	public double weight; //lb
	public double length; //inch
	public double width;  //inch
	public double height; //inch
	public double price;
	public Integer promotQuantity;
	public double promotPrice;
	public double warehousePrice;
	public Integer warehousePromotQuantity;
	public double warehousePromotePrice;
	public Integer tickets;
	public String status;
	public String createdTime;
	public String updateTime;
	public int limitPerPerson;
	public String operatingStatus;
	public String operationRecord;
	public String parameterString;
	public String uRI;
	private String userNote;
	

	
	
	
	public EbizProduct(String string){
		super();
		String[] eles=string.split(",");
		UID=Integer.parseInt(eles[0]);
		this.company=eles[1];
		this.model=eles[2];
		this.productName=eles[3];
		this.UPC=eles[4];
		this.Asin=eles[5];
		this.SKU=eles[6];
		this.brand=eles[7];
		this.price=Double.parseDouble(eles[8]);
		if(eles.length>=10){
		this.status=eles[9];
		this.tickets=Integer.parseInt(eles[10]);
		this.limitPerPerson=Integer.parseInt(eles[11]);
		this.parameterString=eles[12];
		this.parameterString=eles[13];
		}

	}

	public EbizProduct(String company,String model, String productName, String UPC, String asin, String sKU, String brand,
			Double weight,Double length,Double width,Double height,Double price,Integer PromotQuantity,Double PromotPrice,Double WarehousePrice,Integer WarehousePromotQuantity,
			Double WarehousePromotePrice,String status,Integer tickets,Integer limitPerPerson,String parameterString,String uRI) {
		super();
		this.company=company;
		this.model = model;
		this.productName = productName;
		this.UPC = UPC;
		Asin = asin;
		SKU = sKU;
		this.brand = brand;
		this.weight=weight;
		this.length=length;
		this.width=width;
		this.height=height;
		this.price = price;
		this.promotQuantity=PromotQuantity;
		this.promotPrice=PromotPrice;
		this.warehousePrice=WarehousePrice;
		this.warehousePromotQuantity=WarehousePromotQuantity;
		this.warehousePromotePrice=WarehousePromotePrice;
		this.status=status;
		this.tickets=tickets;
		this.limitPerPerson=limitPerPerson;
		this.parameterString=parameterString;
		this.uRI=uRI;
	}
	public EbizProduct(Integer UID,String company,String model, String productName, String UPC, String asin, String sKU, String brand,
			Double weight,Double length,Double width,Double height,Double price,Integer PromotQuantity,Double PromotPrice,Double WarehousePrice,Integer WarehousePromotQuantity,
			Double WarehousePromotePrice,String status,Integer tickets,Integer limitPerPerson,String parameterString,String uRI) {
		super();
	
		this.UID=UID;
		this.company=company;
		this.model = model;
		this.productName = productName;
		this.UPC = UPC;
		Asin = asin;
		SKU = sKU;
		this.brand = brand;
		this.weight=weight;
		this.length=length;
		this.width=width;
		this.height=height;
		this.price = price;
		this.promotQuantity=PromotQuantity;
		this.promotPrice=PromotPrice;
		this.warehousePrice=WarehousePrice;
		this.warehousePromotQuantity=WarehousePromotQuantity;
		this.warehousePromotePrice=WarehousePromotePrice;
		this.status=status;
		this.tickets=tickets;
		this.limitPerPerson=limitPerPerson;
		this.parameterString=parameterString;
		this.uRI=uRI;
	}
	   public EbizProduct() {
		// TODO Auto-generated constructor stub
	}

	public static Comparator<EbizProduct> UID_COMPARATOR = new Comparator<EbizProduct>() {
		      @Override
		      public int compare(final EbizProduct o1, final EbizProduct o2) {
		         return Integer.valueOf(o2.UID).compareTo(o1.UID);
		      }
		   };





	public String toString(){
		return 	UID+","
				//+company+","
				+model+";"
				+productName+";"
				//+UPC+";"
				//+Asin+";"
				//+SKU+";"
				+brand+";"
				+price+";";
		
	}
	public Integer getUID() {
		return UID;
	}
	public void setUID(Integer uID) {
		UID = uID;
	}
	public String getModel() {
		return model;
	}
	public void setModelNumber(String modelNumber) {
		this.model = modelNumber;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUpc() {
		return UPC;
	}
	public void setUpc(String upc) {
		this.UPC = upc;
	}
	public String getAsin() {
		return Asin;
	}
	public void setAsin(String asin) {
		Asin = asin;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		if(userNote==null) {
			this.userNote=""; 
			return;
		}
		this.userNote = userNote;
	}

	public void setParameterString(String param) {
		this.parameterString=param;
		
	}
	
	public Long getCreatedTime(){
		if(parameterString!=null&&parameterString.contains("CreatedTime=")){
			String[] strings=parameterString.split("\\?");
			for(int i=0;i<strings.length;i++){
				if(strings[i].contains("CreatedTime=")){
					String temp=strings[i].replace("CreatedTime=", "");
					return Long.parseLong(temp);
				}
			}
		}
		return 0L;
	}
	

}
