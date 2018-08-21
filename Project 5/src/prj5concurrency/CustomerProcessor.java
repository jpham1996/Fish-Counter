/* Multiple processors: 
 1.  Executes same instruction simultaneously on different data. 
 2.  One processor controls operation of other processors. 
 3.  Each own local memory.  */

package prj5concurrency;

import java.util.Random;

public class CustomerProcessor extends Thread {
	private final static Random generator = new Random();
	private final FishBuffer buffer;
	private final int numCustomers;
	
	public CustomerProcessor(FishBuffer buffer, int numberOfCustomers) {
		this.buffer = buffer;
		this.numCustomers = numberOfCustomers;
	}

	@Override
	public synchronized void run() {
	
		for(int i = 1; i <= numCustomers; i++) {
			try {
				Thread.sleep(generator.nextInt(1000));						// Delay to simulate the processing of an employee for 1 customer. 
				buffer.get();												// Call the get function of the FishBuffer. 
				System.out.println("Customer " + i + " left the queue");	// Once the get function finishes, the customer is fully processed. 
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Total customers processed: " + numCustomers);
	}
}