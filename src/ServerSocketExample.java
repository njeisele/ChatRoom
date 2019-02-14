import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerSocketExample {
	
    // The server will store the last message sent 
    
    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException{
        //create the socket server object
    	
    	 HashMap<String, ArrayList<ObjectInputStream>> idsToInputs = new HashMap<String, ArrayList<ObjectInputStream>>();
    	 HashMap<String, ArrayList<ObjectOutputStream>> idsToOutputs = new HashMap<String, ArrayList<ObjectOutputStream>>();
    	 HashMap<String, String> idsToNames = new HashMap<String, String>();
    	    
    	ClientAdder clientAdder = new ClientAdder(idsToInputs, idsToOutputs, idsToNames);
    	Thread clientAddingThread = new Thread(clientAdder);
    	System.out.println("Launching the server at IP: " + InetAddress.getLocalHost().getHostAddress() + " on port 9876");
        clientAddingThread.start();
      
    }
}
