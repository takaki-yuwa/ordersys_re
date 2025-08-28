<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
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
				<c:forEach var="order_list" items="${sessionScope.orderList}" varStatus="order_status">
					<c:set var="order_id" value="${order_list.order_id}" />
					<c:set var="product_id" value="${order_list.product_id}" />
					<c:set var="product_name" value="${order_list.product_name}" />
					<c:set var="category_name" value="${order_list.category_name}" />
					<c:set var="product_price" value="${order_list.product_price}" />
					<c:set var="menu_quantity" value="${order_list.menu_quantity}" />
					<c:set var="menu_stock" value="${order_list.menu_stock}" />
					<c:set var="menu_subtotal" value="${order_list.menu_subtotal}" />
					<c:set var="menu_subtotal" value="${product_price * menu_quantity}" />
					<li>
						<div class="order-item">
							<div class="product-name"><c:out value="${product_name}" /></div>
							<div class="product-price"><c:out value="${product_price}" />円</div>
						</div> 
						<c:forEach var="topping_id" items="${order_list.topping_id}" varStatus="topping">
							<c:set var="topping_name" value="${order_list.topping_name[topping.index]}" />
							<c:set var="topping_price" value="${order_list.topping_price[topping.index]}" />
							<c:set var="topping_quantity" value="${order_list.topping_quantity[topping.index]}" />
							<c:if test="${topping_quantity>=1 }">
								<%--トッピング表示--%>
								<div class="order-item">
									<div class="topping-name">・<c:out value="${topping_name}" />✕<span><c:out value="${topping_quantity}" /></span></div>
									<div class="topping-price"><c:out value="${topping_price * topping_quantity}" />円</div>
								</div>
								<c:set var="menu_subtotal" value="${menu_subtotal+(topping_price * topping_quantity)}" />
							</c:if>
						</c:forEach>
						<div class="order-item">
							<div class="subtotal-price">
								小計: <span id="subtotal-<c:out value='${order_id}' />" data-price="<c:out value='${menu_subtotal}' />"><c:out value="${menu_subtotal}" /></span>円
							</div>
						</div> <c:set var="total" value="${total+menu_subtotal}" /> <!-- ボタンを横並びに配置 -->
						<div class="order-item buttons-container">
							<!-- 変更ボタン -->
							<form action="DetailsChange" method="post">
								<input type="hidden" name="from" value="OrderList.jsp">
								<input type="hidden" name="order_id" value="<c:out value='${order_id}' />">
								<input type="hidden" name="product_id" value="<c:out value='${product_id}' />">
								<input type="hidden" name="product_name" value="<c:out value='${product_name}' />">
								<input type="hidden" name="product_price" value="<c:out value='${product_price}' />"> 
								<input type="hidden" name="category_name" value="<c:out value='${category_name}' />"> 
								<input type="hidden" name="product_subtotal" value="<c:out value='${menu_subtotal}' />">
								<c:forEach var="topping_id" items="${order_list.topping_id}" varStatus="topping">
									<c:set var="topping_name" value="${order_list.topping_name[topping.index]}" />
									<c:set var="topping_price" value="${order_list.topping_price[topping.index]}" />
									<c:set var="topping_quantity" value="${order_list.topping_quantity[topping.index]}" />
									<input type="hidden" name="topping_id[]" value="<c:out value='${topping_id}' />">
									<input type="hidden" name="topping_order_id[]" value="<c:out value='${topping_order_id}' />">
									<input type="hidden" name="topping_name[]" value="<c:out value='${topping_name}' />">
									<input type="hidden" name="topping_price[]" value="<c:out value='${topping_price}' />">
									<input type="hidden" name="topping_quantity[]" value="<c:out value='${topping_quantity}' />">
								</c:forEach>
								<button class="change-btn">変更</button>
							</form>
							<!-- 増減ボタンを追加 -->
							<div class="quantity-buttons">
								<button type="button" name="quantity" value="<c:out value='${menu_quantity - 1}' />" class="decrease-btn" id="decrement-<c:out value='${order_id}' />"></button>
								<!-- 数量を表示する要素、変数にバインド -->
								<span class="quantity" id="counter-<c:out value='${order_id}' />"><c:out value="${menu_quantity}" /></span>
								<button type="button" name="quantity" value="<c:out value='${menu_quantity + 1}' />" class="increase-btn" id="increment-<c:out value='${order_id}' />">+</button>
							</div>
						</div>
					</li>
				</c:forEach>
			</c:if>
			<c:if test="${empty sessionScope.orderList}">
				<div class="break-word bold-text">カートが空です</div>
			</c:if>
		</div>
	</main>
	<!--ポップアップの背景-->
	<div class="popup-overlay" id="popup-overlay"></div>
	<!--ポップアップの内容-->
	<div class="popup-content" id="popup-content">
		<p>この商品を削除します</p>
		<p>よろしいですか？</p>
		<button class="popup-close" id="close-popup">いいえ</button>
		<!-- 商品を削除 -->
		<form action="OrderRemove" method="post">
			<input type="hidden" name="order_id" id="popup-order-id" />
			<button type="submit" class="popup-proceed" id="confirm-button">は　い</button>
		</form>
	</div>
	<footer class="footer-buttons">
		<div class="table-number"><c:out value="${sessionScope.tableNumber}" />卓</div>
		<div class="footer-wrapper">
			<!--ボタン-->
			<!--注文完了へ遷移-->
			<form action="OrderCompleted" method="post">
				<c:forEach var="order_list" items="${sessionScope.orderList}" varStatus="product">
					<input type="hidden" id="menu_id" name="order_id[]" value="<c:out value='${order_list.order_id}' />">
					<input type="hidden" name="product_id[]" value="<c:out value='${order_list.product_id}' />">
					<input type="hidden" id="countField-<c:out value='${order_list.order_id}' />" name="product_quantity[]" value="<c:out value='${order_list.menu_quantity}' />">
					<input type="hidden" id="priceField-<c:out value='${order_list.order_id}' />" name="order_price[]" value="<c:out value='${order_list.menu_subtotal}' />">
					<!-- トッピングがある場合にループ -->
					<c:forEach var="topping_id" items="${order_list.topping_id}" varStatus="topping">
						<input type="hidden" name="topping_id_<c:out value='${product.index}' />" value="<c:out value='${topping_id}' />">
						<input type="hidden" id="toppingcountField-<c:out value='${order_list.order_id}' />-<c:out value='${topping_id}' />" name="topping_quantity_<c:out value='${product.index}' />" value="<c:out value='${order_list.topping_quantity[topping.index]}' />">
					</c:forEach>
				</c:forEach>
				<input type="hidden" name="tableNumber" value="<c:out value='${sessionScope.tableNumber}' />">
				<c:if test="${not empty sessionScope.orderList}">
					<button class="fixed-right-button">
						<img src="Image/Vector.png" alt="注文のボタン">注文する
					</button>
				</c:if>
			</form>
			<!--メニューへ遷移-->
			<a href="OrderSystem">
				<button class="fixed-left-button">
					<img src="Image/menu.png" alt="メニューのボタン"> メニュー
				</button>
			</a>
		</div>
	</footer>
	<c:if test="${not empty sessionScope.orderList}">
		<footer class="footer-subtotal">
			<div class="footer-subtotal-wrapper">
				<div class="subtotal-text" id="total">合計:<c:out value="${total}" />円(税込)</div>
			</div>
		</footer>
	</c:if>
