<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
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
<link rel="stylesheet" href="CSS/Popup.css">
<link rel="icon" href="data:," />

<script src="JavaScript/OrderList.js"></script>
</head>
<%
	boolean menu_flag=true;	//注文リスト表示フラグ
%>
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
				<c:forEach var="order_id" items="${sessionScope.orderList.order_id}" varStatus="product">
					<c:set var="product_id" value="${sessionScope.orderList.product_id[product.index]}" />
					<c:set var="product_name" value="${sessionScope.orderList.product_name[product.index]}" />
					<c:set var="category_name" value="${sessionScope.orderList.category_name[product.index]}" />
					<c:set var="product_price" value="${sessionScope.orderList.product_price[product.index]}" />
					<c:set var="menu_quantity" value="${sessionScope.orderList.menu_quantity[product.index]}" />
					<c:set var="menu_stock" value="${sessionScope.orderList.menu_stock[product.index]}" />
					<c:set var="menu_subtotal" value="${sessionScope.orderList.menu_subtotal[product.index]}" />
					<c:set var="menu_subtotal" value="${product_price * menu_quantity}" />
					<li>
						<div class="order-item">
							<div class="product-name">${product_name}</div>
							<div class="product-price">${product_price}円</div>
						</div> 
								<c:forEach var="topping_id" items="${sessionScope.orderList.topping_id}" varStatus="topping">
									<c:set var="topping_order_id" value="${sessionScope.orderList.topping_order_id[topping.index]}"/>
									<%--order_idと複数トッピングのtopping_order_idが同じである場合 --%>
									<c:if test="${order_id == topping_order_id}">
										<c:set var="topping_name" value="${sessionScope.orderList.topping_name[topping.index]}" />
										<c:set var="topping_price" value="${sessionScope.orderList.topping_price[topping.index]}" />
										<c:set var="topping_quantity" value="${sessionScope.orderList.topping_quantity[topping.index]}" />
										<div class="order-item">
											<div class="topping-name">・${topping_name}✕${topping_quantity}</div>
											<div class="topping-price">${topping_price * topping_quantity}円</div>
										</div>
										<c:set var="menu_subtotal" value="${menu_subtotal+(topping_price * topping_quantity)}" />
									</c:if>
								</c:forEach>
						<div class="order-item">
							<div class="subtotal-price">
								小計: <span id="subtotal-${order_id}">${menu_subtotal}</span>円
							</div>
						</div> <c:set var="total" value="${total+menu_subtotal}" /> 
						<!-- ボタンを横並びに配置 -->
						<div class="order-item buttons-container">
							<!-- 変更ボタン -->
							<form action="DetailsChange" method="post">
								<input type="hidden" name="from" value="OrderList.jsp">
								<input type="hidden" name="order_id" value="${order_id}">
								<input type="hidden" name="product_id" value="${product_id}">
								<input type="hidden" name="product_name" value="${product_name}">
								<input type="hidden" name="product_price" value="${product_price}"> 
								<input type="hidden" name="category_name" value="${category_name}"> 
								<input type="hidden" name="product_subtotal" value="${menu_subtotal}">
									<c:forEach var="topping_id" items="${sessionScope.orderList.topping_id}" varStatus="topping">
										<c:set var="topping_order_id" value="${sessionScope.orderList.topping_order_id[topping.index]}" />
										<c:set var="topping_name" value="${sessionScope.orderList.topping_name[topping.index]}" />
										<c:set var="topping_price" value="${sessionScope.orderList.topping_price[topping.index]}" />
										<c:set var="topping_quantity" value="${sessionScope.orderList.topping_quantity[topping.index]}" />
										<%--order_idと複数トッピングのtopping_order_idが同じである場合 --%>
										<c:if test="${order_id == topping_order_id}">
											<input type="hidden" name="topping_id[]" value="${topping_id}">
											<input type="hidden" name="topping_order_id[]" value="${topping_order_id}">
											<input type="hidden" name="topping_name[]" value="${topping_name}">
											<input type="hidden" name="topping_price[]" value="${topping_price}">
											<input type="hidden" name="topping_quantity[]" value="${topping_quantity}">
										</c:if>
										<%--order_idと複数トッピングのtopping_order_idが同じでない場合 --%>
										<c:if test="${order_id != topping_order_id}">
											<input type="hidden" name="topping_id[]" value="${topping_id}">
											<input type="hidden" name="topping_order_id[]" value="0">
											<input type="hidden" name="topping_name[]" value="${topping_name}">
											<input type="hidden" name="topping_price[]" value="0">
											<input type="hidden" name="topping_quantity[]" value="0">
										</c:if>
									</c:forEach>
								<button class="change-btn">変更</button>
							</form>
							<!-- 増減ボタンを追加 -->
							<div class="quantity-buttons">
								<c:choose>
									<%-- 数量が1の場合、ゴミ箱ボタンを表示 --%>
									<c:when test="${menu_quantity == 1}">
										<button type="submit" name="quantity" value="${menu_quantity - 1}" class="decrease-btn" id="decrement-${order_id}">
											<img class="dustbox-img" src="Image/dustbox.png" alt="ゴミ箱ボタン">
										</button>
									</c:when>
									<%-- 数量が1以外の場合、マイナスボタンを表示 --%>
									<c:otherwise>
										<button type="submit" name="quantity" value="${menu_quantity - 1}" class="decrease-btn" id="decrement-${order_id}">−</button>
									</c:otherwise>
								</c:choose>
								<!-- 数量を表示する要素、変数にバインド -->
								<span class="quantity" id="counter-${order_id}">${menu_quantity}</span>
								<button type="button" name="quantity" value="${menu_quantity + 1}" class="increase-btn" id="increment-${order_id}">+</button>
							</div>
							<!--ポップアップの背景-->
							<div class="popup-overlay" id="popup-overlay"></div>
							<!--ポップアップの内容-->
							<div class="popup-content" id="popup-content">
								<p>この商品を削除します</p>
								<p>よろしいですか？</p>
								<button class="popup-close" id="close-popup">いいえ</button>
								<button class="popup-proceed" id="confirm-button">はい
								</button>
							</div>
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
					<c:forEach var="order" items="${sessionScope.orderList.order_id}" varStatus="order">
						<input type="hidden" id="menu_id" name="order_id[]" value="${order_id}">
						<input type="hidden" id="countField" name="product_quantity[]" value="${menu_quantity}">
						<input type="hidden" id="priceField" name="order_price[]" value="${menu_subtotal}">
					</c:forEach>
					<input type="hidden" name="tableNumber" value="3">
					<button class="fixed-right-button">
						<img src="Image/Vector.png" alt="注文のボタン">注文する
					</button>
				</form>
			<!--メニューへ遷移-->
			<a href="OrderSystem">
				<button class="fixed-left-button">
					<img src="Image/menu.png" alt="メニューのボタン"> メニュー
				</button>
			</a>
		</div>
	</footer>
	<footer class="footer-subtotal">
		<div class="footer-subtotal-wrapper">
			<div class="subtotal-text" id="total">合計:${total}円(税込)</div>
		</div>
	</footer>
