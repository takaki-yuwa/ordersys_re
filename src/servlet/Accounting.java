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
        String strTotalPrice = request.getParameter("totalPrice");
        accounting_list Accounting_List = new accounting_list(strTableNo, strTotalPrice);
        
        
        // JSPへフォワード
        request.setAttribute("accountingList", Accounting_List);
        request.getRequestDispatcher("/WEB-INF/JSP/Accounting.jsp").forward(request, response);
        //request.getRequestDispatcher("/Accounting.html").forward(request, response);
    }
}
