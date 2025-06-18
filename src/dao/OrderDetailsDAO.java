package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import servlet.multiple_topping_list;
import servlet.order_details_list;

public class OrderDetailsDAO {

    public List<order_details_list> findOrderHistory(int search_table_number, int search_order_price)
    {
        List<order_details_list> orderDetailsList = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT order_details_id, order_id, product_quantity, order_price, table_number, accounting_flag FROM order_details where table_number = " 
            + search_table_number + " AND accounting_flag = " + 0))
            //+ search_table_number + " AND order_price = " + search_order_price + " AND accounting_flag = " + 0))
        {

            while (rs.next())
            {
            	int order_details_id = rs.getInt("order_details_id");
                int order_id = rs.getInt("order_id");
                int product_quantity = rs.getInt("product_quantity");
                int order_price = rs.getInt("order_price");
                int table_number = rs.getInt("table_number");

                // 商品詳細テーブル取得
                try ( Statement st2 = con.createStatement();
	                ResultSet rs_product_details = st2.executeQuery("SELECT product_id FROM product_details where order_id = " + order_id))
                {
	                if (rs_product_details.next())
	                {
	                    int product_id = rs_product_details.getInt("product_id");
	
	                    // 商品テーブル取得
	                    try ( Statement st3 = con.createStatement();
		                    ResultSet rs_products = st3.executeQuery("SELECT product_name, product_price FROM product where product_id = " + product_id))
	                    {
		                    if (rs_products.next())
		                    {
		                        String product_name = rs_products.getString("product_name");
		                        int product_price = rs_products.getInt("product_price");
		
				                // トッピングテーブル取得
				                List<multiple_topping_list> multipleToppingList = new ArrayList<>();
				                try ( Statement st4 = con.createStatement();
					                ResultSet rs_topping = st4.executeQuery("SELECT topping.topping_name, multiple_toppings.topping_quantity FROM topping Join multiple_toppings ON topping.topping_id = multiple_toppings.topping_id where multiple_toppings.order_id = " + order_id))
					                {
					                while(rs_topping.next())
					                {
					                    String topping_name = rs_topping.getString("topping_name");
					                    int topping_quantity = rs_topping.getInt("topping_quantity");
					                    multipleToppingList.add(new multiple_topping_list(topping_name, topping_quantity));
					                }
					                orderDetailsList.add(new order_details_list(order_details_id, order_id, product_quantity, order_price, table_number,
					                        product_name, product_price, multipleToppingList));
				                }
		                    }
	                    }
	                }
                }
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderDetailsList;
    }
}
