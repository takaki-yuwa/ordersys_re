package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.OrderDetailsDAO;

@WebServlet("/OrderHistory")
public class OrderHistory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	  // キャッシュ制御ヘッダーを設定
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
        response.setHeader("Pragma", "no-cache"); // HTTP/1.0
        response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

        // セッションを取得
        HttpSession session = request.getSession();

        // tableNumberをセッションから取得
        Integer tableNumber = (Integer) session.getAttribute("tableNumber");

        // tableNumberがnullの場合はデフォルト値0を設定（またはエラー処理を行う）
        if (tableNumber == null) {
        	request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
        }

        // パラメータの取得
        int price = 0;  // 注文金額の初期化

        // 注文履歴を格納するリスト
        List<order_details_list> OrderDetailsList = new ArrayList<>();

        // OrderDetailsDAO インスタンスの生成
        OrderDetailsDAO dao = new OrderDetailsDAO();

        // 注文金額の取得
        String[] strOrderPrice = request.getParameterValues("orderPrice");

        // 注文金額の変換と履歴の取得
        if (strOrderPrice != null && strOrderPrice.length > 0) {
            for (int i = 0; i < strOrderPrice.length; i++) {
                try {
                    price = Integer.parseInt(strOrderPrice[i]);  // 注文金額を整数に変換
                    List<order_details_list> orderHistory = dao.findOrderHistory(tableNumber, price);  // 注文履歴を取得

                    if (orderHistory != null && !orderHistory.isEmpty()) {
                        OrderDetailsList.addAll(orderHistory);  // 注文履歴をリストに追加
                    } else {
                        System.out.println("卓番 " + tableNumber + " と注文金額 " + price + " の注文履歴は見つかりませんでした。");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("無効な数値: 注文金額=" + strOrderPrice[i]);  // 無効な値の場合エラーメッセージを出力
                }
            }
        }

        // 注文履歴がない場合の処理
        if (OrderDetailsList.isEmpty()) {
            System.out.println("注文履歴が見つかりませんでした。");
        }

        // JSPへフォワード
        request.setAttribute("orderHistory", OrderDetailsList);  // 注文履歴をリクエストにセット
        request.getRequestDispatcher("/OrderHistory.jsp").forward(request, response);  // OrderHistory.jspに遷移
    }
}
