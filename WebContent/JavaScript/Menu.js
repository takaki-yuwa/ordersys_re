/**
 * 
 */
const pricing=[
	{name:"納豆お好み焼き",price:"1,000円(税込)"},
	{name:"桜えびお好み焼き",price:"1,000円(税込)"},
	{name:"ベビースターお好み焼き",price:"1,000円(税込)"},
	{name:"ピザ風お好み焼き",price:"1,000円(税込)"},
	{name:"スペシャルお好み焼き納豆桜えびベビースターデラックス",price:"1,000円(税込)"},
	{name:"海鮮桜えびベビースター",price:"1,000円(税込)"},
	{name:"チーズポテトお好み焼き",price:"1,000円(税込)"},
	{name:"桜えびお好み焼き",price:"1,000円(税込)"},
];

const pricingList=document.getElementById('price');
pricing.forEach(item =>{
	const li=document.createElement('li');
	li.innerHTML=`<div class="menu-row">
					<div class="break-word bold-text">${item.name}</div>
					<a href="ProductDetails.html" class="view-details">
					<img src="Image/plusButton.png" alt="商品詳細画面へ遷移する" class="product-shift-button">
					</a>
					</div>
					<p>${item.price}</p>`;
	pricingList.appendChild(li);
});


