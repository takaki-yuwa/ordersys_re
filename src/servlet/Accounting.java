package servlet;

import java.io.IOException;

import dao.AccountingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Accounting")
public class Accounting extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// POSTリクエストを処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// セッションを取得
		HttpSession session = request.getSession();

		String tableNumberStr = (String) session.getAttribute("tableNumber");
		int tableNumber = 0;
		if (tableNumberStr != null) {
			try {
				tableNumber = Integer.parseInt(tableNumberStr);
			} catch (NumberFormatException e) {
				// 無効な数値の場合はエラー処理
				System.out.println("無効な tableNumber: " + tableNumberStr);
				request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
				return;
			}
		} else {
			// null の場合はエラー画面へ
			request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
			return;
		}

		// パラメータの取得
		String strTableNo = request.getParameter("tableNo"); // テーブル番号
		String strTotalPrice = request.getParameter("totalPrice"); // 総額

		// 総額を整数に変換
		int totalPrice = Integer.parseInt(strTotalPrice);
		int tableNo = Integer.parseInt(strTableNo);

		// AccountingDAOのインスタンスを作成
		AccountingDAO accountingDAO = new AccountingDAO();

		// 会計情報を挿入し、関連するorder_detailsを処理
		accountingDAO.processAccountingData(totalPrice, tableNo);

		// accounting_list オブジェクトを作成
		accounting_list accountingList = new accounting_list(strTableNo, strTotalPrice);

		// セッションの取得と情報削除
		request.getSession().invalidate();

		// JSPへフォワード
		request.setAttribute("accountingList", accountingList);
		request.getRequestDispatcher("/Accounting.jsp").forward(request, response);
	}
}
