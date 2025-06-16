/**
 * 
 */
//増減ボタン処理
function updateOrderState(menuId, menuQuantity, menuStock, menuSubtotal) {
    var orderstate = { count: menuQuantity };

    const incrementbtn = document.getElementById(`increment-${menuId}`);
    const decrementbtn = document.getElementById(`decrement-${menuId}`);
    const counter = document.getElementById(`counter-${menuId}`);
    const subtotal = document.getElementById(`subtotal-${menuId}`);
    const totalElem = document.getElementById('total');

    document.getElementById(`menu_id`).value = 0;

    if (incrementbtn && decrementbtn && counter && subtotal && totalElem) {
        // イベントリスナー重複防止のため、一度クローンで置き換え
        incrementbtn.replaceWith(incrementbtn.cloneNode(true));
        decrementbtn.replaceWith(decrementbtn.cloneNode(true));

        const newIncrementBtn = document.getElementById(`increment-${menuId}`);
        const newDecrementBtn = document.getElementById(`decrement-${menuId}`);
		//増加処理
        newIncrementBtn.addEventListener('click', () => {
            if (orderstate.count < menuStock) {
                const quantityValue = ++orderstate.count;
                counter.innerHTML = quantityValue;
                const subtotalValue = parseFloat(menuSubtotal) * orderstate.count;
                subtotal.innerHTML = subtotalValue;
                setQuantity(quantityValue);
                setPrice(subtotalValue);
                updateButtonDisplay(menuId, orderstate.count);
                updateTotal();
            }
        });
		//減少処理
        newDecrementBtn.addEventListener('click', () => {
            if (orderstate.count > 0) {
                if (orderstate.count === 1) {
                    showDeletePopup(menuId);
                } else {
                    const quantityValue = --orderstate.count;
                    counter.innerHTML = quantityValue;
                    const subtotalValue = parseFloat(menuSubtotal) * orderstate.count;
                    subtotal.innerHTML = subtotalValue;
                    setQuantity(quantityValue);
                    setPrice(subtotalValue);
                    updateButtonDisplay(menuId, orderstate.count);
                    updateTotal();
                }
            }
        });

        updateButtonDisplay(menuId, orderstate.count);

    } else {
        console.warn(`Element with ID increment-${menuId} or decrement-${menuId} not found.`);
    }
}
//ゴミ箱ボタンとマイナスボタン切替処理
function updateButtonDisplay(orderId, quantity) {
    const decrementBtn = document.getElementById(`decrement-${orderId}`);
    if (!decrementBtn) return;

    if (quantity === 1) {
        decrementBtn.innerHTML = '<img class="dustbox-img" src="Image/dustbox.png" alt="ゴミ箱ボタン">';
    } else {
        decrementBtn.innerHTML = '−';
    }
}
//ポップアップ処理
function showDeletePopup(orderId) {
    const popupOverlay = document.getElementById('popup-overlay');
    const popupContent = document.getElementById('popup-content');
    const closePopupButton = document.getElementById('close-popup');
    const confirmButton = document.getElementById('confirm-button');

    popupOverlay.classList.add('show');
    popupContent.classList.add('show');

    closePopupButton.onclick = () => {
        popupOverlay.classList.remove('show');
        popupContent.classList.remove('show');
    };

    confirmButton.onclick = () => {
        // 削除処理をここに追加
        popupOverlay.classList.remove('show');
        popupContent.classList.remove('show');

        const counter = document.getElementById(`counter-${orderId}`);
        const subtotal = document.getElementById(`subtotal-${orderId}`);
        if (counter && subtotal) {
            counter.innerHTML = '0';
            subtotal.innerHTML = '0';
            setQuantity(0);
            setPrice(0);
            updateButtonDisplay(orderId, 0);
            updateTotal();
        }
    };
}

//合計の更新処理
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

//小計の更新処理
function setPrice(price) {
    document.getElementById(`priceField`).value = price;
}

//個数の更新処理
function setQuantity(count) {
    document.getElementById(`countField`).value = count;
}


