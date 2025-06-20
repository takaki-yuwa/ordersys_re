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

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// seccionからorderList取得
		HttpSession session = request.getSession();
		List<order_list> orderList = (List<order_list>) session.getAttribute(SESSION_LIST_KEY);
		if (null == orderList) {
			orderList = new ArrayList<>();
		}
		
		// tableNumberをセッションから取得
        Integer tableNumber = (Integer) session.getAttribute("tableNumber");

        // tableNumberがnullの場合はデフォルト値0を設定（またはエラー処理を行う）
        if (tableNumber == null) {
        	request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
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
		String urlDetailsChange= "DetailsChange";
		if (urlDetailsAdd.equals(pageName)) {
			// リストの初期化
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
		
		} else if (urlDetailsChange.equals(pageName)) {
			//パラメータを取得
			String o_id = request.getParameter("order_id");
			String p_id = request.getParameter("product_id");
			String product_name = request.getParameter("product_name");
			String p_price = request.getParameter("product_price");
			String category_name = request.getParameter("category_name");
			String p_subtotal=request.getParameter("product_subtotal");
			String[] t_id = request.getParameterValues("topping_id[]");
			String[] t_name = request.getParameterValues("topping_name[]");
			String[] t_price = request.getParameterValues("topping_price[]");
			String[] t_quantity = request.getParameterValues("topping_quantity[]");
			
			Integer order_id = 0;
			Integer product_id = 0;
			Integer product_price = 0;
			Integer product_subtotal=0;
			
			// 配列 → Listへ変換
			List<Integer> topping_id = new ArrayList<>();
			List<String> topping_name = new ArrayList<>();
			List<Integer> topping_price = new ArrayList<>();
			List<Integer> topping_quantity = new ArrayList<>();
			
			//int型への変換処理
			if (o_id != null && !o_id.isEmpty() && p_id != null && !p_id.isEmpty() && p_price != null
					&& !p_price.isEmpty() && p_subtotal!=null && !p_subtotal.isEmpty()) {
				try {
					order_id = Integer.parseInt(o_id);
					product_id = Integer.parseInt(p_id);
					product_price = Integer.parseInt(p_price);
					product_subtotal=Integer.parseInt(p_subtotal);
				} catch (NumberFormatException e) {
					System.out.println("無効な数値: order_id=" + o_id);
					System.out.println("無効な数値: product_id=" + p_id);
					System.out.println("無効な数値: product_price=" + p_price);

				}
			}
			
			
			// 配列がnullでないことを確認してから処理
			//order_idとproduct_idが一致しているか確認
			for(order_list order : orderList) {
				if(order.getOrder_id()== order_id && order.getProduct_id() == product_id) {
					if (t_id != null && t_name != null && t_price != null && t_quantity != null) {
					    for (String id : t_id) {
					        try {
					        	topping_id.add(Integer.parseInt(id));
					        } catch (NumberFormatException e) {
					            // 必要ならロギングなど
					        }
					    }
					    for (String name : t_name) {
					        topping_name.add(name);
					    }
					    for (String price : t_price) {
					        try {
					        	topping_price.add(Integer.parseInt(price));
					        } catch (NumberFormatException e) {
					            // 必要ならロギングなど
					        }
					    }
					    for (String qty : t_quantity) {
					        try {
					        	topping_quantity.add(Integer.parseInt(qty));
					        } catch (NumberFormatException e) {
					            // 必要ならロギングなど
					        }
					    }
					}
					//トッピングの変更内容をセットする
					order.setTopping_id(topping_id);
					order.setTopping_name(topping_name);
					order.setTopping_price(topping_price);
					order.setTopping_quantity(topping_quantity);
					
					//デバッグ確認
					System.out.println("注文ID："+order_id);
					System.out.println("商品ID："+product_id);
					System.out.println("商品名："+product_name);
					System.out.println("商品価格："+product_price);
					System.out.println("カテゴリー名："+category_name);
					for(int i=0;i<topping_id.size();i++) {
						System.out.println("トッピングID："+topping_id.get(i)+"　名前："+topping_name.get(i)+"　個数："+topping_quantity.get(i));
					}
					
				}
			}
			
		}
		session.setAttribute(SESSION_LIST_KEY, orderList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderList.jsp");
		dispatcher.forward(request, response);

	}
}