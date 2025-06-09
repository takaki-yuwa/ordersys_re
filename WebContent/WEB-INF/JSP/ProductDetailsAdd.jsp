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
<script>
	let topping = 0;
	let total = ${sessionScope.productList.price};
</script>
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
								<button class="counter-button minus" id="toppingButton"
									data-index="${status.index}">-</button> <input type="text"
								value="0" class="counter-input" readonly>
								<button class="counter-button plus" data-index="${status.index}" id="toppingButton"
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
						            const min = parseInt(this.getAttribute('data-min')) || 0;
						            
						            // 対応する input 要素を取得
						            const input = this.parentElement.querySelector('.counter-input');
						            let currentValue = parseInt(input.value);
						
						            if (isNaN(currentValue)) {
						                currentValue = 0;
						            }
						
						            // - ボタンがクリックされた場合は値を減らす
						            if (isMinusButton && currentValue > min) {
						                input.value = currentValue - 1;
						                topping--;
						            }
						
						            // + ボタンがクリックされた場合は値を増やす
						            if (isPlusButton && currentValue < max) {
						                input.value = currentValue + 1;
						                topping++;
						            }

						            const minusButton = this.parentElement.querySelector('.counter-button.minus');
						            const plusButton = this.parentElement.querySelector('.counter-button.plus');
						            changeButtonColor(input.value,min,max,minusButton,plusButton);
						            document.getElementById('total').value = input.value;
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
			<form action="OrderList" method="post">
				<button class="fixed-right-button">
				    <input type="hidden" name="id" value="${sessionScope.productList.id}">
                    <c:forEach var="topping" items="${topping_list}" varStatus="status">
                        <input type="hidden" name="topping" value="${topping.id}">
                        <input type="hidden" name="topping-value" id="topping-${topping.id}" value="0">
                    </c:forEach>
					<img src="Image/addCart.png" alt="追加のボタン"> 追加
				</button>
			</form>
			<a href="OrderSystem">
				<button class="fixed-left-button">
					<img src="Image/menu.png" alt="メニューのボタン"> メニュー
				</button>
			</a>
		</div>
	</footer>

	<footer class="footer-subtotal">
		<div class="footer-subtotal-wrapper">
			<div class="subtotal-text">小計:<span id="total">0</span>円(税込)</div>
		</div>
	</footer>

<script>
const buttons = document.getElementsByClassName("counter-button");
const totalElem = document.getElementById('total');
for(let button of buttons){
	button.addEventListener("click", function() {
		// トッピングの値段は固定値を設定しているので修正が必要
		totalElem.textContent  = total +(110 * topping);
	});
}
document.addEventListener("DOMContentLoaded", function() {
	  // ページのDOMが完全に読み込まれた後に実行される処理
	totalElem.textContent = total +(110 * topping);
});

function changeButtonColor(currentValue,min,max,minusButton,plusButton) {
    // - ボタン
    if (currentValue <= min) {
        minusButton.classList.add('disabled');
        minusButton.setAttribute('disabled', 'true');
    } else {
        minusButton.classList.remove('disabled');
        minusButton.removeAttribute('disabled');
    }

    // + ボタン
    if (currentValue == max) {
        plusButton.classList.add('disabled');
        plusButton.setAttribute('disabled', 'true');
    } else {
        plusButton.classList.remove('disabled');
        plusButton.removeAttribute('disabled');
    }
};
</script>
</body>
</html>
