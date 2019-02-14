import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientReader implements Runnable {

	ObjectOutputStream oos; 
	Scanner scan;
	// Output stream
	ClientReader(ObjectOutputStream oos, Scanner sn) {
		this.oos = oos;
		this.scan = sn;
	}
	
	// Reads from the user input and sends this to the server
	@Override
	public void run() {
		while (true) {
			String userMessage = scan.nextLine();
			if (userMessage.contentEquals("exit")) {
	    		break;
	    	}
			try {
				oos.writeObject(userMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
