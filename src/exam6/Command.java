package exam6;

public class Command {

	String stockName;
	Operation operation;
	
	public Command(String stockName, Operation operation) {
		this.stockName = stockName;
		this.operation = operation;
	}
	
	public static enum Operation{
		BUY, SELL;
	}
}
