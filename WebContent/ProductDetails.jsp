<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
    <title>商品詳細画面</title>
    <link rel="stylesheet" href="CSS/Import.css">
    <link rel="stylesheet" href="CSS/WordWrap.css">
    <link rel="stylesheet" href="CSS/ProductDetails.css">
    <link rel="stylesheet" href="CSS/Topping.css">
    <script src="JavaScript/Topping.js" defer></script>
</head>
<body>
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
            <div class="product-text">${productName}</div>
            <div class="price-text">${productPrice}円(税込)</div>
        </div>
    </header>
    <main>
        <p>トッピング:110円</p>
        <ul class="topping" id="topping"></ul>
    </main>
    <footer class="footer-buttons">
        <div class="table-number">3卓</div>
        <div class="footer-wrapper">
            <!-- ボタン -->
            <a href="OrderList.html">
                <button class="fixed-right-button">
                    <img src="Image/addCart.png" alt="追加のボタン">
                    追加
                </button>
            </a>
            <a href="OrderMenu.jsp">
                <button class="fixed-left-button">
                    <img src="Image/menu.png" alt="メニューのボタン">
                    メニュー
                </button>
            </a>
        </div>
    </footer>
    <footer class="footer-subtotal">
        <div class="footer-subtotal-wrapper">
            <div class="subtotal-text">小計:111,430円(税込)</div>
        </div>
    </footer>
</body>
</html>