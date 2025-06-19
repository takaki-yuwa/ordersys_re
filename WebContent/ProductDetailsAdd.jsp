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
<%
String fromPage=request.getParameter("from");
//メニュー画面から遷移してきた場合
if("OrderMenu.jsp".equals(fromPage)){
%>
<script>
	let topping = 0;
	let total = ${sessionScope.productList.price};
</script>
<%
//注文リスト画面から遷移してきた場合
}else if("OrderList.jsp".equals(fromPage)){
%>
<script>
	let total = ${sessionScope.changeList.product_subtotal};
	let topping=0;
</script>
<%
}
%>
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
			<%
			//メニュー画面から遷移してきた場合
if("OrderMenu.jsp".equals(fromPage)){
			%>
			<c:if test="${not empty sessionScope.productList}">
				<div class="product-text">${sessionScope.productList.name}</div>
				<div class="price-text">${sessionScope.productList.price}円(税込)</div>
			</c:if>
			<%
			//注文リスト画面から遷移してきた場合
			}else if("OrderList.jsp".equals(fromPage)){
			%>
			<c:if test="${not empty sessionScope.changeList}">
				<div class="product-text">${sessionScope.changeList.product_name}</div>
				<div class="price-text">${sessionScope.changeList.product_price}円(税込)</div>
			</c:if>
			<%
			}
			%>
		</div>
	</header>
	<main class="details-main">
		<%
		//メニュー画面から遷移してきた場合
		if("OrderMenu.jsp".equals(fromPage)){
		%>
			<!-- セッションからカテゴリー名を取ってくる -->
			<c:if test="${not empty sessionScope.productList.category}">
				<c:choose>
					<c:when
						test="${sessionScope.productList.category == 'お好み焼き' || sessionScope.productList.category == 'もんじゃ焼き'}">
						<c:if test="${not empty topping_list}">
	                        <p>トッピング:110円</p>
							<c:forEach var="topping" items="${topping_list}"
								varStatus="status">
								<li class="topping-row">
									<div class="break-topping">${topping.name}</div>
									<button class="counter-button minus" id="toppingButton" data-id="${topping.id}"
										data-index="${status.index}">-</button> <input type="text"
									value="0" class="counter-input" readonly>
									<button class="counter-button plus" data-index="${status.index}" id="toppingButton"
										 data-id="${topping.id}" data-max="${topping.stock}">+</button>
								</li>
							</c:forEach>
                        </c:if>
                        <c:if test="${empty topping_list}">
                            <div>トッピングはありません。</div>
                        </c:if>
                    </c:when>
                </c:choose>
            </c:if>
        <%
        //注文リスト画面から遷移してきた場合
        }else if("OrderList.jsp".equals(fromPage)){
        %>
        <!-- セッションからカテゴリー名を取ってくる -->
		<c:if test="${not empty sessionScope.changeList.category_name}">
			<c:choose>
				<c:when
					test="${sessionScope.changeList.category_name == 'お好み焼き' || sessionScope.changeList.category_name == 'もんじゃ焼き'}">
					<p>トッピング:110円</p>
					<c:if test="${not empty topping_list}">
						<c:forEach var="topping" items="${topping_list}"
							varStatus="status">

							<c:set var="topping_quantity"
								value="${sessionScope.changeList.topping_quantity[status.index]}" />
							<li class="topping-row">
								<div class="break-topping">${topping.name}</div>
								<button class="counter-button minus" id="toppingButton"
									data-id="${topping.id}" data-index="${status.index}">-</button>
								<input type="text" value="${topping_quantity}"
								class="counter-input" readonly>
								<button class="counter-button plus" data-index="${status.index}"
									id="toppingButton" data-id="${topping.id}"
									data-max="${topping.stock}">+</button>
							</li>
						</c:forEach>
					</c:if>
					<c:if test="${empty topping_list}">
						<div>トッピングはありません。</div>
					</c:if>
				</c:when>
			</c:choose>
		</c:if>
		<%
		}
		%>
	</main>

	<footer class="footer-buttons">
		<div class="table-number">${sessionScope.tableNumber}卓</div>
		<div class="footer-wrapper">
			<!-- ボタン -->
			<%
			//メニュー画面から遷移してきた場合
			if("OrderMenu.jsp".equals(fromPage)){
			%>
				<form action="OrderList" method="post">
					<button class="fixed-right-button">
					    <input type="hidden" name="product_id" value="${sessionScope.productList.id}">
					    <input type="hidden" name="product_name" value="${sessionScope.productList.name}">
					    <input type="hidden" name="product_price" value="${sessionScope.productList.price}">
					    <input type="hidden" name="category_name" value="${sessionScope.productList.category}">
					    <input type="hidden" name="tableNo" value="3">
	                    <c:forEach var="topping" items="${topping_list}" varStatus="status">
	                        <input type="hidden" name="topping" value="${topping.id}">
	                        <input type="hidden" name="topping_name" value="${topping.name}">
	                        <input type="hidden" name="topping_quantity" id="topping-${topping.id}" value="0">
	                    </c:forEach>
	                    <input type="hidden" name="total" id="input-total" value="">
						<img src="Image/addCart.png" alt="追加のボタン"> 追加
					</button>
				</form>
			<%
			//注文リスト画面から遷移してきた場合
			}else if("OrderList.jsp".equals(fromPage)){
			%>
				<form action="OrderList" method="post">
					<button class="fixed-right-button">
						<input type="hidden" name="order_id" value="${sessionScope.changeList.order_id}">
                        <input type="hidden" name="product_id" value="${sessionScope.changeList.product_id}">
                        <input type="hidden" name="product_name" value="${sessionScope.changeList.product_name}">
                        <input type="hidden" name="product_price" value="${sessionScope.changeList.product_price}">
                        <input type="hidden" name="category_name" value="${sessionScope.changeList.category_name}">
                        <input type="hidden" name="product_subtotal" value="${sessionScope.changeList.product_subtotal}">
                        <input type="hidden" name="tableNo" value="3">
                        <c:forEach var="topping_id" items="${sessionScope.changeList.topping_id}" varStatus="status">
                        	<c:set var="topping_name" value="${sessionScope.changeList.topping_name[status.index]}" />
                        	<c:set var="topping_price" value="${sessionScope.changeList.topping_price[status.index]}" />
                        	<c:set var="topping_quantity" value="${sessionScope.changeList.topping_quantity[status.index]}" />
                            <input type="hidden" name="topping_id[]" value="${topping_id}">
                            <input type="hidden" name="topping_name[]" value="${topping_name}">
                            <input type="hidden" name="topping_price[]" value="${topping_price}">
                            <%--valueに変更していない個数がリセットされないように取得した${topping_quantity}を入れる--%>
                            <input type="hidden" name="topping_quantity[]" id="topping-${topping_id}" value="${topping_quantity}">
                        </c:forEach>
                        <input type="hidden" name="total" id="input-total" value="">
						<img src="Image/changeCart.png" alt="変更のボタン"> 変更
					</button>
				</form>
			<%
			}
			%>
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
function initializeCounterButtons(container = document) {
    container.querySelectorAll('.counter-button').forEach(button => {
        button.removeEventListener('click', handleCounterClick); // 安全対策
        button.addEventListener('click', handleCounterClick);
    });
}

