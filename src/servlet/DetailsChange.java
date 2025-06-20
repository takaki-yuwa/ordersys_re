package servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ToppingDAO;

@WebServlet("/DetailsChange")
public class DetailsChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		//パラメータを取得
		String o_id = request.getParameter("order_id");
		String p_id = request.getParameter("product_id");
		String product_name = request.getParameter("product_name");
		String p_price = request.getParameter("product_price");
		String category_name = request.getParameter("category_name");
		String p_subtotal=request.getParameter("product_subtotal");
		String[] t_o_id=request.getParameterValues("topping_order_id[]");
		String[] t_id = request.getParameterValues("topping_id[]");
		String[] topping_name = request.getParameterValues("topping_name[]");
		String[] t_price = request.getParameterValues("topping_price[]");
		String[] t_quantity = request.getParameterValues("topping_quantity[]");

		int order_id = 0;
		int product_id = 0;
		int product_price = 0;
		int product_subtotal=0;
		int[] topping_order_id;
		int[] topping_id;
		int[] topping_price;
		int[] topping_quantity;

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
		//int型[]への変換処理
		//nullチェックを行い、nullの場合はからの配列を代入
//		topping_order_id = (t_o_id != null) ? Arrays.stream(t_o_id).mapToInt(Integer::parseInt).toArray() : new int[0];
		topping_id = (t_id != null) ? Arrays.stream(t_id).mapToInt(Integer::parseInt).toArray() : new int[0];
		topping_price = (t_price != null) ? Arrays.stream(t_price).mapToInt(Integer::parseInt).toArray() : new int[0];
		topping_quantity = Optional.ofNullable(t_quantity).filter(arr -> arr.length > 0).map(arr -> Arrays.stream(arr).mapToInt(Integer::parseInt).toArray()).orElse(new int[]{0});

		//詳細変更リストオブジェクトの作成
		details_change_list changeList = new details_change_list(order_id, product_id, product_name, product_price,
				category_name, product_subtotal, topping_id, topping_name, topping_price, topping_quantity);
		
	//詳細変更リストオブジェクトの作成
//					details_change_list changeList = new details_change_list(order_id, product_id, product_name, product_price,
//						category_name, product_subtotal, topping_order_id, topping_id, topping_name, topping_price, topping_quantity);

		//セッションに詳細変更情報をセット
		HttpSession session = request.getSession();
		session.setAttribute("changeList", changeList);
		
		// tableNumberをセッションから取得
        Integer tableNumber = (Integer) session.getAttribute("tableNumber");

        // tableNumberがnullの場合はデフォルト値0を設定（またはエラー処理を行う）
        if (tableNumber == null) {
        	request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
        }

		// トッピング情報の取得
		ToppingDAO dao = new ToppingDAO();
		List<topping_list> toppingList = dao.getToppingNames(product_id);

		// トッピング情報をリクエストにセット
		request.setAttribute("topping_list", toppingList);
		
		// JSPに転送
		request.getRequestDispatcher("/ProductDetailsAdd.jsp").forward(request, response);
	}
}
