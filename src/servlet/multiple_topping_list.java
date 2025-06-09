package servlet;

public class multiple_topping_list {
	private String topping_name;
	private int topping_quantity;

	public multiple_topping_list(String topping_name, int topping_quantity) {
		this.topping_name = topping_name;
		this.topping_quantity = topping_quantity;
	}

	public String getTopping_name() {
		return topping_name;
	}

	public int getTopping_quantity() {
		return topping_quantity;
	}
}
