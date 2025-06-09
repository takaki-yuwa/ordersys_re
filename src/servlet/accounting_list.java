package servlet;

public class accounting_list {
	private String tableNo;
	private String totalPrice;
	
	public accounting_list(String strTableNo, String strTotalPrice) {
		this.tableNo=strTableNo;
		this.totalPrice=strTotalPrice;
	}
	
	public String getTableNo() {
		return tableNo;
	}
	
	public String getTotalPrice() {
		return totalPrice;
	}
	
}
