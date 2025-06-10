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

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//データベース接続
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			//データベース接続
			conn = DriverManager.getConnection(URL, USER, PASSWD);

			//SQLクエリを準備
			String sql = "INSERT INTO order_details (order_id,product_quantity,order_price,table_number) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			//パラメータを設定
			String[] order_id = request.getParameterValues("order_id");
			String[] product_quantity = request.getParameterValues("product_quantity[]");
			String[] order_price = request.getParameterValues("order_price[]");
			String table_number = request.getParameter("tableNumber");

			for (int i = 0; i < order_id.length; i++) {
				pstmt.setInt(1, Integer.parseInt(order_id[i])); //order_id
				pstmt.setInt(2, Integer.parseInt(product_quantity[i])); //product_quantity
				pstmt.setInt(3, Integer.parseInt(order_price[i])); //order_price
				pstmt.setInt(4, Integer.parseInt(table_number)); //table_number
				pstmt.addBatch();
			}

			//SQLを実行
			int rowsAffected = pstmt.executeUpdate();

			// レスポンスに成功メッセージを表示
			if (rowsAffected > 0) {
				response.getWriter().println("データが正常に追加されました！");
			} else {
				response.getWriter().println("データの追加に失敗しました。");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("データベース接続エラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		request.getRequestDispatcher("/WEB-INF/JSP/OrderCompleted.jsp").forward(request, response);
	}
}