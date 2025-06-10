<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
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
<!--.jsの呼び出し-->
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

		<!--リストを取得-->
		<%
		List<product_list> productList = (List<product_list>) request.getAttribute("product_list");
		%>

		<h1 id="01">お好み焼き</h1>

		<!-- リストを「お好み焼き」カテゴリーで絞り込んで表示 -->
		<div class="menu">
			<%
			if (productList != null && !productList.isEmpty()) {
				for (product_list p : productList) {
					// 「お好み焼き」カテゴリの商品だけを表示
					if ("お好み焼き".equals(p.getCategory())) {
			%>
			<li>
				<div class="menu-row">
					<div class="break-word bold-text"><%=p.getName()%></div>

					<div>
						<form action="DetailsAdd" method="post">
							<input type="hidden" name="from" value="OrderMenu.jsp">
							<input type="hidden" name="id" value="<%=p.getId()%>">
							<input type="hidden" name="name" value="<%=p.getName()%>">
							<input type="hidden" name="price" value="<%=p.getPrice()%>">
							<input type="hidden" name="category"
								value="<%=p.getCategory()%>"> <input type="image"
								src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
						</form>
					</div>
				</div>
				<div><%=p.getPrice()%>
					円
				</div>
			</li>
			<%
			}
			}
			} else {
			%>
			<p>商品情報がありません。</p>
			<%
			}
			%>
		</div>
		<h1 id="02">もんじゃ焼き</h1>

		<!-- リストを「もんじゃ焼き」カテゴリーで絞り込んで表示 -->
		<div class="menu">
			<%
			if (productList != null && !productList.isEmpty()) {
				for (product_list p : productList) {
					// 「もんじゃ焼き」カテゴリの商品だけを表示
					if ("もんじゃ焼き".equals(p.getCategory())) {
			%>
			<li>
				<div class="menu-row">
					<div class="break-word bold-text"><%=p.getName()%></div>

					<div>
						<form action="DetailsAdd" method="post">
							<input type="hidden" name="id" value="<%=p.getId()%>">
							<input type="hidden" name="name" value="<%=p.getName()%>">
							<input type="hidden" name="price" value="<%=p.getPrice()%>">
							<input type="hidden" name="category"
								value="<%=p.getCategory()%>"> <input type="image"
								src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
						</form>
					</div>
				</div>
				<div><%=p.getPrice()%>
					円
				</div>
			</li>
			<%
			}
			}
			} else {
			%>
			<p>商品情報がありません。</p>
			<%
			}
			%>
		</div>
		<h1 id="03">鉄板焼き</h1>

		<!-- リストを「鉄板焼き」カテゴリーで絞り込んで表示 -->
		<div class="menu">
			<%
			if (productList != null && !productList.isEmpty()) {
				for (product_list p : productList) {
					// 「鉄板焼き」カテゴリの商品だけを表示
					if ("鉄板焼き".equals(p.getCategory())) {
			%>
			<li>
				<div class="menu-row">
					<div class="break-word bold-text"><%=p.getName()%></div>

					<div>
						<form action="DetailsAdd" method="post">
							<input type="hidden" name="id" value="<%=p.getId()%>">
							<input type="hidden" name="name" value="<%=p.getName()%>">
							<input type="hidden" name="price" value="<%=p.getPrice()%>">
							<input type="hidden" name="category"
								value="<%=p.getCategory()%>"> <input type="image"
								src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
						</form>
					</div>
				</div>
				<div><%=p.getPrice()%>
					円
				</div>
			</li>
			<%
			}
			}
			} else {
			%>
			<p>商品情報がありません。</p>
			<%
			}
			%>
		</div>

		<h1 id="04">サイドメニュー</h1>

		<!-- リストを「サイドメニュー」カテゴリーで絞り込んで表示 -->
		<div class="menu">
			<%
			if (productList != null && !productList.isEmpty()) {
				for (product_list p : productList) {
					// 「サイドメニュー」カテゴリの商品だけを表示
					if ("サイドメニュー".equals(p.getCategory())) {
			%>
			<li>
				<div class="menu-row">
					<div class="break-word bold-text"><%=p.getName()%></div>

					<div>
						<form action="DetailsAdd" method="post">
							<input type="hidden" name="id" value="<%=p.getId()%>">
							<input type="hidden" name="name" value="<%=p.getName()%>">
							<input type="hidden" name="price" value="<%=p.getPrice()%>">
							<input type="hidden" name="category"
								value="<%=p.getCategory()%>"> <input type="image"
								src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
						</form>
					</div>
				</div>
				<div><%=p.getPrice()%>
					円
				</div>
			</li>
			<%
			}
			}
			} else {
			%>
			<p>商品情報がありません。</p>
			<%
			}
			%>
		</div>

		<h1 id="05">ソフトドリンク</h1>

		<!-- リストを「ソフトドリンク」カテゴリーで絞り込んで表示 -->
		<div class="menu">
			<%
			if (productList != null && !productList.isEmpty()) {
				for (product_list p : productList) {
					// 「ソフトドリンク」カテゴリの商品だけを表示
					if ("ソフトドリンク".equals(p.getCategory())) {
			%>
			<li>
				<div class="menu-row">
					<div class="break-word bold-text"><%=p.getName()%></div>

					<div>
						<form action="DetailsAdd" method="post">
							<input type="hidden" name="id" value="<%=p.getId()%>">
							<input type="hidden" name="name" value="<%=p.getName()%>">
							<input type="hidden" name="price" value="<%=p.getPrice()%>">
							<input type="hidden" name="category"
								value="<%=p.getCategory()%>"> <input type="image"
								src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
						</form>
					</div>
				</div>
				<div><%=p.getPrice()%>
					円
				</div>
			</li>
			<%
			}
			}
			} else {
			%>
			<p>商品情報がありません。</p>
			<%
			}
			%>
		</div>

		<h1 id="06">お酒</h1>

		<!-- リストを「お酒」カテゴリーで絞り込んで表示 -->
		<div class="menu">
			<%
			if (productList != null && !productList.isEmpty()) {
				for (product_list p : productList) {
					// 「お酒」カテゴリの商品だけを表示
					if ("お酒".equals(p.getCategory())) {
			%>
			<li>
				<div class="menu-row">
					<div class="break-word bold-text"><%=p.getName()%></div>

					<div>
						<form action="DetailsAdd" method="post">
							<input type="hidden" name="id" value="<%=p.getId()%>">
							<input type="hidden" name="name" value="<%=p.getName()%>">
							<input type="hidden" name="price" value="<%=p.getPrice()%>">
							<input type="hidden" name="category"
								value="<%=p.getCategory()%>"> <input type="image"
								src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
						</form>
					</div>
				</div>
				<div><%=p.getPrice()%>
					円
				</div>
			</li>
			<%
			}
			}
			} else {
			%>
			<p>商品情報がありません。</p>
			<%
			}
			%>
		</div>

		<h1 id="07">ボトル</h1>

		<!-- リストを「ボトル」カテゴリーで絞り込んで表示 -->
		<div class="menu">
			<%
			if (productList != null && !productList.isEmpty()) {
				for (product_list p : productList) {
					// 「ボトル」カテゴリの商品だけを表示
					if ("ボトル".equals(p.getCategory())) {
			%>
			<li>
				<div class="menu-row">
					<div class="break-word bold-text"><%=p.getName()%></div>

					<div>
						<form action="DetailsAdd" method="post">
							<input type="hidden" name="id" value="<%=p.getId()%>">
							<input type="hidden" name="name" value="<%=p.getName()%>">
							<input type="hidden" name="price" value="<%=p.getPrice()%>">
							<input type="hidden" name="category"
								value="<%=p.getCategory()%>"> <input type="image"
								src="Image/plusButton.png" alt="商品詳細画面へ遷移する">
						</form>
					</div>
				</div>
				<div><%=p.getPrice()%>
					円
				</div>
			</li>
			<%
			}
			}
			} else {
			%>
			<p>商品情報がありません。</p>
			<%
			}
			%>
		</div>
	</main>


	<footer class="footer-buttons">
		<div class="table-number">3卓</div>
		<div class="footer-wrapper">
			<!--ボタン-->
			<!--注文リストへ遷移-->
			<form action="OrderList" method="post">
				<button class="fixed-right-button">
					<img src="Image/cart.png" alt="注文リストのボタン"> 注文リスト
				</button>
			</form>
			<!--履歴・お会計へ遷移-->
			<form action="OrderHistory" method="post">
				<button class="fixed-left-button">
					<input type="hidden" name="tableNo" value="4">
					<%
					int iOrderPrice[] = { 770, 740 };
					for (int i = 0; i < iOrderPrice.length; i++) {
					%>
					<input type="hidden" name="orderPrice"
						value="<%=iOrderPrice[i]%>">
					<%
					}
					%>
					<img src="Image/menuhistory.png" alt="履歴・お会計のボタン"> 履歴・お会計
				</button>
			</form>
		</div>
	</footer>
</body>
</html>
