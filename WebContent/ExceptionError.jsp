<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
<title>エラー画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/ExceptionError.css">
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
		<c:if test="${empty sessionScope.tableNumber}">
			<div class="center-container">
				<div class="square-box">
					<p class="center-text bold-text">卓番が取得できていません</p>
					<p class="center-text bold-text">もう一度URLから取得してください</p>
				</div>
			</div>
		</c:if>
	</main>
</body>
</html>