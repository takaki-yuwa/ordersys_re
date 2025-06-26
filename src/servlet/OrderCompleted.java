package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import dao.DBUtil;
import dao.OrderCompletedDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderCompleted")
public class OrderCompleted extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // キャッシュ制御ヘッダーを設定
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
        response.setHeader("Pragma", "no-cache"); // HTTP/1.0
        response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

        // パラメータの取得
        String[] order_ids = request.getParameterValues("order_id[]");
        String[] product_id = request.getParameterValues("product_id[]");
        String[] product_quantity = request.getParameterValues("product_quantity[]");
        String[][] topping_ids = new String[order_ids.length][];
        String[][] topping_quantities = new String[order_ids.length][];
        String[] order_price = request.getParameterValues("order_price[]");
        String[] toppingId = null;
        String[] toppingQuantity = null;
        for(int i=0;i<order_ids.length;i++) {
        	topping_ids[i] = request.getParameterValues("topping_id_"+i);
        	String[] topping_quantity = request.getParameterValues("topping_quantity_"+i);
        	toppingId = request.getParameterValues("topping_id_"+i);
            toppingQuantity = request.getParameterValues("topping_quantity_"+i);
        	topping_quantities[i]=new String[topping_quantity.length];
        	for(int j=0;j<topping_quantity.length;j++) {
        		topping_quantities[i][j]=topping_quantity[j];
        	}
        }
        

        // パラメータの値の確認
        System.out.println("product_id: " + Arrays.toString(product_id));
        System.out.println("product_quantity: " + Arrays.toString(product_quantity));
        System.out.println("order_price: " + Arrays.toString(order_price));
        System.out.println("topping_id: " + Arrays.toString(toppingId));
        System.out.println("topping_quantity: " + Arrays.toString(toppingQuantity));

        // セッションを取得
        HttpSession session = request.getSession();

        // tableNumberをセッションから取得（Integer型で取得）
        Integer tableNumber = (Integer) session.getAttribute("tableNumber");

        // tableNumberがnullの場合はエラー画面に遷移
        if (tableNumber == null) {
            request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
            return; // return で処理を終了させる
        }

        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            OrderCompletedDAO dao = new OrderCompletedDAO();

            // 1. product_details に product_id を挿入し、order_id を取得
            List<Integer> orderIdList = dao.insertProductDetails(conn, product_id);

            // 2. List<Integer> → String[] に変換
            String[] order_id = orderIdList.stream().map(String::valueOf).toArray(String[]::new);

            // 3. order_details に挿入
            // tableNumberはInteger型なので、intに変換して渡す
            boolean insertSuccess = dao.insertOrderDetails(conn, order_id, product_quantity, order_price, tableNumber.intValue());

            if (insertSuccess) {
                // 商品在庫を更新
                dao.updateProductStock(conn, product_id, product_quantity);
                conn.commit();
                
            } else {
                // 注文詳細の挿入が失敗した場合
                conn.rollback();
                response.getWriter().println("注文詳細の追加に失敗しました。");
            }
            
            // トッピングのデータ挿入
            boolean toppingInsertSuccess = dao.insertTopingDetails(conn, topping_ids, topping_quantities, order_id);
            if (toppingInsertSuccess) {
                // トッピングの挿入が成功した場合
                dao.updateToppingStock(conn, toppingId, toppingQuantity);
                conn.commit();
                response.getWriter().println("すべてのデータが正常に追加されました！");
            } else {
                // トッピング挿入が失敗した場合
                conn.rollback();
                response.getWriter().println("トッピング詳細の追加に失敗しました。");
            }

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            response.getWriter().println("エラーが発生しました: " + e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        request.getSession().removeAttribute("orderList"); // 注文リストのセッションのみ削除
        request.getRequestDispatcher("/OrderCompleted.jsp").forward(request, response);
    }
}
