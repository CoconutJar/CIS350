import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

//import java.util.StringTokenizer;

public class Client {

	boolean loggedIn = true;
	DataInputStream dis;
	DataOutputStream dos;
	Socket s;
	Scanner scn;

	public void makeConnection(String Name) throws IOException {

		scn = new Scanner(System.in);

		InetAddress ip = InetAddress.getByName("localhost");

		s = new Socket(ip, 3158);

		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());
		loggedIn = true;

		dos.writeUTF(Name);

	}

	public void sendMessage(String message) throws IOException {

		dos.writeUTF(message);

		if (message.equals("QUIT")) {
			s.close();
			scn.close();
			loggedIn = false;
		}

	}

	public DataInputStream getDataInputStream() {
		return this.dis;
	}

	public Socket getSocket() {
		return s;
	}
}
