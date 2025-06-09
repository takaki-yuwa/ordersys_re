package servlet;

import java.util.ArrayList;
import java.util.List;

public class order_details_list {
	// 注文詳細
    private int order_details_id;
    private int order_id;
    private int product_quantity;
    private int order_price;
    private int table_number;
    // 商品詳細
    private String product_name;
    private int product_price;
    private int product_id;
    // トッピング
    List<multiple_topping_list> multipleToppingList;

    public order_details_list(int order_details_id, int order_id, int product_quantity, int order_price, int table_number,
    		String name, Integer price, List<multiple_topping_list> multipleToppingLis) {
    	this.order_details_id = order_details_id;
    	this.order_id = order_id;
        this.product_quantity = product_quantity;
        this.order_price = order_price;
        this.table_number = table_number;
    	this.product_name = name;
        this.product_price = price;
        this.multipleToppingList = new ArrayList<>(multipleToppingLis);
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

    public String getProduct_name() {
        return product_name;
    }

    public Integer getProduct_price() {
        return product_price;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void addmultipleToppingList(multiple_topping_list multipleToppingLis) {
    	multipleToppingList.add(multipleToppingLis);
    }

    public List<multiple_topping_list> getMultipleToppingList() {
    	return multipleToppingList;
    }
}
