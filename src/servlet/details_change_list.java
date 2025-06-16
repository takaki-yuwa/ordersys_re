package servlet;

public class details_change_list {
	private int order_id;
	private int product_id;
	private String product_name;
	private int product_price;
	private String category_name;
	private int product_subtotal;
//	private int[] topping_order_id;
	private int[] topping_id;
	private String[] topping_name;
	private int[] topping_price;
	private int[] topping_quantity;
	
	public details_change_list(int order_id, int product_id, String product_name, int product_price, String category_name, int product_subtotal,
			 int[] topping_id, String[] topping_name, int[] topping_price, int[] topping_quantity)
//	public details_change_list(int order_id, int product_id, String product_name, int product_price, String category_name, int product_subtotal,
//			int[] topping_order_id, int[] topping_id, String[] topping_name, int[] topping_price, int[] topping_quantity)
	{
		this.order_id=order_id;
		this.product_id=product_id;
		this.product_name=product_name;
		this.product_price=product_price;
		this.category_name=category_name;
		this.product_subtotal=product_subtotal;
//		this.topping_order_id=topping_order_id;
		this.topping_id=topping_id;
		this.topping_name=topping_name;
		this.topping_price=topping_price;
		this.topping_quantity=topping_quantity;
		
	}
	
	public int getOrder_id() {
		return order_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public int getProduct_price() {
		return product_price;
	}

	public String getCategory_name() {
		return category_name;
	}

	public int getProduct_subtotal() {
		return product_subtotal;
	}

//	public int[] getTopping_order_id() {
//		return topping_order_id;
//	}

	public int[] getTopping_id() {
		return topping_id;
	}

	public String[] getTopping_name() {
		return topping_name;
	}

	public int[] getTopping_price() {
		return topping_price;
	}

	public int[] getTopping_quantity() {
		return topping_quantity;
	}
}
