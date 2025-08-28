package servlet;

import java.io.IOException;
import java.util.List;

import constants.Constants;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderSystem")
public class product_name extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// キャッシュ制御ヘッダーを設定
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
		response.setHeader("Pragma", "no-cache"); // HTTP/1.0
		response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

		// 卓番パラメータを取得
		String table_num = request.getParameter("tableNumber");

		// セッションを取得
		HttpSession session = request.getSession();

		// 卓番を設定
		if ("table1".equals(table_num)) {
			session.setAttribute("tableNumber", 1); // table1からの遷移は卓番1
		} else if ("table2".equals(table_num)) {
			session.setAttribute("tableNumber", 2); // table2からの遷移は卓番2
		} else {
			// tableNumberをセッションから取得
			Integer tableNumber = (Integer) session.getAttribute("tableNumber");

			// tableNumberがnullの場合はデフォルト値0を設定（またはエラー処理を行う）
			if (tableNumber == null) {
				request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
			}
		}

		// 商品リストを取得
		ProductDAO dao = new ProductDAO();
		List<product_list> productNameList = dao.getAllProductNames();

		// 商品リストをリクエスト属性にセット
		request.setAttribute("product_list", productNameList);
		request.setAttribute("categoryList", Constants.CATEGORY_LIST);

		// 次のページ (OrderMenu.jsp) へフォワード
		request.getRequestDispatcher("/OrderMenu.jsp").forward(request, response);
	}
}
