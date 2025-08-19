/**
 * 

/*タブのカテゴリーに合った商品を表示する*/
document.addEventListener('DOMContentLoaded', () => {

	//全てのラジオボタンを取得
	const tabs = document.querySelectorAll('input[name="tab"]');

	//全ての注文一覧が入っている親要素を取得
	const orderBox = document.querySelector('.tab-contents');
	if (!orderBox) return;

	const orders = orderBox.querySelectorAll('.order_row');
	
	

	//タブ変更時のフィルター表示
	// ✅ 絞り込み処理：カテゴリに一致する注文だけ表示
		const applyFilter = (selectedCategory) => {
			orders.forEach(order => {
				const category = order.getAttribute('data-category');
				order.style.display = category === selectedCategory ? '' : 'none';
			});

			document.querySelectorAll('.selected-category').forEach(input => {
				input.value = selectedCategory;
			});

			window.scrollTo({ top: 0 });
		};

		//タブの変更イベントを登録
		tabs.forEach((tab, index) => {
			tab.addEventListener('change', () => {
				if (tab.checked) {
					const selectedCategory = categoryList[index];
					applyFilter(selectedCategory);
				}
			});
		});

		// 初期表示でチェックされているタブに対応した絞り込みを実行
		tabs.forEach((tab, index) => {
			if (tab.checked) {
				const selectedCategory = categoryList[index];
				applyFilter(selectedCategory);
			}
		});
	});



