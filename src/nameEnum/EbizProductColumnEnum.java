package nameEnum;

public enum EbizProductColumnEnum {
	UID("id"),
	Company("Company"),
	ModelNumber("Model"),
	ProductName("ProductName"),
	Condition("Condition"),
	UPC("UPC"),
	ASIN("ASIN"),
	SKU("SKU"),
	Brand("Brand"),
	Price("Price"),
	Reported("Reported"),
	Incoming("Incoming"),
	TotalReceived("Received"),
	QuantityShipping("Shipping"),
	TotalQuantyShipped("Shipped"),
	Instock("In Stock"),
	QuantityPreSend("PreSend"),
	CreatedTime("Created Time"),
	UpdateTime("Update Time"),
	Note("Note"),
	WareHouse("WareHouse"),
	CheckBox("Check");




    private final String columnName;

    EbizProductColumnEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

}
