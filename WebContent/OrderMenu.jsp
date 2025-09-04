<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.util.List"%>
<%@ page import="servlet.product_list"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map.Entry"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
<!--.jsの呼び出し-->
<script src="JavaScript/setTabContent.js"></script>
<body>
	<!--ヘッダー(店の名前)-->
	<header class="header-storename">
		<div class="header-image-wrapper">
			<img src="Image/木目3.jpg" alt="背景" class="header-background-image">
			<img src="Image/biglogo.png" alt="店の名前" class="header-image">
		</div>
	</header>
	<main>
		<!--カテゴリー-->
		<div class="tab">
			<!-- ラジオボタン（表示制御のキーになる） -->
			<!-- 直前に押されていたボタンを呼び出す -->
			<c:forEach var="category" items="${categoryList}" varStatus="status">
				<input type="radio" name="tab" class="tab-item" id="tab${status.index}" ${status.index == 0 ? "checked" : ""}>
			</c:forEach>
			<div class="tab-wrapper">
				<!-- ラベル（横スクロール） -->
				<div class="tab-labels">
					<c:forEach var="category" items="${categoryList}" varStatus="status">
						<label for="tab${status.index}"><c:out value="${category}" /></label>
					</c:forEach>
				</div>
			</div>
			<div class="tab-contents">
				<c:if test="${not empty product_list}">
					<c:forEach var="product" items="${product_list}">
						<div class="order_row hidden-row" data-category="<c:out value='${product.category}'/>">
							<div class="menu">
							<c:if test="${product.displayflag == 1}">
								<li>
									<div class="menu-row">
										<div class="break-word bold-text"><c:out value="${product.name}" /></div>
										<c:if test="${product.stock != 0}">
											<%-- 在庫がある場合は商品詳細画面へ遷移 --%>
											<form action="DetailsAdd" method="post">
												<input type="hidden" name="from" value="OrderMenu.jsp">
												<input type="hidden" name="id" value="<c:out value="${product.id}" />">
												<input type="hidden" name="name" value="<c:out value="${product.name}" />">
												<input type="hidden" name="price" value="<c:out value="${product.price}" />">
												<input type="hidden" name="category" value="<c:out value="${product.category}" />"> 
												<input type="image" src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
											</form>
										</c:if>
										<c:if test="${product.stock == 0}">
											<img src="Image/soldout.png" alt="売り切れ" style="width: 55px; height: auto;">
										</c:if>
									</div>
									<p><c:out value="${product.price}" />円</p>
								</li>
								</c:if>
							</div>
							</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty product_list}">
				商品情報がありません。
				</c:if>
			</div>
		</div>
	</main>
	<!-- jsにjspのcategoryListを渡す -->
	<script>
		const categoryList=[
			<c:forEach items="${categoryList}" var="cat" varStatus="category_">
			'<c:out value="${cat}"/>'<c:if test="${!category_.last}">,</c:if>
			</c:forEach>
			];
	</script>
	<script
		src="<%=request.getContextPath()%>/JavaScript/OrderMenuTab.js"></script>
	<footer class="footer-buttons">
		<div class="table-number"><c:out value="${sessionScope.tableNumber}" />卓</div>
		<div class="footer-wrapper">
			<!--ボタン-->
			<!--注文リストへ遷移-->
			<form action="OrderList" method="post">
				<button class="fixed-right-button">
					<img src="Image/cart.png" alt="注文リストのボタン"><div>注文リスト<span class="count">1</span></div>
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
