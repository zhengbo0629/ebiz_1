package nameEnum;

public enum EbizPackageStatusEnum {
	//PartlyShipped("PartlyShipped"),
	NumberUnMatch("Number UnMatch"),
	UPCUnMatch("UPC UnMatch"),
	UPCNumberUnMatch("UPC and Num UnMatch"),
	Deleted("Deleted"),
	//Placed("Placed"),
	//Confirmed("Confirmed"),
	UnConfirmedInStock("UnConfirmed InStock"),
	UnConfirmedUnReceived("UnConfirmed UnReceived"),
	UnConfirmedUnPlaced("UnConfirmed UnPlaced"),
	//Refused("Refused"),
	InStock("InStock"),
	UnReceived("UnReceived"),
	EmailedLabel("EmailedLabel"),
	Packed("Packed"),
	Shipped("Shipped"),
	Delivered("Delivered"),
	Complete("Complete");

    private final String columnName;

    EbizPackageStatusEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

}
