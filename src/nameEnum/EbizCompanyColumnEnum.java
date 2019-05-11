package nameEnum;
public enum EbizCompanyColumnEnum {
	UID("id","MEDIUMINT",9,"��������"),
	CreatedTime("CreatedTime","VARCHAR",30,"����ʱ��"),
	UpdateTime("UpdateTime","VARCHAR",30,"����ʱ��"),
	CompanyName("CompanyName","VARCHAR",50,"��˾��"),
	OwnerName("OwnerName","VARCHAR",30,"ӵ����"),
	Status("Status","VARCHAR",20,"״̬"),
	Permision("Permision","VARCHAR",400,"Ȩ��"),
	Balance("Balance","DOUBLE",0,"���"),
	Note("Note","VARCHAR",400,"��̨��ע"),
	UserNote("UserNote","VARCHAR",400,"�û���ע"),
	PayTimeInfor("PayTimeInfor","VARCHAR",200,"֧��ʱ����Ϣ"),
	Address("Address","VARCHAR",300,"��ַ"),
	Address1Name("Address1Name","VARCHAR",45,"��ַ1��"),
	Address1Value("Address1Value","VARCHAR",120,"��ַ1ֵ"),
	Address2Name("Address2Name","VARCHAR",45,"��ַ2��"),
	Address2Value("Address2Value","VARCHAR",120,"��ַ2ֵ"),
	Address3Name("Address3Name","VARCHAR",45,"��ַ3��"),
	Address3Value("Address3Value","VARCHAR",120,"��ַ3ֵ"),
	Email("Email","VARCHAR",100,"�����ʼ�"),
	PhoneNumber("PhoneNumber","VARCHAR",20,"�绰����"),
	UserManual("UserManual","VARCHAR",100,"�û��ֲ�");

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
