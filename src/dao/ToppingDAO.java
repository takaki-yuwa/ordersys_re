package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ToppingDAO {

    public List<String> getAllProductNames() {
        List<String> toppingNameList = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT topping_name FROM topping")) {

            while (rs.next()) {
                toppingNameList.add(rs.getString("topping_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return toppingNameList;
    }
}
