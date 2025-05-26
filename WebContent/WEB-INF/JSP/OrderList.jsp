<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
		<c:forEach var="name" items="${sessionScope.orderList.name}" varStatus="status">
			<c:set var="price" value="${sessionScope.orderList.price[status.index]}"/>
			<li>
				<div class="order-item">
					<div class="product-name">${name}</div><div class="product-price">${price}円</div>
				</div>		
				<c:forEach var="name2" items="${sessionScope.orderList.name2}">
					<c:set var="price2" value="${sessionScope.orderList.price2[status.index]}"/>
					<div class="order-item">
						<div class="topping-name">・${name2}</div><div class="topping-price"> ${price2}円</div>
					</div>
				</c:forEach>
				
			</li>
		</c:forEach>
		<c:forEach var="subtotal" items="${sessionScope.orderList.subtotal}">
					<div class="subtotal">小計:${subtotal}円</div>
		</c:forEach>
	</c:if>
</div>

</main>
<footer class="footer-buttons">
	<div class="table-number">3卓</div>
	<div class="footer-wrapper">
		<!--ボタン-->
		<!--注文完了へ遷移-->
		<a href="OrderCompleted.html">
		<button class="fixed-right-button">
			<img src="Image/Vector.png" alt="注文のボタン">
			注文する
		</button>
		</a>
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
	<div class="footer-subtotal-wrapper">
		<div class="subtotal-text">合計:111,430円(税込)</div>
	</div>
</footer>
</body>
</html>