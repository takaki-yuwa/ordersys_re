package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderConpletedDAO {

    public void insertOrderDetails(int order_id, int table_no, int[] product_quantities, double[] order_prices) {
        // データベース接続
        try (Connection con = DBUtil.getConnection()) {
            // トランザクション開始
            con.setAutoCommit(false);
            
            // 注文詳細の挿入用SQL
            String insertOrderDetailsSQL = "INSERT INTO order_details (order_id, product_quantity, order_price, table_number, accounting_flag) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertOrderDetailsSQL)) {
                
                // 各商品の注文詳細をループして挿入
                for (int i = 0; i < product_quantities.length; i++) {
                    // 注文詳細の金額（order_price）は既に計算されているものを使う
                    double order_price = order_prices[i];
                    
                    // SQLにパラメータ設定
                    ps.setInt(1, order_id);  // 注文ID
                    ps.setInt(2, product_quantities[i]);  // 商品個数
                    ps.setDouble(3, order_price);  // 注文金額
                    ps.setInt(4, table_no);  // 卓番
                    ps.setInt(5, 0);  // 会計フラグ（全て0）

                    // 注文詳細のデータを挿入
                    ps.addBatch();
                }

                // 一括挿入
                ps.executeBatch();

                // 商品在庫の更新
                updateProductStock(con, product_quantities);

                // コミット
                con.commit();

            } catch (SQLException e) {
                con.rollback();  // 失敗した場合はロールバック
                throw new RuntimeException("注文詳細の挿入中にエラーが発生しました。", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("データベース接続エラーが発生しました。", e);
        }
    }

    private void updateProductStock(Connection con, int[] product_quantities) throws SQLException {
        // 商品在庫の更新SQL
        String updateProductSQL = "UPDATE product SET stock = stock - ? WHERE product_id = ?";
        try (PreparedStatement ps = con.prepareStatement(updateProductSQL)) {
            for (int i = 0; i < product_quantities.length; i++) {
                // 商品の在庫を減らす処理を行うが、product_idは別で管理する必要がある
                // ここではproduct_idを別途管理する必要があります
                // ps.setInt(1, product_quantities[i]); // 減らす数量
                // ps.setInt(2, product_ids[i]); // 商品ID
                ps.addBatch();
            }
            ps.executeBatch();  // 一括更新
        }
    }
}
