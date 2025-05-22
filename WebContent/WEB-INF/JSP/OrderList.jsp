<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
<title>Insert title here</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">

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
		<a href="OrderMenu.html">
		<button class="fixed-left-button">
			<img src="Image/menu.png" alt="メニューのボタン">
			メニュー
		</button>
		</a>
	</div>
</footer>
</body>
</html>