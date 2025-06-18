package servlet;

import java.util.ArrayList;
import java.util.List;

public class order_list {
	private Integer order_id;
	private Integer product_id;
	private List<Integer> topping_id;
	private String product_name;
	private String category_name;
	private List<String> topping_name;
	private Integer product_price;
	private List<Integer> topping_price;
	private List<Integer> topping_quantity;
	private Integer menu_quantity;
	private Integer menu_stock;
	private Integer menu_subtotal;

	private int menu_total;

	public order_list(Integer order_id, Integer product_id, List<Integer> topping_id, String product_name, String category_name, List<String> topping_name,
			Integer product_price, List<Integer> topping_price, List<Integer> topping_quantity,
			Integer menu_quantity, Integer menu_stock, Integer menu_subtotal, int menu_total) {
		this.order_id = order_id;
		this.product_id=product_id;
		this.topping_id=new ArrayList<>(topping_id);
		this.product_name = product_name;
		this.category_name=category_name;
		this.topping_name = new ArrayList<>(topping_name);
		this.product_price = product_price;
		this.topping_price = new ArrayList<>(topping_price);
		this.topping_quantity = new ArrayList<>(topping_quantity);
		this.menu_quantity = menu_quantity;
		this.menu_stock = menu_stock;
		this.menu_subtotal = menu_subtotal;
		this.menu_total = menu_total;
	}
	
	public order_list(Integer order_id, Integer product_id, List<Integer> topping_id, String product_name, String category_name, List<String> topping_name,
			Integer product_price, List<Integer> topping_price, List<Integer> topping_quantity,
			Integer menu_subtotal) {
		this.order_id = order_id;
		this.product_id=product_id;
		this.topping_id=new ArrayList<>(topping_id);
		this.product_name = product_name;
		this.category_name=category_name;
		this.topping_name = new ArrayList<>(topping_name);
		this.product_price = product_price;
		this.topping_price = new ArrayList<>(topping_price);
		this.topping_quantity = new ArrayList<>(topping_quantity);
		this.menu_subtotal = menu_subtotal;
	}
	
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public void setTopping_id(List<Integer> topping_id) {
		this.topping_id = topping_id;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public void setTopping_name(List<String> topping_name) {
		this.topping_name = topping_name;
	}

	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
	}

	public void setTopping_price(List<Integer> topping_price) {
		this.topping_price = topping_price;
	}

	public void setTopping_quantity(List<Integer> topping_quantity) {
		this.topping_quantity = topping_quantity;
	}

	public void setMenu_quantity(Integer menu_quantity) {
		this.menu_quantity = menu_quantity;
	}

	public void setMenu_stock(Integer menu_stock) {
		this.menu_stock = menu_stock;
	}

	public void setMenu_subtotal(Integer menu_subtotal) {
		this.menu_subtotal = menu_subtotal;
	}

	public void setMenu_total(int menu_total) {
		this.menu_total = menu_total;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public List<Integer> getTopping_id() {
		return topping_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public String getCategory_name() {
		return category_name;
	}

	public List<String> getTopping_name() {
		return topping_name;
	}

	public Integer getProduct_price() {
		return product_price;
	}

	public List<Integer> getTopping_price() {
		return topping_price;
	}

	public List<Integer> getTopping_quantity() {
		return topping_quantity;
	}

	public Integer getMenu_quantity() {
		return menu_quantity;
	}

	public Integer getMenu_stock() {
		return menu_stock;
	}

	public Integer getMenu_subtotal() {
		return menu_subtotal;
	}

	public int getMenu_total() {
		return menu_total;
	}
}
