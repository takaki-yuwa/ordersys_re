<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>注文完了画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/OrderCompleted.css">
<link rel="icon" href="/favicon.ico" type="image/x-icon">

</head>
<body>
	<!--ヘッダー(店の名前)-->
	<header class="header-storename">
		<div class="header-image-wrapper">
			<img src="Image/木目3.jpg" alt="背景" class="header-background-image">
			<img src="Image/biglogo.png" alt="店の名前" class="header-image">
		</div>
	</header>
	<main>
		<div class="center-container">
			<div class="square-box">
				<p class="center-text">ご注文いただき</p>
				<p class="center-text">ありがとうございます</p>
			</div>
		</div>
		<p class="center-text">お料理を準備いたしますので</p>
		<p class="center-text">しばらくお待ち下さい</p>

	</main>
	<footer class="footer-buttons">
		<div class="table-number">3卓</div>
		<div class="footer-wrapper">
			<!--ボタン-->
			<!--メニューへ遷移-->
			<a href="OrderSystem">
				<button class="fixed-left-button">
					<img src="Image/menu.png" alt="メニューのボタン"> メニュー
				</button>
			</a>
		</div>
	</footer>
</body>
</html>