package exam6;

import java.util.HashMap;
import java.util.Map;

public class StocksDB {

	private final static Map<String, Stock> stocks = new HashMap<>() {
		{
			put(StockName.DOODLE.name(), new Stock(StockName.DOODLE.name(), 3.72, 3.6));
			put(StockName.BARVAZON.name(), new Stock(StockName.BARVAZON.name(), 1.25, 1));
			put(StockName.HEADBOOK.name(), new Stock(StockName.HEADBOOK.name(), 9.52, 9.33));
		}
	};

	public static Stock getStockByName(String name) throws IllegalArgumentException{
		Stock stock = stocks.get(name.toUpperCase());
		if(stock==null)
			throw new IllegalArgumentException("Stock is not found in DB");
		return stock;
	}
	
	public static void printStocksInfo() {
		System.out.println("\nStocks Info:");
		System.out.println(StocksDB.getStockByName("doodle"));
		System.out.println(StocksDB.getStockByName("barvazon"));
		System.out.println(StocksDB.getStockByName("headbook"));

	}
}
