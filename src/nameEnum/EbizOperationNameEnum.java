package nameEnum;


public enum EbizOperationNameEnum {
	//UID("UID"),
	//only active user are included
	DeleteRow("Delete","ɾ����"),  
	AddRow("Add","�����"), 
	UpdateColumn("Update","����");

    private final String name;
    private final String chinese;

    EbizOperationNameEnum(String name,String chinese) {
        this.name = name;
        this.chinese=chinese;
    }

    public String getName() {
        return name;
    }
    public String getChinese(){
    	return chinese;
    }

}
