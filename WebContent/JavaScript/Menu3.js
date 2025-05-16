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
            <a href="ProductDetails?id=${item.id}&name=${encodeURIComponent(item.name)}&price=${encodeURIComponent(item.price)}" class="view-details">
                <img src="Image/plusButton.png" alt="商品詳細画面へ遷移する" class="product-shift-button">
            </a>
        </div>
        <p>${item.price}円(税込)</p>
    `;
    pricingList.appendChild(li);
});