function handleCounterClick(event) {
    // -ボタンと+ボタンのクリックイベントを処理
    const button = event.currentTarget;
    const isMinusButton = button.classList.contains('minus');
    const isPlusButton = button.classList.contains('plus');
    const max = parseInt(button.getAttribute('data-max')) || 20;
    const min = parseInt(button.getAttribute('data-min')) || 0;
    
    // 対応する input 要素を取得
    const input = button.parentElement.querySelector('.counter-input');
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

    // トッピングの値段は固定値を設定しているので修正が必要
    totalElem.textContent = total + (110 * topping);
    inputTotalElem.value = total + (110 * topping);

    const minusButton = button.parentElement.querySelector('.counter-button.minus');
    const plusButton = button.parentElement.querySelector('.counter-button.plus');
    changeButtonColor(input.value, min, max, minusButton, plusButton);
    const btnId = button.dataset.id;  // data-item-id を取得
    console.log(JSON.stringify(btnId));
    console.log('topping-' + btnId);
    document.getElementById('topping-' + btnId).value = input.value;
}

document.addEventListener('DOMContentLoaded', () => {
    initializeCounterButtons();

    // ページの読み込み時にボタンの見た目を初期化
    document.querySelectorAll('.counter-button').forEach(button => {
        const input = button.parentElement.querySelector('.counter-input');
        const currentValue = parseInt(input.value) || 0;
        const min = parseInt(button.getAttribute('data-min')) || 0;
        const max = parseInt(button.getAttribute('data-max')) || 20;
        
        const minusButton = button.parentElement.querySelector('.counter-button.minus');
        const plusButton = button.parentElement.querySelector('.counter-button.plus');
        changeButtonColor(currentValue, min, max, minusButton, plusButton);
    });

    // 初期値を反映
    totalElem.textContent = total + (110 * topping);
    inputTotalElem.value = total + (110 * topping);
});

const buttons = document.getElementsByClassName("counter-button");
const totalElem = document.getElementById('total');
const inputTotalElem = document.getElementById('input-total');

function changeButtonColor(currentValue, min, max, minusButton, plusButton) {
    // - ボタン
    if (currentValue == min) {
        minusButton.setAttribute('disabled', 'true');
    } else {
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
