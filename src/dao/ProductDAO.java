package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import servlet.product_list;

public class ProductDAO {

	public List<product_list> getAllProductNames() {
		List<product_list> productNameList = new ArrayList<>();

		try (Connection con = DBUtil.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st
						.executeQuery("SELECT * FROM product")) {

			while (rs.next()) {
				int id = rs.getInt("product_id");
				String name = rs.getString("product_name");
				int price = rs.getInt("product_price");
				String category = rs.getString("category_name");
				int stock = rs.getInt("product_stock");
				int displayflag = rs.getInt("product_display_flag");
				productNameList.add(new product_list(id, name, price, category, stock, displayflag));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productNameList;
	}
}
