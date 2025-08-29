<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文開始画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/OrderCompleted.css">
<link rel="icon" href="/favicon.ico" type="image/x-icon">
<script>
	// ページ読み込み後 3秒で自動POST
	window.onload = function() {
		setTimeout(function() {
			document.getElementById("autoForm").submit();
		}, 3000); // 3秒後に送信
	};
</script>
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
				<div class="table-number"><c:out value="${tableInfo.table_id}" />卓</div>
			</div>
		</div>
		<p class="center-text">いらっしゃいませ！</p>
	</main>
	<!-- 自動でPOSTするフォーム -->
	<form id="autoForm" action="OrderSystem" method="post">
		<input type="hidden" name="sessionNumber" value="<c:out value='${tableInfo.session_id}' />">
		<input type="hidden" name="tableNumber" value="<c:out value='${tableInfo.table_id}' />">
		<input type="hidden" name="sessionStatus" value="<c:out value='${tableInfo.session_status}' />">
	</form>
</body>
</html>