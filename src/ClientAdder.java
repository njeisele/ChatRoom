import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

// A class that waits for new clients

public class ClientAdder implements Runnable{

	private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;
    
    HashMap<String, ArrayList<ObjectInputStream>> idsToInputs;
    HashMap<String, ArrayList<ObjectOutputStream>> idsToOutputs;
    HashMap<String, String> idsToNames;
    List<Thread> readers;
    
	public ClientAdder(HashMap<String, ArrayList<ObjectInputStream>> idsToInputs, 
					   HashMap<String, ArrayList<ObjectOutputStream>> idsToOutputs,
					   HashMap<String, String> idsToNames) throws IOException {
		server = new ServerSocket(port);
		this.idsToInputs =  idsToInputs;
		this.idsToOutputs = idsToOutputs;
		this.idsToNames = idsToNames;
		readers = new ArrayList<Thread>();
	}
	
	@Override
	public void run() {
		// Wait for clients, accept them and add their streams to the maps
		while (true) {
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (IOException e) {
				System.out.println("Error accepting");
				return;
			} 
			
			// The first message from the client should be the ID and display name
			// We'll read this before putting the streams in the map so the other thread doesnt
			// catch them.
			ObjectInputStream ois = null;
			String message = "";
			try {
				ois = new ObjectInputStream(socket.getInputStream());
	            //convert ObjectInputStream object to String
	            message = (String) ois.readObject();
			} catch (IOException | ClassNotFoundException e1) {
				System.out.println("Error reading from the client");
			}
            
            // Get the initial message from this client. It should be a ID and a display name
            String[] contents = message.split(",");
   
            String id = "";
            String name = "";
            try {
            id = contents[0];
            name = contents[1];
            idsToNames.put(id,  name);
            } catch (Exception e) {
            	System.out.println("Error with clients input for id and name");
            	continue;
            }
            //create ObjectOutputStream object
            ObjectOutputStream oos = null;
            try {
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("Error initializing output stream");
			}
            
            // Add to the correct hashmap
            ArrayList<ObjectInputStream> inputStreams = idsToInputs.get(id);
            ArrayList<ObjectOutputStream> outputStreams = idsToOutputs.get(id);
            if (inputStreams == null && outputStreams == null) {
            	// No streams for this ID yet, add this one
            	inputStreams = new ArrayList<ObjectInputStream>();
            	outputStreams = new ArrayList<ObjectOutputStream>();
            	inputStreams.add(ois);
            	outputStreams.add(oos);
            	idsToInputs.put(id, inputStreams);
            	idsToOutputs.put(id, outputStreams);
            } else {
            	inputStreams.add(ois);
            	outputStreams.add(oos);
            }
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            // TODO: Make a server reader thread for this user!
            ServerReader currentReader = new ServerReader(ois, name, id, outputStreams);
            Thread currentThread = new Thread(currentReader);
            readers.add(currentThread);
            currentThread.start(); // This never gets joined
            
            System.out.println(name + " has been added to the chat room: " + id);
		}	
	}

	
}
