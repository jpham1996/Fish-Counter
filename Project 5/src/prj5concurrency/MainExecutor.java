/* Choice 2:  Fish Counter
   Console Explanation:  We have managed to implement another thread through the CustomerProcessor class. 
   The program implements multiple threads already because as we know, there is a main thread (which is the MainExecutor), 
   and once CustomerProcessor is called, it is spawning another thread to process the customers. (Notice that it extends 'Thread' on the class). 
   To synchronize resources, we use the synchronized keyword on both get() and set() methods on the FishBuffer because this class holds the resource 'Window' which is the int array of the employees who can serve customers. 
   This means that whenever we use get() or set() functions, 
   it synchronizes with the thread so it can process the data one by one (whichever goes first).  
*/

package prj5concurrency;

import java.util.Random;
import java.util.Scanner;

public class MainExecutor {

	public static void main(String[] args) throws InterruptedException {
		Random generator = new Random();
		
		Scanner reader = new Scanner(System.in); 
		System.out.print("Enter number of customers: ");
		int customers = reader.nextInt();
		System.out.print("Enter number of employees: ");
		int employees = reader.nextInt();
		reader.close();
		
		FishBuffer buffer = new FishBuffer(employees);										// Create the fish buffer 
		CustomerProcessor cProcessor = new CustomerProcessor(buffer, customers);	// Create the processor with 10 customers 
		cProcessor.start();															// Start the processing 
		
		for (int i = 1; i <= customers; i++) {
			buffer.set(i);
			System.out.println("Customer " + i + " entered queue.");
			int delay = generator.nextInt(1000);
			System.out.println("Delaying for " + delay + " ms.");
			Thread.sleep(delay);
		}
		
		
	}

}
