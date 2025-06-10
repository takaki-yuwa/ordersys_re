package servlet;

import java.io.IOException;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		List<Integer> order_id = List.of(1, 2, 3, 4, 5, 6, 7);
		List<Integer> product_id=List.of(1, 2, 3, 4, 5, 6, 7);
		List<Integer> topping_id=List.of(1, 2, 3, 4, 5, 6);
		List<String> product_name = List.of("納豆お好み焼き", "納豆もんじゃ焼き", "ウィンナー", "山芋磯辺揚げ", "ラムネ", "生ビール",
				"JINRO");
		List<String> category_name=List.of("お好み焼き","もんじゃ焼き","鉄板焼き","サイドメニュー","ソフトドリンク","お酒","ボトル");
		List<String> topping_name = List.of("コーン", "カレー", "チーズ", "もち", "ツナ", "ベビースター");
		List<Integer> product_price = List.of(660, 660, 500, 420, 280, 600, 2500);
		List<Integer> topping_price = List.of(110, 110, 110, 110, 110, 110);
		List<Integer> topping_quantity = List.of(1, 2, 3, 4, 5, 6);
		List<Integer> menu_quantity = List.of(1, 1, 1, 1, 1, 1, 1);
		List<Integer> menu_stock = List.of(10, 20, 21, 22, 23, 24, 25);
		List<Integer> menu_subtotal = List.of(0, 0, 0, 0, 0, 0, 0);
		int menu_total = 0;

		order_list orderList = new order_list(order_id, product_id, topping_id, product_name, category_name, topping_name, product_price, topping_price,
				topping_quantity, menu_quantity, menu_stock, menu_subtotal, menu_total);

		HttpSession session = request.getSession();

		session.setAttribute("orderList", orderList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/JSP/OrderList.jsp");
		dispatcher.forward(request, response);

	}
}