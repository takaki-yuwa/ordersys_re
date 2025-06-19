package servlet;

public class product_list {
	private int id;
	private String name;
	private int price;
	private String category;
	private int stock;

	public product_list(int id, String name, int price, String category, int stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}
	public int getStock() {
		return stock;
	}
}
