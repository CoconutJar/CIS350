import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
public static void main(String[] args) {
		
	
	try {
		
		ServerSocket server = new ServerSocket(6666);
		System.out.println("Waiting for a connection...");
		
		Socket stock = server.accept();
		DataOutputStream dout = new DataOutputStream(stock.getOutputStream());
		System.out.println("Connected to "+stock.getRemoteSocketAddress().toString());
		DataInputStream dis= new DataInputStream(stock.getInputStream());
		
		
		System.out.println("Press q to exit the program!");
		Scanner sc = new Scanner(System.in);
		String word= " ";
		
		while(!word.equals("q")) {
			
			word = sc.nextLine();
			dout.writeUTF(word);
			System.out.println(dis.readUTF());
			dout.flush();
			
		}
		
		dout.close();
		server.close();
	}catch(Exception e) {System.out.println(e);}
}
}