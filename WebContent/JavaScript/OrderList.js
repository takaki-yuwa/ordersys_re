/**
 * 
 */
/*function updateOrderState(menuId) {
    var orderstate = { count: ${quantity} };

    // 動的に生成された要素があるか確認
    const incrementbtn = document.getElementById(`increment-${menuId}`);
    const decrementbtn = document.getElementById(`decrement-${menuId}`);
    const counter = document.getElementById(`counter-${menuId}`);
    const subtotal = document.getElementById(`subtotal-${menuId}`);

    // 要素が存在する場合のみイベントを登録
    if (incrementbtn && decrementbtn && counter && subtotal) {
        incrementbtn.addEventListener('click', () => {
            counter.innerHTML = ++orderstate.count;
            subtotal.innerHTML = orderstate.count * ${product_price}; // 個別の価格を使う
        });

        decrementbtn.addEventListener('click', () => {
            if (orderstate.count > 0) {
                counter.innerHTML = --orderstate.count;
                subtotal.innerHTML = orderstate.count * ${product_price};
            }
        });
    } else {
        console.warn(`Element with ID increment-${menuId} or decrement-${menuId} not found.`);
    }
}*/

function updateOrderState(menuId,menuQuantity,menuSubtotal) {
    var orderstate = { count: menuQuantity };

    // 動的に生成された要素があるか確認
    const incrementbtn = document.getElementById(`increment-${menuId}`);
    const decrementbtn = document.getElementById(`decrement-${menuId}`);
    const counter = document.getElementById(`counter-${menuId}`);
    const subtotal = document.getElementById(`subtotal-${menuId}`);

    // 要素が存在する場合のみイベントを登録
    if (incrementbtn && decrementbtn && counter && subtotal && total) {
        incrementbtn.addEventListener('click', () => {
            counter.innerHTML = ++orderstate.count;
            subtotal.innerHTML = orderstate.count * menuSubtotal; // 個別の価格を使う
            
        });

        decrementbtn.addEventListener('click', () => {
            if (orderstate.count > 0) {
                counter.innerHTML = --orderstate.count;
                subtotal.innerHTML = orderstate.count * menuSubtotal;
                
            }
        });
    } else {
        console.warn(`Element with ID increment-${menuId} or decrement-${menuId} not found.`);
    }
}

