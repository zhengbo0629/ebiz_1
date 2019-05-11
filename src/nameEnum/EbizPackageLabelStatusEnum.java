package nameEnum;

public enum EbizPackageLabelStatusEnum {
	UnMadeLabel("UnMade"),
	MakingLable("MakingLable"),
	MadeLabel("MadeLabel");
    private final String name;
    EbizPackageLabelStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
