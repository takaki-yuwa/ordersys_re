<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
<title>メニュー画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="CSS/Import.css">
<link rel="stylesheet" href="CSS/ProductCategory.css">
<link rel="stylesheet" href="CSS/Menu.css">
<link rel="stylesheet" href="CSS/WordWrap.css">
<link rel="stylesheet" href="CSS/Tab.css">
<link rel="icon" href="data:," />
<!--.jsの呼び出し-->
<script src="JavaScript/Menu3.js" defer></script>
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
<!--カテゴリー-->
<!--タブ表示ラジオボタンで設定してCSSで調整-->
<div class="area">
  <input type="radio" name="tab_name" id="tab1" checked>
  <label class="tab_class" for="tab1">お好み焼き</label>
  <div class="content_class">
    <ul class="menu" id="price"></ul>
  </div>
  <input type="radio" name="tab_name" id="tab2" >
  <label class="tab_class" for="tab2">鉄板焼き</label>
  <div class="content_class">
    <p>鉄板焼き一覧</p>
  </div>
  <input type="radio" name="tab_name" id="tab3" >
  <label class="tab_class" for="tab3">タブ3</label>
  <div class="content_class">
    <p>タブ3のコンテンツを表示します</p>
  </div>
  <input type="radio" name="tab_name" id="tab4" >
  <label class="tab_class" for="tab4">タブ4</label>
  <div class="content_class">
    <p>タブ4のコンテンツを表示します</p>
  </div>
  <input type="radio" name="tab_name" id="tab5" >
  <label class="tab_class" for="tab5">タブ5</label>
  <div class="content_class">
    <p>タブ5のコンテンツを表示します</p>
  </div>
<ul class="menu" id="price"></ul>
</main>
<footer class="footer-buttons">
	<div class="table-number">3卓</div>
	<div class="footer-wrapper">
		<!--ボタン-->
		<!--注文リストへ遷移-->
		<a href="OrderList.html">
		<button class="fixed-right-button">
			<img src="Image/cart.png" alt="注文リストのボタン">
			注文リスト
		</button>
		</a>
		<!--履歴・お会計へ遷移-->
		<a href="OrderHistory.html">
		<button class="fixed-left-button">
			<img src="Image/menuhistory.png" alt="履歴・お会計のボタン">
			履歴・お会計
		</button>
		</a>
	</div>
</footer>
</body>
</html>