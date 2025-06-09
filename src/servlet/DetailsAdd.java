package servlet;

import java.io.IOException;
import java.util.List;

import dao.ToppingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DetailsAdd")
public class DetailsAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 製品情報のパラメータを取得
		String p_id = request.getParameter("id");
		String name = request.getParameter("name");
		String p_price = request.getParameter("price");
		String category = request.getParameter("category");

		int id = 0;
		int price = 0;

		// idの変換処理
		if (p_id != null && !p_id.isEmpty()) {
			try {
				id = Integer.parseInt(p_id);
			} catch (NumberFormatException e) {
				System.out.println("無効な数値: id=" + p_id);
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

		// 製品リストオブジェクトの作成
		product_list productList = new product_list(id, name, price, category);

		// セッションに製品情報をセット
		HttpSession session = request.getSession();
		session.setAttribute("productList", productList);

		// トッピング情報の取得
		ToppingDAO dao = new ToppingDAO();
		List<topping_list> toppingList = dao.getToppingNames(id);

		// トッピング情報をリクエストにセット
		request.setAttribute("topping_list", toppingList);

		// JSPに転送
		request.getRequestDispatcher("/WEB-INF/JSP/ProductDetailsAdd.jsp").forward(request, response);
	}
}
