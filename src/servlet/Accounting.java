package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.AccountingDAO;

@WebServlet("/Accounting")
public class Accounting extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // POSTリクエストを処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータの取得
        String strTableNo = request.getParameter("tableNo"); // テーブル番号
        String strTotalPrice = request.getParameter("totalPrice"); // 総額

        // 総額を整数に変換
        int totalPrice = Integer.parseInt(strTotalPrice);

        // AccountingDAOのインスタンスを作成
        AccountingDAO accountingDAO = new AccountingDAO();

        // 会計情報を挿入し、関連するorder_detailsを処理
        accountingDAO.processAccountingData(totalPrice);

        // accounting_list オブジェクトを作成
        accounting_list accountingList = new accounting_list(strTableNo, strTotalPrice);

        // JSPへフォワード
        request.setAttribute("accountingList", accountingList);
        request.getRequestDispatcher("/WEB-INF/JSP/Accounting.jsp").forward(request, response);
    }
}
