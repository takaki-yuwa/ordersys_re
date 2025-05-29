package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Accounting")
public class Accounting extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータの取得
        String strTableNo = request.getParameter("tableNo");
        // JSPへフォワード
        //request.setAttribute("orderHistory", OrderDetailsList);
        //quest.getRequestDispatcher("/WEB-INF/JSP/Accounting.jsp").forward(request, response);
        request.getRequestDispatcher("/WebContent/Accounting.html").forward(request, response);
    }
}
