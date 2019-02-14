import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This class implements java socket client
 * @author pankaj
 *
 */
public class SocketClientExample {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        
        // A client will have a specific ID for a chat room they wish to enter
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the ID of the chat room you wish to enter.");
        String id = scan.nextLine();
        System.out.println("Enter your desired display name.");
        String name = scan.nextLine();
        
        // Make a socket with the server
        socket = new Socket(host.getHostName(), 9876);
        // We write this to the server
        oos = new ObjectOutputStream(socket.getOutputStream());
        // Write to server
        oos.writeObject(id + "," + name);
        
        ois = new ObjectInputStream(socket.getInputStream());
        
        ClientReader cr = new ClientReader(oos, scan);
        Thread t1 = new Thread(cr);
        t1.start(); // Start listening for the user to type things
        
        // Client now recieves messages
        while (true) {
        	// Check if the server has a message for this client
        	String message = (String) ois.readObject();
        	System.out.println(message);
        	Thread.sleep(100);
        }
        
        /*ois.close();
        oos.close();
        socket.close();
        scan.close();*/
        

    }
}