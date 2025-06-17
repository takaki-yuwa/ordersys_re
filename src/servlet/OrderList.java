package servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
	private int tableNo = 3;

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// seccionからorderList取得
		HttpSession session = request.getSession();
		List<order_list> orderList = (List<order_list>) session.getAttribute(SESSION_LIST_KEY);
		if (null == orderList) {
			orderList = new ArrayList<>();
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
		if (urlDetailsAdd.equals(pageName)) {
			// リストの初期化
			//商品詳細テーブルから取得するもの
			//主キー(注文ID):order_id
			//外部キー(商品ID):product_id
			//外部キー(商品名):product_name
			//外部キー(カテゴリー名):category_name
			//外部キー(商品価格):product_price
			//外部キー(商品在庫):product_stock

			//複数トッピングテーブルから取得するもの
			//主キー(複数トッピングID):multiple_toppings_id
			//トッピング個数:topping_quantity
			//外部キー(トッピングID):topping_id
			//外部キー(トッピング名):topping_name
			//外部キー(トッピング価格):topping_price
			//外部キー(トッピング在庫):topping_stock
			Integer maxOrderId=orderList.stream().mapToInt(order -> order.getOrder_id()).max().orElse(0);
			Integer order_id = maxOrderId+1;
			List<Integer> topping_id = new ArrayList<Integer>();
			String product_name = request.getParameter("product_name");
			String category_name = request.getParameter("category_name");
			Integer product_price = Integer.parseInt(request.getParameter("product_price"));
			List<Integer> topping_price;
			Integer menu_quantity = 1;
			Integer menu_stock = 20;
			Integer menu_subtotal = 0;
			int menu_total = 0;

			// 商品詳細画面からの取得
			// 商品ID
			String strProductId = request.getParameter("product_id");
			Integer iProductId = 0;
			// idの変換
			if (strProductId != null && !strProductId.isEmpty()) {
				try {
					iProductId = Integer.parseInt(strProductId);
				} catch (NumberFormatException e) {
					System.out.println("無効な数値: 商品ID=" + strProductId);
				}
			}
			// 卓番
			String strTableNo = request.getParameter("tableNo");
			// idの変換
			if (strTableNo != null && !strTableNo.isEmpty()) {
				try {
					tableNo = Integer.parseInt(strTableNo);
				} catch (NumberFormatException e) {
					System.out.println("無効な数値: 卓番=" + strTableNo);
				}
			}
			// トッピングID
			String[] strToppingId = request.getParameterValues("topping");
			if (strToppingId != null) {
				topping_price = new ArrayList<Integer>(Arrays.asList(110, 110, 110, 110, 110, 110));
				for (int i = 0; i < strToppingId.length; i++) {
					try {
						topping_id.add(Integer.parseInt(strToppingId[i]));
					} catch (NumberFormatException e) {
						System.out.println("無効な数値: トッピング数=" + strToppingId[i]);
					}
				}
			} else {
				topping_price = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0));
				topping_id.add(0);
			}

			// トッピング名
			String[] strToppingName = request.getParameterValues("topping_name");
			List<String> topping_name = (strToppingName != null) ? new ArrayList<String>(Arrays.asList(strToppingName))
					: new ArrayList<String>();
			;

			// トッピング数
			String[] strToppingQuantity = request.getParameterValues("topping_quantity");
			List<Integer> iToppingQuantity = new ArrayList<Integer>();
			if (strToppingQuantity != null) {
				for (int i = 0; i < strToppingQuantity.length; i++) {
					try {
						iToppingQuantity.add(Integer.parseInt(strToppingQuantity[i]));
					} catch (NumberFormatException e) {
						System.out.println("無効な数値: トッピング数=" + strToppingQuantity[i]);
					}
				}
			} else {
				iToppingQuantity.add(0);
			}

			orderList.add(new order_list(order_id, iProductId, topping_id, product_name, category_name, topping_name,
					product_price, topping_price,
					iToppingQuantity, menu_quantity, menu_stock, menu_subtotal, menu_total));
		} else {
			//			List<Integer> order_id = List.of(1, 2, 3, 4, 5, 6, 7);
			//			List<Integer> product_id=List.of(1, 2, 3, 4, 5, 6, 7);
			//			List<Integer> topping_id=List.of(1, 2, 3, 4, 5, 6);
			//			List<Integer> topping_order_id = List.of(1, 1, 1, 1, 1, 1);
			//			List<String> product_name = List.of("納豆お好み焼き", "納豆もんじゃ焼き", "ウィンナー", "山芋磯辺揚げ", "ラムネ", "生ビール",
			//					"JINRO");
			//			List<String> category_name=List.of("お好み焼き","もんじゃ焼き","鉄板焼き","サイドメニュー","ソフトドリンク","お酒","ボトル");
			//			List<String> topping_name = List.of("コーン", "カレー", "チーズ", "もち", "ツナ", "ベビースター");
			//			List<Integer> product_price = List.of(660, 660, 500, 420, 280, 600, 2500);
			//			List<Integer> topping_price = List.of(110, 110, 110, 110, 110, 110);
			//			List<Integer> topping_quantity = List.of(1, 2, 3, 4, 5, 6);
			//			List<Integer> menu_quantity = List.of(1, 1, 1, 1, 1, 1, 1);
			//			List<Integer> menu_stock = List.of(10, 20, 21, 22, 23, 24, 25);
			//			List<Integer> menu_subtotal = List.of(0, 0, 0, 0, 0, 0, 0);
			//			int menu_total = 0;
			//			orderList = new order_list(order_id, product_id, topping_id,  product_name, category_name, topping_name, product_price, topping_price,
			//					topping_quantity, menu_quantity, menu_stock, menu_subtotal, menu_total);
		}
		session.setAttribute(SESSION_LIST_KEY, orderList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderList.jsp");
		dispatcher.forward(request, response);

	}
}