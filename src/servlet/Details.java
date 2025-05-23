package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Details")
public class Details extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータの取得
        String p_id = request.getParameter("id");
        String name = request.getParameter("name");
        String p_price = request.getParameter("price");
        String category=request.getParameter("category");
        int id = 0;
        int price = 0;
        // idの変換
        if (p_id != null && !p_id.isEmpty()) {
            try {
                id = Integer.parseInt(p_id);
            } catch (NumberFormatException e) {
                System.out.println("無効な数値: id=" + p_id);
            }
        }

        // priceの変換
        if (p_price != null && !p_price.isEmpty()) {
            try {
                price = Integer.parseInt(p_price);
            } catch (NumberFormatException e) {
                System.out.println("無効な数値: price=" + p_price);
            }
        }
        
        product_list productList=new product_list(id, name, price, category);
        
        HttpSession session=request.getSession();
        
        session.setAttribute("productList", productList);

        // JSPへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/ProductDetails.jsp");
        dispatcher.forward(request, response);
    }
}
