package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DBUtil;
import dao.OrderCompletedDAO;

@WebServlet("/OrderCompleted")
public class OrderCompleted extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] product_id = request.getParameterValues("product_id[]");
        String[] product_quantity = request.getParameterValues("product_quantity[]");
        String[] topping_id = request.getParameterValues("topping_id[]");
        String[] topping_quantity = request.getParameterValues("topping_quantity[]");
        String[] order_price = request.getParameterValues("order_price[]");

        System.out.println("product_id: " + Arrays.toString(product_id));
        System.out.println("product_quantity: " + Arrays.toString(product_quantity));
        System.out.println("order_price: " + Arrays.toString(order_price));
        System.out.println("topping_id: " + Arrays.toString(topping_id));
        System.out.println("topping_quantity: " + Arrays.toString(topping_quantity));

        String table_number = "3";

        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            OrderCompletedDAO dao = new OrderCompletedDAO();

            // 1. product_details に product_id を挿入し、order_id を取得
            List<Integer> orderIdList = dao.insertProductDetails(conn, product_id);

            // 2. List<Integer> → String[] に変換
            String[] order_id = orderIdList.stream().map(String::valueOf).toArray(String[]::new);

            // 3. order_details に挿入
            boolean insertSuccess = dao.insertOrderDetails(conn, order_id, product_quantity, order_price, table_number);

            if (insertSuccess) {
//                dao.updateProductStock(conn, product_id, product_quantity);//商品在庫を更新
//                dao.updateToppingStock(conn, topping_id, topping_quantity);
                conn.commit();
                response.getWriter().println("すべてのデータが正常に追加されました！");
            } else {
                conn.rollback();
                response.getWriter().println("注文詳細の追加に失敗しました。");
            }

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            response.getWriter().println("エラーが発生しました: " + e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        request.getSession().invalidate();
        request.getRequestDispatcher("/OrderCompleted.jsp").forward(request, response);
    }
}
