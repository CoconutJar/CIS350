import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	
	Socket s;
	DataInputStream dis;
	boolean loggedIn;
	DataOutputStream dos;
	Client client;
	
	
	/****
	 * 
	 * Sends the messages to the server using the output stream. If the message is
	 * 'QUIT' the client disconnects from the server.
	 * 
	 ****/
	public void sendMessage(String message) throws IOException {

		// Send the message using TCP.
		dos.writeUTF(message);

		if (message.equals("QUIT")) {

			// Closes the connection.
			s.close();
			loggedIn = false;
		}

	}
	
	/****
	 * 
	 * Forms the TCP socket connection to the server in order to receive and send
	 * messages.
	 * 
	 ****/
	public void makeConnection(String Name) throws IOException {

		// IP of the server to connect to.
		InetAddress ip = InetAddress.getByName("localhost");

		// Connection to server.
		s = new Socket(ip, 3158);

		// Set up input and output stream to send and receive messages.
		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());

		// Sets logged in status to true.
		loggedIn = true;

		// Sends the UserName of the client to the server.
		dos.writeUTF(Name);
		
		// Responsible for reading in any data from the server input stream.
		// Adds any text received to the chatText box.
		Thread recieveMessages = new Thread(new Runnable() {
			@Override
			public void run() {

				// Will hold all messages received from server.
				String chatText = "";

				// If the client is loggedOff they wont receive any messages.
				while (loggedIn) {

					// If the socket is still open.
					// Read the message sent to this client.
					try {
						if (!s.isClosed()) {
							while (dis.available() > 0) {
								String msg = dis.readUTF();
								chatText += msg + "\n";
								
								// Update the chat in the GUI.
								GUI.chat.setText(chatText);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

		// Starts the thread.
		recieveMessages.start();

	}
}
