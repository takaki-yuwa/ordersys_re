/**
 * 
 */
//setTabContent.js
//タブの切替表示
document.addEventListener('DOMContentLoaded', () => {
	const tabs = document.querySelectorAll('input[type="radio"][name="tab"]');
	const tabContents = document.querySelectorAll('.tab-content');
	const tabLabels = document.querySelectorAll('.tab-labels label');

	tabs.forEach((tab, index) => {
		tab.addEventListener('change', () => {
			// タブ切り替え
			tabContents.forEach(tc => tc.style.display = 'none');
			if (tabContents[index]) tabContents[index].style.display = 'block';

			// ラベルスタイル切り替え
			tabLabels.forEach(label => {
				label.style.borderBottom = 'none';
				label.style.color = '#999';
			});
			if (tabLabels[index]) {
				tabLabels[index].style.borderBottom = '2px solid rgb(0, 128, 0)';
				tabLabels[index].style.color = 'rgb(0, 128, 0)';
			}
		});
	});

	// 初期表示設定
	const checkedTab = Array.from(tabs).find(tab => tab.checked);
	if (checkedTab) {
		checkedTab.dispatchEvent(new Event('change'));
	}
});
