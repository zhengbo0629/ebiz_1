package nameEnum;

public enum EbizPackageCheckStatusEnum {
	UnChecked("UnChecked"),
	Checking("Checking"),
	Checked("Checked");
    private final String name;
    EbizPackageCheckStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
