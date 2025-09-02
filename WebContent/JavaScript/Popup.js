/**
 * 
 */
//要素の取得
const openPopupButton = document.getElementById('open-popup');
const closePopupButton = document.getElementById('close-popup');
const popupOverlay = document.getElementById('popup-overlay');
const popupContent = document.getElementById('popup-content');

//ポップアップを表示する
openPopupButton.addEventListener('click', () => {
	popupOverlay.classList.add('show');
	popupContent.classList.add('show');
});

//ポップアップを閉じる
closePopupButton.addEventListener('click', () => {
	popupOverlay.classList.remove('show');
	popupContent.classList.remove('show');
});

