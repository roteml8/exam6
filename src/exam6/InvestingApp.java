package exam6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import exam6.Command.Operation;

public class InvestingApp {
	
	private static List<Command> transactions = new ArrayList<>();
	private static double totalFunds = 0.0;
	
	private static final int NUM_THREADS = 3;
	private static final double BUY_INCREASE = 0.02;
	private static final double SELL_DECREASE = 0.01;
	private static final int NUM_TRANSACTIONS = 10;
	
	public static void initTransactionsList()
	{
		System.out.println("Initializing transactions list...");
		for (int i=0; i<NUM_TRANSACTIONS; i++)
		{
			Operation operation = i%2 == 0 ? Operation.BUY : Operation.SELL;
			String stockName = i%3 == 0 ? StockName.BARVAZON.toString() : 
				i%3 == 1 ? StockName.DOODLE.toString() : StockName.HEADBOOK.toString();
			Command command = new Command(stockName, operation);
			transactions.add(command);
			System.out.println(command);
		}
		Command illegalCommand = new Command("Solaredge", Operation.BUY);
		transactions.add(illegalCommand);
	}

	public static void printTotalTransactionsValue()
	{
		System.out.println("\nTotal transactions funds: "+totalFunds);
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		initTransactionsList();
		StocksDB.printStocksInfo();
		printTotalTransactionsValue();
		
		ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
		List<Future<Double>> futures = new ArrayList<Future<Double>>();
		
		System.out.println("\nHandling transactions...");
		for (Command command: transactions)
		{
			futures.add(executorService.submit(new CommandCallable(command))); 

		}
		
		for (Future<Double> future : futures) {
			try {
				totalFunds += future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		executorService.shutdown();
		executorService.awaitTermination(2, TimeUnit.SECONDS);
		
		StocksDB.printStocksInfo();
		printTotalTransactionsValue();
		
	}
	
	
	static class CommandCallable implements Callable<Double>
	{
		
		private Command command;
		
		public CommandCallable(Command command)
		{
			this.command = command;
		}

		@Override
		public Double call() throws Exception {
			
			double commandValue = 0;
			try {
			Stock commandStock = StocksDB.getStockByName(command.stockName);
			if (command.operation == Operation.BUY)
			{
				commandValue = commandStock.getBuyPrice();
				commandStock.addToBuyPrice(BUY_INCREASE);
			}
			else 
			{
				commandValue = commandStock.getSellPrice();
				commandStock.decreaseFromSellPrice(SELL_DECREASE);
			}
			System.out.println("Transaction completed successfully.");
			return commandValue;
			}
			catch (IllegalArgumentException e)
			{
				System.out.println("Illegal stock name for transaction - no change to DB.");
				return 0.0; // no change to total funds value
			}
		}
	}
	
}
