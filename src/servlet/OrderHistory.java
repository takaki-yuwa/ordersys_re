package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.OrderDetailsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderHistory")
public class OrderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パラメータの取得
		String strTableNo = request.getParameter("tableNo");
		int tableNo = 0;
		int price = 0;
		// idの変換
		if (strTableNo != null && !strTableNo.isEmpty()) {
			try {
				tableNo = Integer.parseInt(strTableNo);
			} catch (NumberFormatException e) {
				System.out.println("無効な数値: 卓番=" + strTableNo);
			}
		}

		// 卓番に無効な値 = 0 が入っている場合は処理しない
		List<order_details_list> OrderDetailsList = new ArrayList<order_details_list>();
		if (tableNo > 0) {
			OrderDetailsDAO dao = new OrderDetailsDAO();
			String[] strOrderPrice = request.getParameterValues("orderPrice");
			// 注文金額の変換
			if (strOrderPrice.length > 0) {
				for (int i = 0; i < strOrderPrice.length; i++) {
					try {
						price = Integer.parseInt(strOrderPrice[i]);
						OrderDetailsList.addAll(dao.findOrderHistory(tableNo, price));
					} catch (NumberFormatException e) {
						System.out.println("無効な数値: 注文金額=" + strOrderPrice[i]);
					}
				}
			}
		}

		// JSPへフォワード
		request.setAttribute("orderHistory", OrderDetailsList);
		request.getRequestDispatcher("/WEB-INF/JSP/OrderHistory.jsp").forward(request, response);
	}
}
