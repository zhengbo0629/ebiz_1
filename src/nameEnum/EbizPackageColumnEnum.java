package nameEnum;
public enum EbizPackageColumnEnum {
	UID("id","MEDIUMINT",9,"��������"),
	CreatedTime("CreatedTime","VARCHAR",30,"����ʱ��"),
	UpdateTime("UpdateTime","VARCHAR",30,"����ʱ��"),
	CompanyName("CompanyName","VARCHAR",50,"��˾��"),
	TrackingNumber("TrackingNumber","VARCHAR",500,"��ݵ���"),
	ShipID("ShipID","VARCHAR",150,"�ʵݺ���"),
	ModelNumber("ModelNumber","VARCHAR",50,"�ͺ�"),
	ProductName("ProductName","VARCHAR",300,"��Ʒ��"), 
	ProductCondition("ProductCondition","VARCHAR",20,"��Ʒ״̬"),
	UPC("UPC","VARCHAR",50,"UPC"),
	ASIN("ASIN","VARCHAR",50,"ASIN"),
	SKU("SKU","VARCHAR",50,"SKU"),
	Brand("Brand","VARCHAR",30,"SKU"),
	Price("Price","DOUBLE",0,"�ּ�"),
	BasePrice("BasePrice","DOUBLE",0,"�����۸�"),
	Quantity("Quantity","INT",11,"����"),
	SerialNumber("SerialNumber","VARCHAR",500,"���к�"),
	PromQuantity("PromQuantity","INT",11,"��������"),
	StoreName("StoreName","VARCHAR",30,"�̵���"),
	UserName("UserName","VARCHAR",30,"�û���"),
	ShippingAddress("ShippingAddress","VARCHAR",150,"��ַ"),
	Email("Email","VARCHAR",50,"�����ʼ�"),
	PhoneNumber("PhoneNumber","VARCHAR",20,"�绰����"),
	Receiver("Receiver","VARCHAR",30,"����Ա"),
	Recipient("Recipient","VARCHAR",30,"�ռ���"),
	Note("Note","VARCHAR",500,"��̨��ע"),
	UserNote("UserNote","VARCHAR",500,"�û���ע"),
	CreditCardNumber("CreditCardNumber","VARCHAR",500,"���ÿ���"),
	Status("Status","VARCHAR",30,"����״̬"),
	CheckStatus("CheckStatus","VARCHAR",45,"���״̬"),
	Checker("Checker","VARCHAR",30,"���Ա"),
	LabelStatus("LabelStatus","VARCHAR",45,"Label״̬"),
	Labeler("Labeler","VARCHAR",30,"LabelԱ"),
	PayStatus("PayStatus","VARCHAR",45,"֧��״̬"),
	Payer("Payer","VARCHAR",30,"֧��Ա");

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
