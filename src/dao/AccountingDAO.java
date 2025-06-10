package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountingDAO {

    // 会計情報を挿入後、関連するorder_detailsとmultiple_order_detailsの処理を行うメソッド
    public void processAccountingData(int totalPrice) {
        // トランザクション開始
        try (Connection con = DBUtil.getConnection()) {
            // 自動コミットを無効にする
            con.setAutoCommit(false); // トランザクション開始

            System.out.println("コネクション取得成功");

            // 1. 会計情報をinsertし、自動採番された会計情報IDを取得
            int accountingInformationId = insertAccountingData(totalPrice);
            System.out.println("会計情報ID: " + accountingInformationId);

            // 2. order_detailsテーブルからaccounting_flagが0のorder_details_idをすべて取ってくる
            String selectSql = "SELECT order_details_id FROM order_details WHERE accounting_flag = 0";
            try (PreparedStatement ps = con.prepareStatement(selectSql);
                 ResultSet rs = ps.executeQuery()) {

                System.out.println("order_detailsテーブルからデータ取得開始");

                // 3. multiple_order_detailsテーブルに取ってきたorder_details_idと会計情報IDを結び付けて格納
                String insertMultipleOrderDetailsSql = "INSERT INTO multiple_order_details (order_details_id, accounting_information_id) VALUES (?, ?)";
                try (PreparedStatement insertPs = con.prepareStatement(insertMultipleOrderDetailsSql)) {
                    // 4. order_detailsテーブルのaccounting_flagを1に更新
                    String updateSql = "UPDATE order_details SET accounting_flag = 1 WHERE order_details_id = ?";
                    try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {
                        while (rs.next()) {
                            int orderDetailsId = rs.getInt("order_details_id");

                            // 5. multiple_order_detailsにorder_details_idと会計情報IDを挿入
                            insertPs.setInt(1, orderDetailsId);
                            insertPs.setInt(2, accountingInformationId);
                            insertPs.addBatch(); // バッチ処理で一度に挿入

                            // 6. order_detailsテーブルのaccounting_flagを1に更新
                            updatePs.setInt(1, orderDetailsId);
                            updatePs.executeUpdate();

                            System.out.println("order_details_id: " + orderDetailsId + " に会計情報IDを関連付け");
                        }

                        // バッチ処理を実行
                        int[] rowsAffected = insertPs.executeBatch();
                        System.out.println("multiple_order_detailsに挿入された行数: " + rowsAffected.length);

                        // トランザクションをコミット
                        con.commit();
                        System.out.println("トランザクションをコミットしました");

                    } catch (SQLException e) {
                        con.rollback(); // エラー時はロールバック
                        System.out.println("エラーが発生しました。ロールバックします。");
                        e.printStackTrace();
                    }
                }
            }

        } catch (SQLException e) {
            // DB接続やSQLエラーの場合に詳細なエラーメッセージを表示
            System.out.println("SQLエラーが発生しました:");
            e.printStackTrace();
        }
    }

    // 会計情報をinsertし、IDを返すメソッド
    public int insertAccountingData(int totalPrice) {
        // 挿入するSQL文
        String sql = "INSERT INTO accounting (accounting_price) VALUES (?)";
        int accountingInformationId = -1;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // プレースホルダーにTotalPriceをセット
            ps.setInt(1, totalPrice);

            // SQLを実行
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " 行がaccountingテーブルに挿入されました。");

            // 自動採番されたIDを取得
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    accountingInformationId = generatedKeys.getInt(1); // 自動生成された主キー
                    System.out.println("自動採番された会計情報ID: " + accountingInformationId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountingInformationId;
    }
}
