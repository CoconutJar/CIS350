
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server {
	private static ServerSocket welcomeSocket;
	public static ArrayList<ClientHandler> con = new ArrayList<ClientHandler>();

	public static void main(String[] args) throws IOException {

		try {
			welcomeSocket = new ServerSocket(3158); // CCPort established
		} catch (Exception e) {
			System.err.println("ERROR: Server could not be started.");
		}

		try {
			while (true) {

				Socket connectionSocket = welcomeSocket.accept();
				System.out.println(connectionSocket.getRemoteSocketAddress().toString() + " has connected!");

				DataInputStream dis = new DataInputStream(connectionSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());

				ClientHandler client = new ClientHandler(connectionSocket, dis, dos);
				con.add(client);
				Thread t = new Thread(client);
				t.start();
			}

		} catch (Exception e) {
			System.err.println("ERROR: Connecting Client");
			e.printStackTrace();

		} finally {
			try {
				welcomeSocket.close();
				System.out.println("Server socket closed.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}

class ClientHandler implements Runnable {

	Socket connectionSocket;
	String fromClient;
	String clientName;
	DataInputStream dis;
	DataOutputStream dos;
	boolean loggedIn;

	public ClientHandler(Socket connectionSocket, DataInputStream dis, DataOutputStream dos) {

		this.connectionSocket = connectionSocket;
		this.dis = dis;
		this.dos = dos;
		this.loggedIn = true;

	}

	@Override
	public void run() {

		String name;
		try {

			name = dis.readUTF();
			this.clientName = name;

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("-- " + clientName + " is ready to chat! --");

		try {
			boolean hasNotQuit = true;

			// Send Msgs
			do {
				fromClient = dis.readUTF();

				System.out.println(fromClient);

				if (fromClient.equals("-list")) {

					dos.writeUTF("Users currently online:");
					for (int i = 0; i < Server.con.size(); i++) {
						if (Server.con.get(i).loggedIn)
							dos.writeUTF(Server.con.get(i).clientName);
					}

				} else if (fromClient.equals("QUIT")) {

					hasNotQuit = false;

				} else if (fromClient.equals("-help")) {

					dos.writeUTF("Enter '-list' to see who's available for messaging.\n "
							+ "To send a message: <message> <recipicant>." + "Enter 'QUIT' to exit.");

				} else {

					// break the string into message and recipient part
					StringTokenizer st = new StringTokenizer(fromClient);
					String message = "";
					int number = st.countTokens() - 1;

					for (int i = 0; i < number; i++) {
						message += st.nextToken() + " ";
					}

					String recipient = st.nextToken();
					boolean found = false;
					for (ClientHandler c : Server.con) {

						if (c.clientName.equals(recipient) && c.loggedIn == true) {
							c.dos.writeUTF(this.clientName + " sent : " + message);
							found = true;
							break;
						}
					}
					if (!found) {
						dos.writeUTF("Cant find user " + recipient
								+ "\nEnter '-list' to see who's available for messaging \n:)");
					}
				}

			} while (hasNotQuit);

			this.loggedIn = false;
			this.connectionSocket.close();
			System.out.println(clientName + " has disconnected!");

		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}
