/**
 * 
 */
const pricing = [
	{ id: 1, name: "納豆お好み焼き", price: "1000" },
	{ id: 2, name: "桜えびお好み焼き", price: "1000" },
	{ id: 3, name: "ベビースターお好み焼き", price: "1000" },
	{ id: 4, name: "ピザ風お好み焼き", price: "1000" },
	{ id: 5, name: "スペシャルお好み焼き納豆桜えびベビースターデラックス", price: "1000" },
	{ id: 6, name: "海鮮桜えびベビースター", price: "1000" },
	{ id: 7, name: "チーズポテトお好み焼き", price: "1000" },
	{ id: 8, name: "桜えびお好み焼き", price: "1000" },
];

const pricingList = document.getElementById('price');
pricing.forEach(item => {
	const li = document.createElement('li');
	li.innerHTML = `
        <div class="menu-row">
            <div class="break-word bold-text">${item.name}</div>
            <button onclick="submitProductDetails(${item.id}, '${item.name}', '${item.price}')">
                <img src="Image/plusButton.png" alt="商品詳細画面へ遷移する" class="product-shift-button">
            </button>
        </div>
        <p>${item.price}円(税込)</p>
    `;
	pricingList.appendChild(li);
});

function submitProductDetails(id, name, price) {
	const form = document.createElement('form');
	form.method = 'POST';
	form.action = 'ProductDetails';

	const fields = [
		{ name: 'id', value: id },
		{ name: 'name', value: name },
		{ name: 'price', value: price }
	];

	fields.forEach(field => {
		const input = document.createElement('input');
		input.type = 'hidden';
		input.name = field.name;
		input.value = field.value;
		form.appendChild(input);
	});

	document.body.appendChild(form);
	form.submit();
}

