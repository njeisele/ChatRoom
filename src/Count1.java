
public class Count1 implements Runnable {

	int counter = 0;
	
	@Override
	public void run() {
		counter++;
		System.out.println("Counter 1 is done: " + counter);
	}

	
}
