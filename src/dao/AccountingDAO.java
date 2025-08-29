package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountingDAO {

	// 会計情報を挿入後、関連するorder_detailsとmultiple_order_detailsの処理を行うメソッド
	public void processAccountingData(int totalPrice, int sessionId) {
		// トランザクション開始
		try (Connection con = DBUtil.getConnection()) {
			// 自動コミットを無効にする
			con.setAutoCommit(false); // トランザクション開始

			System.out.println("コネクション取得成功");

			// 1. 会計情報をinsertし、自動採番された会計情報IDを取得
			int accountingInformationId = insertAccountingData(totalPrice);
			System.out.println("会計情報ID: " + accountingInformationId);

			// 2. order_detailsテーブルからaccounting_flagが0のorder_details_idをすべて取ってくる
			String selectSql = "SELECT order_details_id, session_id FROM order_details WHERE accounting_flag = 0";
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
							int orderSessionId = rs.getInt("session_id");
							int orderDetailsId = rs.getInt("order_details_id");
							if (sessionId == orderSessionId) {
								// 5. multiple_order_detailsにorder_details_idと会計情報IDを挿入
								insertPs.setInt(1, orderDetailsId);
								insertPs.setInt(2, accountingInformationId);
								insertPs.addBatch(); // バッチ処理で一度に挿入

								// 6. order_detailsテーブルのaccounting_flagを1に更新
								updatePs.setInt(1, orderDetailsId);
								updatePs.executeUpdate();

								System.out.println("order_details_id: " + orderDetailsId + " に会計情報IDを関連付け");
							}

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

	public void processSession(int tableNo, String sessionNumberStr) {
		// 挿入するSQL文
		String updateSql = "UPDATE table_sessions AS s\n"
				+ "JOIN table_master AS m ON s.table_id = m.table_id\n"
				+ "SET\n"
				+ "s.session_status = 'closed', \n"
				+ "s.end_time = NOW(),\n"
				+ "m.table_status = 'inactive',\n"
				+ "m.updated_at = NOW()\n"
				+ "WHERE s.session_id = ?;\n"
				+ "";
		
		String insertsql = "INSERT INTO table_sessions (table_id, session_status, url_token) VALUES (?,'inactive', CONCAT(UUID(), '-', SUBSTRING(MD5(RAND()), 1, 8)))";
		

			try (Connection con = DBUtil.getConnection();
					PreparedStatement insertPs = con.prepareStatement(insertsql);
					PreparedStatement updatePs = con.prepareStatement(updateSql)) {
				
				 // トランザクション開始
		        con.setAutoCommit(false);
				
				// プレースホルダーにsessionIdをセット
				updatePs.setString(1, sessionNumberStr);
				updatePs.executeUpdate();
				
				// プレースホルダーにtableNoをセット
				insertPs.setInt(1, tableNo);
				insertPs.executeUpdate();
				
				// トランザクションをコミット
				con.commit();
				System.out.println("セッション情報をコミットしました");
				
			}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
