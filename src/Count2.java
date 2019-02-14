
public class Count2 implements Runnable {

	int counter = 0;
	
	@Override
	public void run() {
		
		for (int i = 0; i < 1000000; i++) {
			counter++;
		}
		System.out.println("Counter 2 is done " + counter);
	}

}
