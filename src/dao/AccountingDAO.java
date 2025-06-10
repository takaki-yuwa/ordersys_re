package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountingDAO {

    // TotalPriceを使ってaccountingテーブルにデータを挿入するメソッド
    public int insertAccountingData(int totalPrice) {
        // 挿入するSQL文
        String sql = "INSERT INTO accounting (accounting_price) VALUES (?)";

        // 自動採番されたIDを格納する変数
        int accountingInformationId = -1;

        try (Connection con = DBUtil.getConnection(); // コネクション取得
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // プレースホルダーにTotalPriceをセット
            ps.setInt(1, totalPrice); // accounting_priceにTotalPriceをセット

            // SQLを実行
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " 行が挿入されました。");

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

        return accountingInformationId; // 自動採番されたIDを返す
    }
}