</body>
<!-- 小計・合計の更新 -->
<c:forEach var="order_list" items="${sessionScope.orderList}" varStatus="product">
	<c:set var="order_id" value="${order_list.order_id}" />
	<c:set var="product_price" value="${order_list.product_price}" />
	<c:set var="menu_quantity" value="${order_list.menu_quantity}" />
	<c:set var="menu_stock" value="${order_list.menu_stock}" />
	<c:set var="menu_subtotal" value="${order_list.menu_subtotal}" />
	<c:set var="menu_subtotal" value="${product_price * menu_quantity}" />
	<c:forEach var="topping_id" items="${order_list.topping_id}" varStatus="topping">
		<c:set var="topping_price" value="${order_list.topping_price[topping.index]}" />
		<c:set var="topping_quantity" value="${order_list.topping_quantity[topping.index]}" />
		<c:set var="menu_subtotal" value="${menu_subtotal+(topping_price * topping_quantity)}" />
		<!-- JavaScriptで埋め込む -->
		<script>
		(function() {
			  const toppings = [
			    <c:forEach var="i" begin="0" end="${fn:length(order_list.topping_id) - 1}" varStatus="loop">
			      {
			        id: "<c:out value='${order_list.topping_id[i]}' />",
			        quantity: <c:out value='${order_list.topping_quantity[i]}' />
			      }<c:if test="${!loop.last}">,</c:if>
			    </c:forEach>
			  ];
			  updateOrderState(<c:out value='${order_id}' />, <c:out value='${menu_quantity}' />, <c:out value='${menu_stock}' />, <c:out value='${menu_subtotal}' />, toppings);
		})();
		</script>
	</c:forEach>
</c:forEach>
</html>