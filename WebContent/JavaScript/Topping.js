/**
 * 
 */
const topping=[
	{name:"コーン(100g)"},
	{name:"カレー(100g)"},
	{name:"チーズ(100g)"},
	{name:"もち(100g)"},
	{name:"ツナ(100g)"},
	{name:"ベビースタ(100g)"},
	{name:"ベビースタースペシャル(100g)"}
];

const toppingList=document.getElementById('topping');
topping.forEach(item =>{
	const li=document.createElement('li');
	li.innerHTML=`<div class="topping-row">
					<div class="break-topping">・${item.name}</div>
					</div>`;
	toppingList.appendChild(li);
})