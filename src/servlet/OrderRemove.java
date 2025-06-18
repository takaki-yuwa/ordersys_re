package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderRemove")
public class OrderRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SESSION_LIST_KEY = "orderList";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<order_list> orderList = (List<order_list>) session.getAttribute(SESSION_LIST_KEY);
		Integer order_id=Integer.valueOf(request.getParameter("order_id"));
		//削除対象のデバッグ確認
		System.out.println("削除対象 order_id："+order_id);
		if (null == orderList) {
			orderList = new ArrayList<>();
		}
		//セッションから対象のorder_idを削除する
		if(orderList!=null) {
			
			Iterator<order_list> iterator = orderList.iterator();
			while (iterator.hasNext()) {
			    order_list order = iterator.next();
			    if (order.getOrder_id().equals(order_id)) {
			        iterator.remove();
			        break;
			    }
			}
			
			session.setAttribute("orderList", orderList);
		}
		
		response.sendRedirect("OrderList.jsp");
	}
}