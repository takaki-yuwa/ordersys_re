package servlet;

public class order_details_list {
	private int order_details_id;
	private int order_id;
    private int product_quantity;
    private int order_price;
    private int table_number;

    public order_details_list(int order_details_id, int order_id, int product_quantity, int order_price, int table_number) {
    	this.order_details_id = order_details_id;
    	this.order_id = order_id;
        this.product_quantity = product_quantity;
        this.order_price = order_price;
        this.table_number = table_number;
    }

    public int getorder_details_id() {
        return order_details_id;
    }
    
    public int getorder_id() {
        return order_id;
    }

    public int getproduct_quantity() {
        return product_quantity;
    }
    
    public int getorder_price() {
        return order_price;
    }

    public int gettable_number() {
        return table_number;
    }
}
