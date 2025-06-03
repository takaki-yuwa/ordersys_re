package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderCompleted")
public class OrderCompleted extends HttpServlet {
	
    private static final String URL = "jdbc:mysql://localhost:3306/order_management";
    private static final String USER = "root";
    private static final String PASSWD = "";
	
	private static final long serialVersionUID=1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//データベース接続
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			//データベース接続
			conn=DriverManager.getConnection(URL,USER,PASSWD);
			
			//SQLクエリを準備
			String sql="INSERT INTO order_details (order_id,product_quantity,order_price,table_number) VALUES (?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			
			//パラメータを設定
			pstmt.setLong(1, 1);	//order_id
			pstmt.setInt(2, 10);	//product_quantity
			pstmt.setInt(3, 1230);	//order_price
			pstmt.setInt(4, 3);		//table_number
			
			//SQLを実行
			int rowsAffected = pstmt.executeUpdate();
			
            // レスポンスに成功メッセージを表示
            if (rowsAffected > 0) {
                response.getWriter().println("データが正常に追加されました！");
            } else {
                response.getWriter().println("データの追加に失敗しました。");
            }
			
		}catch(Exception e) {
			e.printStackTrace();
            response.getWriter().println("データベース接続エラー: " + e.getMessage());
		}finally {
            // リソースの解放
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		
		request.getRequestDispatcher("/WEB-INF/JSP/OrderCompleted.jsp").forward(request, response);
	}
}
