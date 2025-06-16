package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

    // POSTリクエストを処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータの取得
        String[] order_id = request.getParameterValues("order_id[]");
        String[] product_id = request.getParameterValues("product_id[]");
        String[] product_quantity = request.getParameterValues("product_quantity[]");
        String[] topping_id = request.getParameterValues("topping_id[]");
        String[] topping_quantity = request.getParameterValues("topping_quantity[]");
        String[] order_price = request.getParameterValues("order_price[]");
        String table_number = request.getParameter("tableNo");

        // データベース接続
        try (Connection conn = DBUtil.getConnection()) {
            // トランザクション開始
            conn.setAutoCommit(false);

            OrderCompletedDAO dao = new OrderCompletedDAO();

            // 注文詳細を挿入
            boolean insertSuccess = dao.insertOrderDetails(conn, order_id, product_quantity, order_price, table_number);

            if (insertSuccess) {
                // 商品在庫の更新
                dao.updateProductStock(conn, product_id, product_quantity);
             // トッピング在庫の更新
                dao.updateToppingStock(conn, topping_id, topping_quantity);

                // コミット
                conn.commit();
                response.getWriter().println("データが正常に追加されました！");
            } else {
                response.getWriter().println("データの追加に失敗しました。");
            }

        } catch (SQLException e) {
            // ロールバック
            try (Connection conn = DBUtil.getConnection()) {
                conn.rollback();  // 失敗した場合(エラー)はロールバック
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            response.getWriter().println("エラーが発生しました: " + e.getMessage());
            e.printStackTrace();
        }
        
        request.getSession().invalidate(); // セッションの取得と情報削除
        request.getRequestDispatcher("/OrderCompleted.jsp").forward(request, response);
    }
}
