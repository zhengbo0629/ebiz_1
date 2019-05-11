package nameEnum;


public enum EbizNurseGroupTypeEnum {
	//UID("UID"),
	//only active user are included
	AllUser("All Users","所有用户"), //所有用户
	NewUser("New Users","新用户"),  //新用户，注册三个月以内的 ，默认checked
	OnePackagesUser("One Packages Users","一单用户"), //三个月内交易过1次的用户 默认checked。
	FivePackagesUser("Five Packages Users","五单用户"), //三个月内交易过5次的用户
	TenPackagesUser("Ten Packages Users","十单用户"),//三个月内交易过10次的用户
	TrustedUser("Trusted User","信任用户"), //标注过的信任用户，默认能收到所有的群发邮件；默认是checked。
	UnTrustedUser("UnTrusted User","非信任用户"); //标注过的非信任用户 除非选这个，默认是不会收到群发邮件。哪怕符合上面的条件

    private final String name;
    private final String chinese;

    EbizNurseGroupTypeEnum(String name,String chinese) {
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
