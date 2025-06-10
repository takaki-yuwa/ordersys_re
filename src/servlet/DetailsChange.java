package servlet;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DetailsChange")
public class DetailsChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//パラメータを取得
		String o_id = request.getParameter("order_id");
		String p_id = request.getParameter("product_id");
		String product_name = request.getParameter("product_name");
		String p_price = request.getParameter("product_price");
		String category_name = request.getParameter("category_name");
		String p_subtotal=request.getParameter("product_subtotal");
		String[] t_id = request.getParameterValues("topping_id[]");
		String[] topping_name = request.getParameterValues("topping_name[]");
		String[] t_price = request.getParameterValues("topping_price[]");
		String[] t_quantity = request.getParameterValues("topping_quantity[]");

		int order_id = 0;
		int product_id = 0;
		int product_price = 0;
		int product_subtotal=0;
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
		topping_id = (t_id != null) ? Arrays.stream(t_id).mapToInt(Integer::parseInt).toArray() : new int[0];
		topping_price = (t_price != null) ? Arrays.stream(t_price).mapToInt(Integer::parseInt).toArray() : new int[0];
		topping_quantity = (t_quantity != null) ? Arrays.stream(t_quantity).mapToInt(Integer::parseInt).toArray() : new int[0];

		//詳細変更リストオブジェクトの作成
		details_change_list changeList = new details_change_list(order_id, product_id, product_name, product_price,
				category_name, product_subtotal, topping_id, topping_name, topping_price, topping_quantity);

		//セッションに詳細変更情報をセット
		HttpSession session = request.getSession();
		session.setAttribute("changeList", changeList);

		// JSPに転送
		request.getRequestDispatcher("/WEB-INF/JSP/ProductDetailsAdd.jsp").forward(request, response);
	}
}
