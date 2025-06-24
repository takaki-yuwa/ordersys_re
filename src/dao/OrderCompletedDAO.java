package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderCompletedDAO {

	// product_details に product_id を挿入するメソッド
	public List<Integer> insertProductDetails(Connection conn, String[] product_ids) throws SQLException {
	    String insertSQL = "INSERT INTO product_details (product_id) VALUES (?)";
	    List<Integer> generatedOrderIds = new ArrayList<>();

	    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        for (String product_id : product_ids) {
	            pstmt.setInt(1, Integer.parseInt(product_id));
	            pstmt.executeUpdate();  // 個別に実行して ID を取得

	            try (ResultSet rs = pstmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    int generatedId = rs.getInt(1);
	                    generatedOrderIds.add(generatedId);
	                }
	            }
	        }
	    }

	    return generatedOrderIds;
	}

	
	public boolean insertOrderDetails(Connection conn, String[] order_id, String[] product_quantity, String[] order_price, int tableNumberInt) throws SQLException {
	    String insertOrderDetailsSQL = "INSERT INTO order_details (order_id, product_quantity, order_price, table_number, order_time) VALUES (?, ?, ?, ?, ?)";

	    try (PreparedStatement pstmt = conn.prepareStatement(insertOrderDetailsSQL)) {
	        for (int i = 0; i < order_id.length; i++) {
	            pstmt.setInt(1, Integer.parseInt(order_id[i]));  // order_id
	            pstmt.setInt(2, Integer.parseInt(product_quantity[i]));  // product_quantity
	            pstmt.setDouble(3, Double.parseDouble(order_price[i]));  // order_price
	            pstmt.setInt(4, tableNumberInt);  // table_number
	            pstmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));  // order_time 


	            pstmt.addBatch();
	        }

	        int[] rowsAffected = pstmt.executeBatch();
	        return rowsAffected.length > 0;
	    }
	}


    
    // トッピング詳細を挿入するメソッド
    public boolean insertTopingDetails(Connection conn, String[] topping_id, String[] topping_quantity, String[] order_id) throws SQLException {
        String insertOrderDetailsSQL = "INSERT INTO multiple_toppings (topping_id, topping_quantity, order_id) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(insertOrderDetailsSQL)) {
            for (int i = 0; i < topping_id.length; i++) {
            	int toppingQuantity = Integer.parseInt(topping_quantity[i]);
            	
            	if(toppingQuantity == 0) {
            		continue;
            	}
                pstmt.setInt(1, Integer.parseInt(topping_id[i]));  // topping_id
                pstmt.setInt(2, Integer.parseInt(topping_quantity[i]));  // topping_quantity
                pstmt.setInt(3, Integer.parseInt(order_id[0]));  // order_id

                pstmt.addBatch();
            }

            int[] rowsAffected = pstmt.executeBatch();
            return rowsAffected.length > 0;
        }
    }


    // 商品の在庫を更新するメソッド
    public void updateProductStock(Connection conn, String[] product_ids, String[] product_quantities) throws SQLException {
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
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        int currentStock = rs.getInt("product_stock");

                        // 2. Java側で計算
                        int newStock = currentStock - quantity;
                        if (newStock < 0) {
                            newStock = 0; // 在庫がマイナスにならないように調整
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
    
    //トッピング在庫を更新するメソッド
	public void updateToppingStock(Connection conn, String[] topping_ids, String[] topping_quantities) throws SQLException {
	    String updateSQL = "UPDATE topping SET topping_stock = topping_stock - ? WHERE topping_id = ? AND topping_stock >= ?";

	    try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
	        for (int i = 0; i < topping_ids.length; i++) {
	            int toppingId = Integer.parseInt(topping_ids[i]);
	            int quantity = Integer.parseInt(topping_quantities[i]);

	            // 数量が0以下はスキップ
	            if (quantity <= 0) continue;

	            pstmt.setInt(1, quantity);     // 減らす量
	            pstmt.setInt(2, toppingId);    // 対象のトッピングID
	            pstmt.setInt(3, quantity);     // topping_stock >= quantity のチェック用
	            pstmt.addBatch();
	        }

	        int[] updateCounts = pstmt.executeBatch();
	        System.out.println("更新件数: " + java.util.Arrays.toString(updateCounts));
	    }
	}

}
