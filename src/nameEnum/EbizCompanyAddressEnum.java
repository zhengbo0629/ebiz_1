package nameEnum;

public enum EbizCompanyAddressEnum {
	Address1("address 1"),
	Address2("address 2"),
	Address3("address 3"),
	Address4("ORWareHouse01"),
	Address5("DEWareHouse01");
    private final String name;
    EbizCompanyAddressEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static boolean isCompanyAddress(String name){
    	for(EbizCompanyAddressEnum element:EbizCompanyAddressEnum.values()){
    		if(name!=null&&name.equals(element.getName())){
    			return true;
    		}
    	}
    	return false;
    }

}
