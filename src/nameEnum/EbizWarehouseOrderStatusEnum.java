package nameEnum;



public enum EbizWarehouseOrderStatusEnum {
	cancelled("cancelled"),
	Pending("Pending"),
	Preparing("Preparing"),
	LabelPrinted("Label Printed"),
	Shipping("Shipping"),
	PartlyShipped("Partly Shipped"),
	Problem("Problem"),
	Completed("Completed"),
	ReturnPending("Return Pending"),
	ReturnCompleted("Return Completed");
    private final String columnName;
    EbizWarehouseOrderStatusEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

}
