package exam6;

public class Stock {

	private String name;
	private double buyPrice;
	private double sellPrice;
	public Stock(String name, double buyPrice, double sellPrice) {
		setName(name);
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
	}
	private void setName(String name) {
		if(StockName.valueOf(name.toUpperCase()) != null) { //throws IllegalArgumentException
			this.name=name;
		}
	}
	
	
	@Override
	public String toString() {
		String description = String.format("Stock name: %s, Buy price: %.2f, Sell price: %.2f", name, buyPrice, sellPrice);
		return description;
	}
	
//	public static void main(String[] args) {
//		Stock stock = new Stock("doodle", 3.0, 4.0);
//		System.out.println(stock);
//	}
	

	public void addToBuyPrice(double addition) {
		this.buyPrice += addition;
	}

	public void decreaseFromSellPrice(double decrease) {
		this.sellPrice -= decrease;
	}
	public double getBuyPrice() {
		return buyPrice;
	}

	public double getSellPrice() {
		return sellPrice;
	}


	
}
