package servlet;

public class topping_list {
	private int id;
	private String name;
    private int price;
    private int stock;

    public topping_list(int id, String name, int price, int stock) {
    	this.id = id;
    	this.name = name;
        this.price = price;
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
    
    public int getStock() {
        return stock;
    }
}
