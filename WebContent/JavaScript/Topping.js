/**
 * 
 */
const topping = [
    { name: "コーン(100g)", count: 0 },
    { name: "カレー(100g)", count: 0 },
    { name: "チーズ(100g)", count: 0 },
    { name: "もち(100g)", count: 0 },
    { name: "ツナ(100g)", count: 0 },
    { name: "ベビースタ(100g)", count: 0 },
    { name: "ベビースタースペシャル(100g)", count: 0 }
];

const toppingList = document.getElementById('topping');

topping.forEach((item, index) => {
    const li = document.createElement('li');
    li.innerHTML = `
        <div class="topping-row">
            <div class="break-topping">・${item.name}</div>
            <button class="counter-button minus" data-index="${index}">-</button>
            <input type="text" value="${item.count}" class="counter-input" readonly>
            <button class="counter-button plus" data-index="${index}">+</button>
        </div>
    `;
    toppingList.appendChild(li);
});

// イベントリスナーの追加
document.querySelectorAll('.counter-button').forEach(button => {
    button.addEventListener('click', (event) => {
        const index = event.target.getAttribute('data-index');
        const action = event.target.classList.contains('minus') ? '-' : '+';
        updateCount(index, action);
    });
});

function updateCount(index, action) {
    const min = 0;
    const max = 10;
    const item = topping[index];

    if (action === '+' && item.count < max) {
        item.count++;
    } else if (action === '-' && item.count > min) {
        item.count--;
    }

    // 数量の表示を更新
    const input = toppingList.children[index].querySelector('input');
    input.value = item.count;

    // ボタンの状態を更新
    const minusButton = toppingList.children[index].querySelector('.minus');
    const plusButton = toppingList.children[index].querySelector('.plus');

    minusButton.disabled = item.count <= min;
    plusButton.disabled = item.count >= max;
}