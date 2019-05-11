package nameEnum;

public enum EbizCompanyPayPeriodEnum {
	TwoMonth("Two Month"),
	SixWeeks("Six Weeks"),
	FiveWeeks("Five Weeks"),
	OneMonth("One Month"),
	FourWeeks("Four Weeks"),
	ThreeWeeks("Three Weeks"),
	TwoWeeks("Two Weeks"),
	OneWeek("One Week"),
	DoNotSet("Do Set Payment Date"); 
    private final String name;

    EbizCompanyPayPeriodEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
