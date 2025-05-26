package servlet;

public class order_list {
	private String[] name;
	private String[] name2;
	private String[] price;
	private String[] price2;
	private String[] subtotal;
	
	public order_list(String[] name, String[] name2, String[] price, String[] price2, String[] subtotal) {
		this.name=name;
		this.name2=name2;
		this.price=price;
		this.price2=price2;
		this.subtotal=subtotal;
	}
	
	public String[] getName() {
		return name;
	}
	
	public String[] getName2() {
		return name2;
	}
	
	public String[] getPrice() {
		return price;
	}
	
	public String[] getPrice2() {
		return price2;
	}
	
	public String[] getSubtotal() {
		return subtotal;
	}
}
