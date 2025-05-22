package servlet;

public class product_list {

    private String name;
    private int price;
    private String category;
	private int id;

    public product_list(String name, int price, String category, int id) {

        this.name = name;
        this.price = price;
        this.category = category;
    	this.id=id;
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
    

    public int getId() {
    	return id;
    }
    
}
