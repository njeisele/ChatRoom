import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServerReader implements Runnable {
	
	// Waits for messages from a given client
	ObjectInputStream ois; // The input stream for this client
	String clientId, roomId; // The id of this client and room
	ArrayList<ObjectOutputStream> outStreams; // The out streams in this chat room
	
	public ServerReader(ObjectInputStream ois, String clientId, String roomId, ArrayList<ObjectOutputStream> outStreams) {
		this.ois = ois;
		this.roomId = roomId;
		this.clientId = clientId;
		this.outStreams = outStreams;
	}

	@Override
	public void run() {
		while (true) {
			// Wait for a message from this client and then write it to everyone in the chat room
			try {
				String message = (String) ois.readObject();
				// Write this message out to all users in this chat room
				for (ObjectOutputStream oos : outStreams) {
					oos.writeObject(clientId + ": " + message);
				} 
			} catch (ClassNotFoundException | IOException e) {
				// The client must have disconnected
				// This server reader can die
				return;
			} 
			try {
				Thread.sleep(100); // Wait a bit
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
