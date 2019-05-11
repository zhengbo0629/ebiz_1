package nameEnum;
public enum EbizCompanyColumnEnum {
	UID("id","MEDIUMINT",9,"包裹号码"),
	CreatedTime("CreatedTime","VARCHAR",30,"创建时间"),
	UpdateTime("UpdateTime","VARCHAR",30,"更新时间"),
	CompanyName("CompanyName","VARCHAR",50,"公司名"),
	OwnerName("OwnerName","VARCHAR",30,"拥有人"),
	Status("Status","VARCHAR",20,"状态"),
	Permision("Permision","VARCHAR",400,"权限"),
	Balance("Balance","DOUBLE",0,"余额"),
	Note("Note","VARCHAR",400,"后台标注"),
	UserNote("UserNote","VARCHAR",400,"用户标注"),
	PayTimeInfor("PayTimeInfor","VARCHAR",200,"支付时间信息"),
	Address("Address","VARCHAR",300,"地址"),
	Address1Name("Address1Name","VARCHAR",45,"地址1名"),
	Address1Value("Address1Value","VARCHAR",120,"地址1值"),
	Address2Name("Address2Name","VARCHAR",45,"地址2名"),
	Address2Value("Address2Value","VARCHAR",120,"地址2值"),
	Address3Name("Address3Name","VARCHAR",45,"地址3名"),
	Address3Value("Address3Value","VARCHAR",120,"地址3值"),
	Email("Email","VARCHAR",100,"电子邮件"),
	PhoneNumber("PhoneNumber","VARCHAR",20,"电话号码"),
	UserManual("UserManual","VARCHAR",100,"用户手册");

    private final String name;
    private final String dataType;
    private final int length;
    private final String chinese;

    EbizCompanyColumnEnum(String name,String dataType,int length,String chinese) {
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
    	EbizCompanyColumnEnum[] columns=EbizCompanyColumnEnum.values();
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
