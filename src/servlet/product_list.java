package servlet;

public class product_list {
	private int id;
	private String name;
	private int price;
	private String category;
	private int stock;
	private int displayflag;

	public product_list(int id, String name, int price, String category, int stock, int displayflag) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.stock = stock;
		this.displayflag = displayflag;
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

	public int getDisplayflag() {
		return displayflag;
	}
}
