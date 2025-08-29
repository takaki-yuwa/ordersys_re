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

		// 情報を取得
		HttpSession session = request.getSession();
		String tableNumber = (String) session.getAttribute("tableNumber");

		// tableNumberがnullの場合はデフォルト値0を設定（またはエラー処理を行う）
		if (tableNumber == null) {
			request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
			return;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			//情報の取得
			String sessionId = request.getParameter("sessionNumber");
			String tableId = request.getParameter("tableNumber");
			String sessionStatus = request.getParameter("sessionStatus");

			//セッションに保存
			HttpSession session = request.getSession();
			session.setAttribute("sessionNumber", sessionId);
			session.setAttribute("tableNumber", tableId);
			session.setAttribute("sessionStatus", sessionStatus);

			// 更新後は一覧画面にリダイレクト（PRGパターン推奨）
			response.sendRedirect(request.getContextPath() + "/OrderSystem");

		} catch (Exception e) {
			//ログに出力
			e.printStackTrace();

			//エラー画面に戻す
			request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
		}
	}
}
