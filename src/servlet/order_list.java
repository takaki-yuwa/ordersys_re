package servlet;

import java.util.ArrayList;
import java.util.List;

public class order_list {
	private List<Integer> menu_id;
    private List<String> product_name;
    private List<String> topping_name;
    private List<Integer> product_price;
    private List<Integer> topping_price;
    private List<Integer> menu_quantity;
    private List<Integer> menu_subtotal;
    private int total;

    public order_list(List<Integer> id, List<String> name, List<String> name2, List<Integer> price, List<Integer> price2, List<Integer> quantity, List<Integer> subtotal, int total) {
        this.menu_id=new ArrayList<>(id);
    	this.product_name = new ArrayList<>(name);
        this.topping_name = new ArrayList<>(name2);
        this.product_price = new ArrayList<>(price);
        this.topping_price = new ArrayList<>(price2);
        this.menu_quantity = new ArrayList<>(quantity);
        this.menu_subtotal = new ArrayList<>(subtotal);
        this.total = total;
    }

    public List<Integer> getMenu_id() {
    	return menu_id;
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

    public List<Integer> getMenu_quantity() {
        return menu_quantity;
    }

    public List<Integer> getMenu_subtotal() {
        return menu_subtotal;
    }

    public int getTotal() {
    	return total;
    }

}