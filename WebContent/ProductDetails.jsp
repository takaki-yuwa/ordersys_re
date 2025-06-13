<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="servlet.product_list"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>商品詳細画面</title>
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/WordWrap.css">
<link rel="stylesheet" href="CSS/ProductDetails.css">
<link rel="stylesheet" href="CSS/Topping.css">
<link rel="stylesheet" href="CSS/style.css">
<link rel="icon" href="data:," />
<!--<script src="JavaScript/Topping.js" defer></script>-->
</head>
<body>
	<!-- ヘッダー(店の名前) -->
	<header class="header-storename">
		<div class="header-image-wrapper">
			<img src="Image/木目3.jpg" alt="背景" class="header-background-image">
			<img src="Image/biglogo.png" alt="店の名前" class="header-image">
		</div>
	</header>
	<header class="header-product">
		<div class="header-product-wrapper">
			<!-- EL式を使用して商品名を表示 -->
			<c:if test="${not empty sessionScope.productList}">
				<div class="product-text">${sessionScope.productList.name}</div>
				<div class="price-text">${sessionScope.productList.price}円(税込)</div>
			</c:if>
		</div>
	</header>
	<main class="details-main">

		<!-- セッションからカテゴリー名を取ってくる -->
		<c:if test="${not empty sessionScope.productList.category}">
			<c:choose>
				<c:when
					test="${sessionScope.productList.category == 'お好み焼き' || sessionScope.productList.category == 'もんじゃ焼き'}">
					<p>トッピング:110円</p>
					<c:if test="${not empty topping_list}">
						<c:forEach var="topping" items="${topping_list}"
							varStatus="status">
							<li class="topping-row">
								<div class="break-topping">${topping.name}</div>
								<button class="counter-button minus"
									data-index="${status.index}">-</button> <input type="text"
								value="0" class="counter-input" readonly>
								<button class="counter-button plus" data-index="${status.index}"
									data-max="${topping.stock}">+</button>
							</li>
						</c:forEach>
						<script>
				    // -ボタンと+ボタンのクリックイベントを処理
				    document.querySelectorAll('.counter-button').forEach(button => {
				        button.addEventListener('click', function() {
				            const isMinusButton = this.classList.contains('minus');
				            const isPlusButton = this.classList.contains('plus');
				            const max = parseInt(this.getAttribute('data-max'));
				            
				            // 対応する input 要素を取得
				            const input = this.parentElement.querySelector('.counter-input');
				            let currentValue = parseInt(input.value);
				
				            if (isNaN(currentValue)) {
				                currentValue = 0;
				            }
				
				            // - ボタンがクリックされた場合は値を減らす
				            if (isMinusButton && currentValue > 0) {
				                input.value = currentValue - 1;
				            }
				
				            // + ボタンがクリックされた場合は値を増やす
				            if (isPlusButton && currentValue < max) {
				                input.value = currentValue + 1;
				            }
				
				            
				        });
				    });
				</script>



					</c:if>
					<c:if test="${empty topping_list}">
						<div>トッピングはありません。</div>
					</c:if>
				</c:when>
			</c:choose>
		</c:if>

	</main>

	<footer class="footer-buttons">
		<div class="table-number">3卓</div>
		<div class="footer-wrapper">
			<!-- ボタン -->
			<a href="OrderList.html">
				<button class="fixed-right-button">
					<img src="Image/addCart.png" alt="追加のボタン"> 追加
				</button>
			</a> <a href="OrderSystem">
				<button class="fixed-left-button">
					<img src="Image/menu.png" alt="メニューのボタン"> メニュー
				</button>
			</a>
		</div>
	</footer>

	<footer class="footer-subtotal">
		<!-- 小計 -->
		<div class="subtotal">
			<p>小計: ${sessionScope.subtotal}円(税込)</p>
		</div>
	</footer>

</body>
</html>
