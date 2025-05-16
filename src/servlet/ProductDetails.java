package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ProductDetails")
public class ProductDetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータの取得
        String productId = request.getParameter("id");
        String productName = request.getParameter("name");
        String productPrice = request.getParameter("price");

        // リクエスト属性に設定
        request.setAttribute("productId", productId);
        request.setAttribute("productName", productName);
        request.setAttribute("productPrice", productPrice);

        // JSPへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("ProductDetails.jsp");
        dispatcher.forward(request, response);
    }
}

