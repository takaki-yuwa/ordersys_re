package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class Menu1 extends HttpServlet {

    public static final String URL = "jdbc:mysql://localhost:3306/order_management";
    public static final String USER = "root";
    public static final String PASSWD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        List<product_list> productList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWD);
            Statement st = con.createStatement();

            // 商品だけを取得
            String sql = "SELECT product_name, product_price, category_name  FROM product ";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("product_name");
                int price = rs.getInt("product_price");
                String category = rs.getString("category_name");
                productList.add(new product_list(name, price, category));
            }

            rs.close();
            st.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("product_list", productList);
        request.getRequestDispatcher("/OrderMenu.jsp").forward(request, response);
    }
}
