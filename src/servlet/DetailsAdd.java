package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ToppingDAO;

@WebServlet("/DetailsAdd")
public class DetailsAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// キャッシュ制御ヘッダーを設定
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
		response.setHeader("Pragma", "no-cache"); // HTTP/1.0
		response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

		// 製品情報のパラメータを取得
		String p_id = request.getParameter("id");
		String name = request.getParameter("name");
		String p_price = request.getParameter("price");
		String category = request.getParameter("category");
		String p_displayflag = request.getParameter("displayflag");

		// stockのnullチェックと変換処理
		int stock = 0;
		String stockStr = request.getParameter("stock");
		if (stockStr != null && !stockStr.isEmpty()) {
			try {
				stock = Integer.parseInt(stockStr);
			} catch (NumberFormatException e) {
				System.out.println("無効な数値: stock=" + stockStr);
			}
		}

		int id = 0;
		int price = 0;
		int displayflag = 0;

		// idの変換処理
		if (p_id != null && !p_id.isEmpty()) {
			try {
				id = Integer.parseInt(p_id);
			} catch (NumberFormatException e) {

			}
		}

		// priceの変換処理
		if (p_price != null && !p_price.isEmpty()) {
			try {
				price = Integer.parseInt(p_price);
			} catch (NumberFormatException e) {
				System.out.println("無効な数値: price=" + p_price);
			}
		}

		// displayの変換処理
		if (p_displayflag != null && !p_displayflag.isEmpty()) {
			try {
				displayflag = Integer.parseInt(p_displayflag);
			} catch (NumberFormatException e) {

			}
		}

		// 製品リストオブジェクトの作成
		product_list productList = new product_list(id, name, price, category, stock, displayflag);

		// セッションに製品情報をセット
		HttpSession session = request.getSession();
		session.setAttribute("productList", productList);

		// トッピング情報の取得
		ToppingDAO dao = new ToppingDAO();
		List<topping_list> toppingList = dao.getToppingNames(id);

		// トッピング情報をリクエストにセット
		request.setAttribute("topping_list", toppingList);

		// JSPに転送
		request.getRequestDispatcher("/ProductDetailsAdd.jsp").forward(request, response);
	}
}
