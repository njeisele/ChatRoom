import java.lang.Thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hi");
		// Just testing out threads
		Count1 counter1 = new Count1();
		Count2 counter2 = new Count2();
		
		Thread t1 = new Thread(counter1);
		Thread t2 = new Thread(counter2);
		
		t1.start();
		t2.start();
		
		t1.join();
		
		System.out.println("Counter 2 is at " + counter2.counter);
		t2.join();
		System.out.println("Counter 2 is at " + counter2.counter);
		return;
	}

}
