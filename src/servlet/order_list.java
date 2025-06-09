package servlet;

import java.util.ArrayList;
import java.util.List;

public class order_list {
	private List<Integer> order_id;
	private List<String> product_name;
	private List<String> topping_name;
	private List<Integer> product_price;
	private List<Integer> topping_price;
	private List<Integer> topping_quantity;
	private List<Integer> menu_quantity;
	private List<Integer> menu_stock;
	private List<Integer> menu_subtotal;

	private int menu_total;

	public order_list(List<Integer> order_id, List<String> product_name, List<String> topping_name,
			List<Integer> product_price, List<Integer> topping_price, List<Integer> topping_quantity,
			List<Integer> menu_quantity, List<Integer> menu_stock, List<Integer> menu_subtotal, int menu_total) {
		this.order_id = new ArrayList<>(order_id);
		this.product_name = new ArrayList<>(product_name);
		this.topping_name = new ArrayList<>(topping_name);
		this.product_price = new ArrayList<>(product_price);
		this.topping_price = new ArrayList<>(topping_price);
		this.topping_quantity = new ArrayList<>(topping_quantity);
		this.menu_quantity = new ArrayList<>(menu_quantity);
		this.menu_stock = new ArrayList<>(menu_stock);
		this.menu_subtotal = new ArrayList<>(menu_subtotal);
		this.menu_total = menu_total;
	}

	public List<Integer> getMenu_id() {
		return order_id;
	}

	public List<Integer> getOrder_id() {
		return order_id;
	}

	public List<String> getProduct_name() {
		return product_name;
	}

	public List<String> getTopping_name() {
		return topping_name;
	}

	public List<Integer> getProduct_price() {
		return product_price;
	}

	public List<Integer> getTopping_price() {
		return topping_price;
	}

	public List<Integer> getTopping_quantity() {
		return topping_quantity;
	}

	public List<Integer> getMenu_quantity() {
		return menu_quantity;
	}

	public List<Integer> getMenu_stock() {
		return menu_stock;
	}

	public List<Integer> getMenu_subtotal() {
		return menu_subtotal;
	}

	public int getMenu_total() {
		return menu_total;
	}
}
