package servlet;

public class topping_list {
	private int id;
	private String name;
	private int price;
	private int stock;
	private int displayflag;

	public topping_list(int id, String name, int price, int stock, int displayflag) {
		this.id = id;
		this.name = name;
		this.price = price;
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

	public int getStock() {
		return stock;
	}

	public int getDisplayflag() {
		return displayflag;
	}
}
