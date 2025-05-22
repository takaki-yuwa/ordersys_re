package servlet;

public class product_list {
    private String name;
    private int price;
    private String category;

    public product_list(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
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
}
