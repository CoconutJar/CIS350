import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

//import java.util.StringTokenizer;

public class Client {
	static boolean loggedIn = true;

	public static void main(String[] args) throws IOException {

		Scanner scn = new Scanner(System.in);

		InetAddress ip = InetAddress.getByName("localhost");

		Socket s = new Socket(ip, 3158);

		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());

		System.out.println("Type a UserName to use in the chat room.");
		String name = scn.nextLine();
		dos.writeUTF(name);
		String sReply = dis.readUTF();
		System.out.println(sReply);

		Thread sendMessage = new Thread(new Runnable() {
			@Override
			public void run() {
				while (loggedIn) {

					// read the message to deliver.
					String msg = scn.nextLine();

					try {
						// write on the output stream
						dos.writeUTF(msg);

						if (msg.equals("QUIT")) {
							s.close();
							scn.close();
							loggedIn = false;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		Thread readMessage = new Thread(new Runnable() {
			@Override
			public void run() {

				while (loggedIn) {
					try {
						// read the message sent to this client
						if (!s.isClosed())
							while (dis.available() > 0) {
								String msg = dis.readUTF();
								System.out.println(msg);
							}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sendMessage.start();
		readMessage.start();
	}
}
