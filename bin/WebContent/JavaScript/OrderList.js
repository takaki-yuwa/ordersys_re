/**
 * 
 */

function updateOrderState(menuId, menuQuantity, menuStock, menuSubtotal) {
	var orderstate = { count: menuQuantity };

	// 動的に生成された要素があるか確認
	const incrementbtn = document.getElementById(`increment-${menuId}`);
	const decrementbtn = document.getElementById(`decrement-${menuId}`);
	const counter = document.getElementById(`counter-${menuId}`);
	const subtotal = document.getElementById(`subtotal-${menuId}`);

	// 要素が存在する場合のみイベントを登録
	if (incrementbtn && decrementbtn && counter && subtotal && total) {
		incrementbtn.addEventListener('click', () => {
			if (orderstate.count < menuStock) {
				counter.innerHTML = ++orderstate.count;
				subtotal.innerHTML = orderstate.count * menuSubtotal; // 個別の価格を使う
				updateTotal();
			}

		});

		decrementbtn.addEventListener('click', () => {
			if (orderstate.count > 0) {
				counter.innerHTML = --orderstate.count;
				subtotal.innerHTML = orderstate.count * menuSubtotal;
				updateTotal();
			}
		});
	} else {
		console.warn(`Element with ID increment-${menuId} or decrement-${menuId} not found.`);
	}
}

function updateTotal() {
	let total = 0;
	const subtotalElems = document.querySelectorAll('[id^="subtotal-"]');
	subtotalElems.forEach(elem => {
		const value = parseInt(elem.innerHTML);
		if (!isNaN(value)) total += value;
	});

	const totalElem = document.getElementById('total');
	if (totalElem) {
		totalElem.innerHTML = `合計:${total}円(税込)`;
	}
}