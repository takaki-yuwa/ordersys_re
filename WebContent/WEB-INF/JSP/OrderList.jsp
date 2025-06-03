<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
<title>注文リスト画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/WordWrap.css">
<link rel="stylesheet" href="CSS/OrderList.css">
<link rel="icon" href="data:," />

<!--<script src="JavaScript/OrderList.js"></script>-->
</head>
<body>
<!--ヘッダー(店の名前)-->
<header class="header-storename">
	<div class="header-image-wrapper">
	  <img src="Image/木目3.jpg" alt="背景" class="header-background-image">
      <img src="Image/biglogo.png" alt="店の名前" class="header-image">
    </div>
</header>
<main class="list-main">
<div class="orderlist">
    <c:if test="${not empty sessionScope.orderList}">
    	<c:forEach var="menu_id" items="${sessionScope.orderList.menu_id}" varStatus="status">
        	<c:set var="product_name" value="${sessionScope.orderList.product_name[status.index]}"/>
            <c:set var="product_price" value="${sessionScope.orderList.product_price[status.index]}"/>
            <c:set var="quantity" value="${sessionScope.orderList.menu_quantity[status.index]}"/>
            <c:set var="subtotal" value="${sessionScope.orderList.menu_subtotal[status.index]}"/>
            <c:set var="subtotal" value="${product_price * quantity}" />
            <li>
                <div class="order-item">
                    <div class="product-name">${product_name}</div>
                    <div class="product-price">${product_price}円</div>
                </div>
                <c:forEach var="topping_name" items="${sessionScope.orderList.topping_name}" varStatus="status2">
                    <c:set var="topping_price" value="${sessionScope.orderList.topping_price[status2.index]}"/>
                    <div class="order-item">
                        <div class="topping-name">・${topping_name}</div>
                        <div class="topping-price"> ${topping_price}円</div>
                    </div>
                    <c:set var="subtotal" value="${subtotal+(topping_price * quantity)}"/>
                </c:forEach>
                <div class="order-item">
                    <div class="subtotal-price">小計: 
                        <span id="subtotal">${subtotal}</span>円
                    </div>
                </div>
                <c:set var="total" value="${total+subtotal}"/>
                
                <!-- ボタンを横並びに配置 -->
                <div class="order-item buttons-container">
                	<form action="DetailsChange" method="post">
                		<input type="hidden" name="menu_id" value="${menu_id}">
                		<input type="hidden" name="product_name" value="${product_name}">
                		<input type="hidden" name="product_price" value="${product_price}">
                		<input type="hidden" name="topping_name" value="${topping_name}">
                		<input type="hidden" name="topping_price" value="${topping_price}"> 
                    	<button class="change-btn">変更</button>
                    </form>
                    <!-- 増減ボタンを追加 -->
                    <div class="quantity-buttons">
                        <button type="submit" name="quantity" value="${quantity - 1}" class="decrease-btn">−</button>
                        <!-- 数量を表示する要素、変数にバインド -->
                        <span class="quantity" id="counter">1</span>
                        <button type="button" name="quantity" value="${quantity + 1}" class="increase-btn" id="increment">+</button>
                    </div>
                    <script type="text/javascript" id="test">
                        const state = { count: 1 };

                        const btn = document.getElementById('increment');
                        btn.addEventListener('click', () => {
                          const counter = document.getElementById('counter');
                          const subtotal = document.getElementById('subtotal');
                          counter.innerHTML = ++state.count;
                          subtotal.innerHTML = state.count * ${subtotal};
                        });
                    </script>
                </div>
            </li>
        </c:forEach>
    </c:if>
</div>

</main>
<footer class="footer-buttons">
	<div class="table-number">3卓</div>
	<div class="footer-wrapper">
		<!--ボタン-->
		<!--注文完了へ遷移-->
		<form action="OrderCompleted" method="post">
		<button class="fixed-right-button">
            <input type="hidden" name="tableNo" value="3">
			<img src="Image/Vector.png" alt="注文のボタン">
			注文する
		</button>
		</form>
		<!--メニューへ遷移-->
		<a href="OrderSystem">
		<button class="fixed-left-button">
			<img src="Image/menu.png" alt="メニューのボタン">
			メニュー
		</button>
		</a>
	</div>
</footer>
<footer class="footer-subtotal">
	<c:if test="${not empty sessionScope.orderList}">
	<div class="footer-subtotal-wrapper">
		<div class="subtotal-text">合計:${total}円(税込)</div>
	</div>
	</c:if>
</footer>
</body>
</html>