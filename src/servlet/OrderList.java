package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderList")
public class OrderList extends HttpServlet {
	private static final long serialVersionUID=1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//パラメータの取得
		String[] name={"お好み焼き","もんじゃ焼き","ドリンク","サイド","メニュー","お酒","ボトル"};
		String[] name2={"コーン","バター","塩"};
		String[] price= {"1001","1002","1003","1004","1005","1006","1007"};
		String[] price2= {"111","112","113"};
		String[] subtotal= {"1111","2222","3333","4444","5555","6666","7777"};

		order_list orderList=new order_list(name,name2,price,price2,subtotal);
		
		HttpSession session=request.getSession();
		
		session.setAttribute("orderList", orderList);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/JSP/OrderList.jsp");
		dispatcher.forward(request, response);
		
	}

}
