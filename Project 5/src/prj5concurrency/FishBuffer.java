package prj5concurrency;

public class FishBuffer {
	
	private int occupied = 0;							// Number of occupied employee windows 
	private int eWrite = 0; 							// Index used for writing to buffer 
	private int eRead = 0;								// Index used for reading to buffer 
	private int[] Window;								// Array used for employee windows 
	
	public FishBuffer(int employees) {
		Window = new int[employees];
		for (int i = 0; i < employees; i++) {
			Window[i] = -1;
		}
	}
	
	/*
	 *	This function waits for an available employee window to be free.
	 *  Once the employee window is free, the customer will be able to proceed for processing.
	 */
	public synchronized int get() {
		while (occupied == 0) {
			System.out.println("Employees waiting...");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int val = Window[eRead];
		eRead = (eRead + 1) % Window.length;
		occupied--; 
		System.out.println("Serving customer " + val + " [Windows occupied: " + occupied + "]");
		Output();
		notifyAll();
		return val;
	}
	
	/*
	 *	This function adds customers to the line.
	 *  If the employee windows are occupied or full, customers should wait. 
	 */
	public synchronized void set(int value) {
		while (Window.length == occupied) {
			System.out.println("Customers waiting...");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
		Window[eWrite] = value;
		eWrite = (eWrite + 1) % Window.length;
		occupied++;
		System.out.println("Customer " + value + " lines up. [Windows occupied: " + occupied + "]");
		Output();
		notifyAll();
	}
	
	/*
	 *	This function outputs the status of the employee windows.  
	 */
	public void Output() {
		System.out.println();
		for (int i = 1; i <= Window.length; i++) {
			System.out.print("Window " + i + ": " + (Window[i - 1] == -1 ? "?" : Window[i - 1]) + " ");
			if (Window[i - 1] != -1) {
				if ((i - 1 == eWrite) && (i - 1 == eRead)) {
					System.out.print("(Customer going to be processed)");
				} else if (i - 1 == eWrite) {
					System.out.print("(Customer queued)");
				} else if (i - 1 == eRead) {
					System.out.print("(Customer served)");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
