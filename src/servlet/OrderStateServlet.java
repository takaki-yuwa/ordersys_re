package servlet;

import java.io.IOException;

import dao.OrderStateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderStart/*")
public class OrderStateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			// パス情報からトークンを取得
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.length() <= 1) {
                request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
                return;
            }
            String table_token = pathInfo.substring(1); // 最初の "/" を除去

			System.out.println("トークンの値：" + table_token);

			//状態の更新と情報の取得
			OrderStateDAO dao = new OrderStateDAO();
			dao.updateSession(table_token);
			TableInfo tableInfo = dao.selectTableInfo(table_token);

			System.out.println("セッションID：" + tableInfo.getSession_id());
			System.out.println("卓番ID：" + tableInfo.getTable_id() + "卓");
			System.out.println("セッションの状態：" + tableInfo.getSession_status());

			//リクエスト属性にセット
			request.setAttribute("tableInfo", tableInfo);

			//次のページへフォワード
			request.getRequestDispatcher("/OrderStart.jsp").forward(request, response);

		} catch (Exception e) {

			System.err.println("セッションの状態がclosed、または接続中にエラーが発生しました: " + e.getMessage());
			//ログに出力
			e.printStackTrace();

			//エラー画面に戻す
			request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
		}
	}
}