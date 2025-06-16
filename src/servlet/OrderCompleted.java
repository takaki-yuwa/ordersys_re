package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DBUtil;

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
        String[] order_price = request.getParameterValues("order_price[]");
        String table_number = request.getParameter("tableNo");

        // データベース接続
        try (Connection conn = DBUtil.getConnection()) {
            // トランザクション開始
            conn.setAutoCommit(false);

            String insertOrderDetailsSQL = "INSERT INTO order_details (order_id, product_quantity, order_price, table_number, accounting_flag) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertOrderDetailsSQL)) {

                // 注文詳細をひとつづつプレースホルダにループして挿入
                for (int i = 0; i < order_id.length; i++) {
                    pstmt.setInt(1, Integer.parseInt(order_id[i]));  // order_id
                    pstmt.setInt(2, Integer.parseInt(product_quantity[i]));  // product_quantity
                    pstmt.setDouble(3, Double.parseDouble(order_price[i]));  // order_price
                    pstmt.setInt(4, Integer.parseInt(table_number));  // table_number
                    pstmt.setInt(5, 0);  // accounting_flag (全て0)

                    pstmt.addBatch();
                }

                // 用意したSQL文を一括挿入
                int[] rowsAffected = pstmt.executeBatch();
                if (rowsAffected.length > 0) {
                    // 成功メッセージ
                    response.getWriter().println("データが正常に追加されました！");
                } else {
                    // 失敗メッセージ
                    response.getWriter().println("データの追加に失敗しました。");
                }

                // 商品在庫の更新 (注文した商品の在庫を減らす)
                updateProductStock(conn, product_id, product_quantity);

                // コミット
                conn.commit();

            } catch (SQLException e) {
                conn.rollback();  // 失敗した場合(エラー)はロールバック
                response.getWriter().println("注文詳細の挿入中にエラーが発生しました: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            response.getWriter().println("データベース接続エラー: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("/OrderCompleted.jsp").forward(request, response);
    }

    private void updateProductStock(Connection conn, String[] product_ids, String[] product_quantities) throws SQLException {
        String selectStockSQL = "SELECT product_stock FROM product WHERE product_id = ?";
        String updateStockSQL = "UPDATE product SET product_stock = ? WHERE product_id = ?";

        try (
            PreparedStatement selectStmt = conn.prepareStatement(selectStockSQL);
            PreparedStatement updateStmt = conn.prepareStatement(updateStockSQL);
        ) {
            for (int i = 0; i < product_ids.length; i++) {
                int productId = Integer.parseInt(product_ids[i]);
                int quantity = Integer.parseInt(product_quantities[i]);

                // 1. 現在のstockを取得
                selectStmt.setInt(1, productId);
                try (var rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                    	int currentStock = rs.getInt("product_stock");

                        // 2. Java側で計算
                        int newStock = currentStock - quantity;
                        if (newStock < 0) {
                            newStock = 0; // 在庫がマイナスにならないように調整（必要に応じて例外処理も）
                        }

                        // 3. 更新用SQLにセット
                        updateStmt.setInt(1, newStock);
                        updateStmt.setInt(2, productId);
                        updateStmt.addBatch();
                    } else {
                        // product_idが見つからなかった場合の処理
                        System.out.println("商品ID " + productId + " の在庫が見つかりません。");
                    }
                }
            }
            // バッチで更新
            updateStmt.executeBatch();
        }
    }


}