</body>
<!-- 小計・合計の更新 -->
<c:forEach var="order_id" items="${sessionScope.orderList.order_id}" varStatus="product">
	<c:set var="product_price" value="${sessionScope.orderList.product_price[product.index]}" />
	<c:set var="menu_quantity" value="${sessionScope.orderList.menu_quantity[product.index]}" />
	<c:set var="menu_stock" value="${sessionScope.orderList.menu_stock[product.index]}" />
	<c:set var="menu_subtotal" value="${sessionScope.orderList.menu_subtotal[product.index]}" />
	<c:set var="menu_subtotal" value="${product_price * menu_quantity}" />
	<c:forEach var="topping_id" items="${sessionScope.orderList.topping_id}" varStatus="topping">
		<c:set var="topping_order_id" value="${sessionScope.orderList.topping_order_id[topping.index]}" />
		<%--order_idと複数トッピングのtopping_order_idが同じである場合 --%>
		<c:if test="${order_id==topping_order_id}">
			<c:set var="topping_price" value="${sessionScope.orderList.topping_price[topping.index]}" />
			<c:set var="topping_quantity" value="${sessionScope.orderList.topping_quantity[topping.index]}" />
			<c:set var="menu_subtotal" value="${menu_subtotal+(topping_price * topping_quantity)}" />
		</c:if>
		<!-- JavaScriptで埋め込む -->
		<script>updateOrderState(${order_id}, ${menu_quantity},${menu_stock},${menu_subtotal});</script>
	</c:forEach>
</c:forEach>
</html>