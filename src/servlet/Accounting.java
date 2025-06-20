package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
        
        // セッションの取得と情報削除
        request.getSession().invalidate(); 
        
        // クライアント側のセッションIDを保持するクッキーを削除
        // クッキーの有効期限を過去に設定し、削除
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0); // クッキーを無効化するための設定（すぐに削除）
        cookie.setPath("/"); // クッキーのパスを設定（すべてのパスに対して有効）
        response.addCookie(cookie);

        
        // JSPへフォワード
        request.setAttribute("accountingList", accountingList);
        request.getRequestDispatcher("/Accounting.jsp").forward(request, response);
    }
}
