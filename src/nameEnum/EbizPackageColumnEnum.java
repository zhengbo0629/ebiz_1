package nameEnum;
public enum EbizPackageColumnEnum {
	UID("id","MEDIUMINT",9,"包裹号码"),
	CreatedTime("CreatedTime","VARCHAR",30,"创建时间"),
	UpdateTime("UpdateTime","VARCHAR",30,"更新时间"),
	CompanyName("CompanyName","VARCHAR",50,"公司名"),
	TrackingNumber("TrackingNumber","VARCHAR",500,"快递单号"),
	ShipID("ShipID","VARCHAR",150,"邮递号码"),
	ModelNumber("ModelNumber","VARCHAR",50,"型号"),
	ProductName("ProductName","VARCHAR",300,"产品名"), 
	ProductCondition("ProductCondition","VARCHAR",20,"产品状态"),
	UPC("UPC","VARCHAR",50,"UPC"),
	ASIN("ASIN","VARCHAR",50,"ASIN"),
	SKU("SKU","VARCHAR",50,"SKU"),
	Brand("Brand","VARCHAR",30,"SKU"),
	Price("Price","DOUBLE",0,"现价"),
	BasePrice("BasePrice","DOUBLE",0,"基础价格"),
	Quantity("Quantity","INT",11,"数量"),
	SerialNumber("SerialNumber","VARCHAR",500,"序列号"),
	PromQuantity("PromQuantity","INT",11,"促销数量"),
	StoreName("StoreName","VARCHAR",30,"商店名"),
	UserName("UserName","VARCHAR",30,"用户名"),
	ShippingAddress("ShippingAddress","VARCHAR",150,"地址"),
	Email("Email","VARCHAR",50,"电子邮件"),
	PhoneNumber("PhoneNumber","VARCHAR",20,"电话号码"),
	Receiver("Receiver","VARCHAR",30,"接收员"),
	Recipient("Recipient","VARCHAR",30,"收件人"),
	Note("Note","VARCHAR",500,"后台标注"),
	UserNote("UserNote","VARCHAR",500,"用户标注"),
	CreditCardNumber("CreditCardNumber","VARCHAR",500,"新用卡号"),
	Status("Status","VARCHAR",30,"包裹状态"),
	CheckStatus("CheckStatus","VARCHAR",45,"检查状态"),
	Checker("Checker","VARCHAR",30,"检查员"),
	LabelStatus("LabelStatus","VARCHAR",45,"Label状态"),
	Labeler("Labeler","VARCHAR",30,"Label员"),
	PayStatus("PayStatus","VARCHAR",45,"支付状态"),
	Payer("Payer","VARCHAR",30,"支付员");

    private final String name;
    private final String dataType;
    private final int length;
    private final String chinese;

    EbizPackageColumnEnum(String name,String dataType,int length,String chinese) {
        this.name = name;
        this.dataType=dataType;
        this.length=length;
        this.chinese=chinese;
    }

    public String getName() {
        return name;
    }
    public String getRole() {
        return dataType;
    }
    public int getLength(){
    	return length;
    }
    public String getChinese(){
    	return chinese;
    }
    public String toString() {
    	EbizPackageColumnEnum[] columns=EbizPackageColumnEnum.values();
    	String temp="";
    	for(int i=0;i<columns.length;i++){
    		if(i<columns.length-1){
    			temp=temp+columns[i].name+",";
    		}else{
    			temp=temp+columns[i].name;
    		}
    	}

    	
        return temp;
    }
}
