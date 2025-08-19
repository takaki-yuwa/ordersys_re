package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import servlet.topping_list;

public class ToppingDAO {

	public List<topping_list> getToppingNames(int product_id) {
		List<topping_list> toppingList = new ArrayList<>();

		try (Connection con = DBUtil.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT tp.topping_id, tp.topping_name, tp.topping_price, tp.topping_stock, tp.topping_display_flag FROM topping tp INNER JOIN product_topping pt ON tp.topping_id = pt.topping_id WHERE pt.product_id = "
								+ product_id)) {

			while (rs.next()) {
				int id = rs.getInt("topping_id");
				String name = rs.getString("topping_name");
				int price = rs.getInt("topping_price");
				int stock = rs.getInt("topping_stock");
				int displayflag = rs.getInt("topping_display_flag");
				toppingList.add(new topping_list(id, name, price, stock, displayflag));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return toppingList;
	}

	public List<topping_list> getAllToppingNames() {
		List<topping_list> toppingList = new ArrayList<>();

		try (Connection con = DBUtil.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM topping")) {

			while (rs.next()) {
				int id = rs.getInt("topping_id");
				String name = rs.getString("topping_name");
				int price = rs.getInt("topping_price");
				int stock = rs.getInt("topping_stock");
				int displayflag = rs.getInt("topping_display_flag");
				toppingList.add(new topping_list(id, name, price, stock, displayflag));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return toppingList;
	}
}