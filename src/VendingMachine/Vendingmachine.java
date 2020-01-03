package VendingMachine;

public class Vendingmachine {
	//Variables for the vending machine
	private String item; 
	private int costPrice; 
	private int quantity;
	private int sellPrice;
	private int totalCredits;
	
	public Vendingmachine() { 
		 
	}

	//Getters and setters for items, price and quantities
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getTotalCredits() {
		return totalCredits;
	}

	public void addTotalCredits(int totalCredits) {
		this.totalCredits += totalCredits;
	}
	
	public void minusTotalCredits(int totalCredits) {
		this.totalCredits -= totalCredits;
	}
	

}
