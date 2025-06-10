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
	const totalElem = document.getElementById('total'); // total 要素の取得
	document.getElementById(`menu_id`).value=0;

	// 要素が存在する場合のみイベントを登録
	if (incrementbtn && decrementbtn && counter && subtotal && total) {
		incrementbtn.addEventListener('click', () => {
			if (orderstate.count < menuStock) {
				const quantityValue=++orderstate.count;
				counter.innerHTML = quantityValue;
				const subtotalValue = parseFloat(menuSubtotal) * orderstate.count;
				subtotal.innerHTML = subtotalValue; // 個別の価格を使う
				setQuantity(quantityValue);
				setPrice(subtotalValue);
				updateTotal();
			}

		});

		decrementbtn.addEventListener('click', () => {
			if (orderstate.count > 0) {
				const quantityValue=--orderstate.count;
				counter.innerHTML = quantityValue;
				const subtotalValue = parseFloat(menuSubtotal) * orderstate.count;
				subtotal.innerHTML = subtotalValue; // 個別の価格を使う
				setQuantity(quantityValue);
				setPrice(subtotalValue);
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

function setPrice(price) {
    document.getElementById(`priceField`).value = price;
}

function setQuantity(count) {
    document.getElementById(`countField`).value = count;
}