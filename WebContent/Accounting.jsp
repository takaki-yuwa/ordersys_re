<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="servlet.accounting_list" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>会計確定画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/Accounting.css">
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
    <%
        accounting_list Accounting_List = (accounting_list)request.getAttribute("accountingList");
    %>
		<div class="center-container">
			<div class="square-box">
				<p class="center-text">会計が確定されました</p>
				<p class="center-text">ご利用ありがとうございます</p>
            <span class="bold-text size-text"><%= Accounting_List.getTableNo() %>卓</span>
            <span class="underline-text bold-text size-text">合計:<%= Accounting_List.getTotalPrice() %>円(税込)</span>
			</div>
		</div>
		<p class="center-text">レジにてお支払いください</p>
		<p class="center-text">またのご利用をお待ちしております</p>
	</main>
	<uji:dispatch />
    <uji:resourceText id="uji.disableBack"/>
</body>
</html>