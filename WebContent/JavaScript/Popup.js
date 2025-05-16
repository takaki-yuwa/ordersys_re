/**
 * 
 */
//要素の取得
const openPopupButton=document.getElementById('open-popup');
const closePopupButton=document.getElementById('close-popup');
const confirmButton=document.getElementById('confirm-button');
const popupOverlay=document.getElementById('popup-overlay');
const popupContent=document.getElementById('popup-content');

//ポップアップを表示する
openPopupButton.addEventListener('click',()=>{
	popupOverlay.classList.add('show');
	popupContent.classList.add('show');
});

//ポップアップを閉じる
closePopupButton.addEventListener('click',()=>{
	popupOverlay.classList.remove('show');
	popupContent.classList.remove('show');
});

//会計完了画面へ遷移する
confirmButton.addEventListener('click',()=>{
	window.location.href='Accounting.html';
});