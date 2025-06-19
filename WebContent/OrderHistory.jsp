<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="servlet.order_details_list" %>
<%@ page import="servlet.multiple_topping_list" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
<title>注文履歴画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/Popup.css">
<link rel="stylesheet" href="CSS/OrderDetails.css">
<link rel="icon" href="/favicon.ico" type="image/x-icon">

<!--.jsの呼び出し-->
<script src="JavaScript/Popup.js" defer></script>
</head>
<body>
<!--ヘッダー(店の名前)-->
<header class="header-storename">
	<div class="header-image-wrapper">
	  <img src="Image/木目3.jpg" alt="背景" class="header-background-image">
      <img src="Image/biglogo.png" alt="店の名前" class="header-image">
    </div>
</header>
<main>
	<!--注文履歴を取得-->
	    <%
	        List<order_details_list> orderDetailsList = (List<order_details_list>)request.getAttribute("orderHistory");
	    %>
	    
    <div class="menu">
        <%
            int iTotalQuantity = 0;
            int iTotalPrice = 0;
            int iTableNo = 0;
            ArrayList<Integer> aiOrderDetailsId = new ArrayList<Integer>();
            if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
                boolean bool = true;
                for (order_details_list p : orderDetailsList) {
                    // 数量の加算
                    iTotalQuantity += p.getproduct_quantity();
                    // 金額の加算
                    iTotalPrice += p.getorder_price();
                    if(bool == true)
                    {
                        bool = false;
        %>
                        <div class="order-details-white">
        <%
                    }
                    else
                    {
                        bool = true;
        %>
                        <div class="order-details-gray">
        <%
                    }
        %>
                            <div class="break-word bold-text"><%= p.getProduct_name() %></div>
        <%
                    for (multiple_topping_list m : p.getMultipleToppingList()) {
        %>
                            <div> ・<%= m.getTopping_name() %>： <%= m.getTopping_quantity() %>個</div>
        <%
                    }
        %>
                            <div></div>
                            <div> 数量：<%= p.getproduct_quantity() %>個</div>
                            <div> <%= p.getorder_price() %>円</div>
                        </div>
        <%
                    iTableNo = p.gettable_number();
                    aiOrderDetailsId.add(p.getorder_details_id());
                }
        %>
                <div class="order-total">
                    <div><%= iTotalQuantity%>点</div>
                    <div><%= iTotalPrice%>円(税込)</div>
                </div>
        <%
            }else{
        %>
                <div class="break-word bold-text"> 注文履歴なし</div>
        <%
            }
        %>
<!--ポップアップの背景-->
<div class="popup-overlay" id="popup-overlay"></div>
<!--ポップアップの内容-->
<div class="popup-content" id="popup-content">
	<p>お会計に進みます。</p>
	<p>よろしいですか？</p>
	<button class="popup-close" id="close-popup">いいえ</button>
    <form action="Accounting" method="post">
	    <button class="popup-proceed" id="confirm-button">
            <input type="hidden" name="tableNo" value="<%= iTableNo %>">
            <input type="hidden" name="orderDetailsId" value="<%= aiOrderDetailsId %>">
            <input type="hidden" name="totalPrice" value="<%= iTotalPrice %>">
            はい
        </button>
	</form>
</div>
</main>
<footer class="footer-buttons">
	<div class="table-number">${sessionScope.tableNumber}卓</div>
	<div class="footer-wrapper">
		<!--ボタン-->
		<!--会計確認ポップアップ表示-->
        <%
            if (orderDetailsList != null && !orderDetailsList.isEmpty())
            {
        %>
                <button class="fixed-right-button" id="open-popup">
                    <img src="Image/history.png" alt="会計のボタン">
                    お会計
                </button>
        <%
            }
        %>
		<!--メニューへ遷移-->
		<a href="OrderSystem">
		<button class="fixed-left-button">
			<img src="Image/menu.png" alt="メニューのボタン">
			メニュー
		</button>
		</a>
	</div>
</footer>
</body>
</html>