<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page import="java.util.List"%>
<%@ page import="servlet.product_list"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>メニュー画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/ProductCategory.css">
<link rel="stylesheet" href="CSS/Menu.css">
<link rel="stylesheet" href="CSS/WordWrap.css">
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

	<main class="default-main">
		<!--カテゴリー-->
		<div class="category-wrapper">
			<button class="category-button" onclick="location.href='#01'">お好み焼き</button>
			<button class="category-button" onclick="location.href='#02'">もんじゃ焼き</button>
			<button class="category-button" onclick="location.href='#03'">鉄板焼き</button>
			<button class="category-button" onclick="location.href='#04'">サイドメニュー</button>
			<button class="category-button" onclick="location.href='#05'">ソフトドリンク</button>
			<button class="category-button" onclick="location.href='#06'">お酒</button>
			<button class="category-button" onclick="location.href='#07'">ボトル</button>
		</div>

		<c:forEach var="category" items="${categoryMap}">
			<c:set var="categoryId" value="${category.key}" />
			<c:set var="categoryName" value="${category.value}" />
			
			<h1 id="${categoryId}">${categoryName}</h1>
			<div class="menu">
				<c:set var="hasProducts" value="false" />
				<c:forEach var="product" items="${product_list}">
					<c:if test="${product.category == categoryName && product.displayflag == 1}">
						<c:set var="hasProducts" value="true" />
						<li>
							<div class="menu-row">
								<div class="break-word bold-text">${product.name}</div>
								<div>
									<c:choose>
										<%-- 在庫がない場合 --%>
										<c:when test="${product.stock == 0}">
											<img src="Image/soldout.png" alt="売り切れ" style="width: 55px; height: auto;">
										</c:when>
										<c:otherwise>
											<%-- 在庫がある場合 --%>
											<form action="DetailsAdd" method="post">
												<input type="hidden" name="from" value="OrderMenu.jsp" /> 
												<input type="hidden" name="id" value="${product.id}" /> 
												<input type="hidden" name="name" value="${product.name}" /> 
												<input type="hidden" name="price" value="${product.price}" /> 
												<input type="hidden" name="category" value="${product.category}" />
												<input type="hidden" name="displayflag" value="${product.displayflag}" /> 
												<input type="image" src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
											</form>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div>${product.price}円</div>
						</li>
					</c:if>
				</c:forEach>
				<c:if test="${!hasProducts}">
					<p>商品情報がありません。</p>
				</c:if>
			</div>
		</c:forEach>
	</main>
	<footer class="footer-buttons">
		<div class="table-number">${sessionScope.tableNumber}卓</div>
		<div class="footer-wrapper">
			<!--注文リストへ遷移-->
			<form action="OrderList" method="post">
				<button class="fixed-right-button">
					<img src="Image/cart.png" alt="注文リストのボタン"> 注文リスト
				</button>
			</form>
			<!--履歴・お会計へ遷移-->
			<form action="OrderHistory" method="post">
				<button class="fixed-left-button">
					<input type="hidden" name="orderPrice" value="0"> 
					<img src="Image/menuhistory.png" alt="履歴・お会計のボタン"> 履歴・お会計
				</button>
			</form>
		</div>
	</footer>
</body>
</html>