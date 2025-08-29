package servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderList")
public class OrderList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SESSION_LIST_KEY = "orderList";

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// キャッシュ制御ヘッダーを設定
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
		response.setHeader("Pragma", "no-cache"); // HTTP/1.0
		response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

		// seccionからorderList取得
		HttpSession session = request.getSession();
		List<order_list> orderList = (List<order_list>) session.getAttribute(SESSION_LIST_KEY);
		if (null == orderList) {
			orderList = new ArrayList<>();
		}

		String tableNumberStr = (String) session.getAttribute("tableNumber");
		int tableNumber = 0;
		if (tableNumberStr != null) {
			try {
				tableNumber = Integer.parseInt(tableNumberStr);
			} catch (NumberFormatException e) {
				// 無効な数値の場合はエラー処理
				System.out.println("無効な tableNumber: " + tableNumberStr);
				request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
				return;
			}
		} else {
			// null の場合はエラー画面へ
			request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
			return;
		}

		// 遷移元を確認
		String url = request.getHeader("REFERER");
		String pageName = "";
		if (url != null) {
			try {
				// URLをパース
				@SuppressWarnings("deprecation")
				URL parsedUrl = new URL(url);
				String path = parsedUrl.getPath(); // 例: /path/to/page.html

				// 最後のスラッシュ以降を取得
				pageName = path.substring(path.lastIndexOf('/') + 1);

				System.out.println("ページ名: " + pageName);
			} catch (MalformedURLException e) {
				e.printStackTrace(); // URLが不正な場合の処理
			}
		}

		String urlDetailsAdd = "DetailsAdd";
		String urlDetailsChange = "DetailsChange";
		if (urlDetailsAdd.equals(pageName)) {
			// order_id の自動付番
			Integer maxOrderId = orderList.stream().mapToInt(order -> order.getOrder_id()).max().orElse(0);
			Integer order_id = maxOrderId + 1;

			String product_name = request.getParameter("product_name");
			String category_name = request.getParameter("category_name");
			Integer product_price = Integer.parseInt(request.getParameter("product_price"));
			Integer menu_quantity = 1;
			Integer menu_stock = 20;
			Integer menu_subtotal = product_price; // 初期は商品価格のみ
			int menu_total = product_price;

			// 商品ID
			Integer iProductId = Integer.parseInt(request.getParameter("product_id"));

			// トッピング情報を配列で取得
			String[] strToppingId = request.getParameterValues("topping");
			String[] strToppingName = request.getParameterValues("topping_name");
			String[] strToppingPrice = request.getParameterValues("topping_price");
			String[] strToppingQty = request.getParameterValues("topping_quantity");

			List<Integer> topping_id = new ArrayList<>();
			List<String> topping_name = new ArrayList<>();
			List<Integer> topping_price = new ArrayList<>();
			List<Integer> topping_quantity = new ArrayList<>();

			int toppingSum = 0;

			if (strToppingId != null) {
				for (int i = 0; i < strToppingId.length; i++) {
					try {
						int qty = Integer.parseInt(strToppingQty[i]);
						int price = Integer.parseInt(strToppingPrice[i]);

						topping_id.add(Integer.parseInt(strToppingId[i]));
						topping_name.add(strToppingName[i]);
						topping_price.add(price);
						topping_quantity.add(qty);

						toppingSum += price * qty; // 個数 × 価格で合計
					} catch (NumberFormatException e) {
						System.out.println("無効な数値: トッピング情報 index=" + i);
					}
				}
			}

			menu_total += toppingSum; // 商品価格 + トッピング合計
			menu_subtotal = menu_total;

			orderList.add(new order_list(order_id, iProductId, topping_id, product_name, category_name, topping_name,
					product_price, topping_price,
					topping_quantity, menu_quantity, menu_stock, menu_subtotal, menu_total));

		} else if (urlDetailsChange.equals(pageName)) {
			// パラメータ取得
			Integer order_id = Integer.parseInt(request.getParameter("order_id"));
			Integer product_id = Integer.parseInt(request.getParameter("product_id"));
			Integer product_price = Integer.parseInt(request.getParameter("product_price"));

			String[] strToppingId = request.getParameterValues("topping_id[]");
			String[] strToppingName = request.getParameterValues("topping_name[]");
			String[] strToppingPrice = request.getParameterValues("topping_price[]");
			String[] strToppingQty = request.getParameterValues("topping_quantity[]");

			for (order_list order : orderList) {
				if (order.getOrder_id() == order_id && order.getProduct_id() == product_id) {
					List<Integer> topping_id = new ArrayList<>();
					List<String> topping_name = new ArrayList<>();
					List<Integer> topping_price = new ArrayList<>();
					List<Integer> topping_quantity = new ArrayList<>();

					int toppingSum = 0;

					if (strToppingId != null) {
						for (int i = 0; i < strToppingId.length; i++) {
							try {
								int qty = Integer.parseInt(strToppingQty[i]);
								int price = Integer.parseInt(strToppingPrice[i]);

								topping_id.add(Integer.parseInt(strToppingId[i]));
								topping_name.add(strToppingName[i]);
								topping_price.add(price);
								topping_quantity.add(qty);

								toppingSum += price * qty;
							} catch (NumberFormatException e) {
								System.out.println("無効な数値: トッピング情報 index=" + i);
							}
						}
					}

					order.setTopping_id(topping_id);
					order.setTopping_name(topping_name);
					order.setTopping_price(topping_price);
					order.setTopping_quantity(topping_quantity);

					// 合計を更新
					order.setMenu_total(product_price + toppingSum);
					order.setMenu_subtotal(product_price + toppingSum);
				}
			}

		}
		session.setAttribute(SESSION_LIST_KEY, orderList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderList.jsp");
		dispatcher.forward(request, response);

	}
}