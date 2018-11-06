
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*******************************************************************************************
 * 
 * Chat+ interface.
 * 
 ******************************************************************************************/
public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// buttons
	JButton iPaddress, send, connect;
	// text field
	JTextArea chat;
	JTextField ipaddress, sent;
	JLabel ipaddressLabel;
	Socket s;
	DataInputStream dis;
	boolean loggedIn;
	DataOutputStream dos;

	/****
	 * 
	 * Starts up the GUI
	 * 
	 ****/
	public static void main(String args[]) {
		GUI gui = new GUI();
		gui.setTitle("Chat Screen");
		gui.pack();
		gui.setVisible(true);
	}

	/****
	 * 
	 * Sets up the User Interface.
	 * 
	 ****/
	public GUI() {

		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();

		// TEXT AREA
		chat = new JTextArea(20, 20);
		JScrollPane scrollPane = new JScrollPane(chat);
		loc.gridx = 0;
		loc.gridy = 1;
		loc.gridheight = 10;
		loc.insets.left = 20;
		loc.insets.right = 20;
		loc.insets.bottom = 20;
		add(scrollPane, loc);

		// IPADDRESS TEXT LABEL
		loc = new GridBagConstraints();
		loc.gridx = 0;
		loc.gridy = 0;
		loc.insets.bottom = 20;
		loc.insets.top = 20;
		add(new JLabel("CHAT"), loc);

		// TEXTFIELD
		ipaddress = new JTextField(15);
		loc.gridx = 2;
		loc.gridy = 3;
		loc.gridwidth = 1;
		add(ipaddress, loc);

		sent = new JTextField(15);
		loc.gridx = 2;
		loc.gridy = 9;
		loc.gridwidth = 1;
		sent.setText("Enter Message Here!");
		add(sent, loc);
		sent.setEnabled(false);

		// BUTTONS
		send = new JButton("Send Chat");
		loc.gridx = 2;
		loc.gridy = 10;
		loc.gridwidth = 1;
		add(send, loc);
		send.setEnabled(false);

		connect = new JButton("Connect");
		loc.gridx = 2;
		loc.gridy = 1;
		loc.gridwidth = 1;
		add(connect, loc);

		// ACTIONLISTENERS
		send.addActionListener(this);
		connect.addActionListener(this);
	}

	/****
	 * 
	 * When a user clicks a button the actionPerformed method is called with a
	 * parameter of the component clicked.
	 * 
	 ****/
	public void actionPerformed(ActionEvent e) {
		JComponent buttonPressed = (JComponent) e.getSource();

		// Grabs the text from a textfield and calls sendMessage.
		if (buttonPressed == send) {

			// holds message to send to server.
			String msg = sent.getText();
			try {
				sendMessage(msg);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		}

		// Try to form a connection using the information in the IPaddress text field.
		else if (buttonPressed == connect) {
			try {
				makeConnection(ipaddress.getText());
			} catch (IOException e1) {

			}

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
									chat.setText(chatText);
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

		// Disabled the connect button and field so you can connect again.
		ipaddress.setEnabled(false);
		connect.setEnabled(false);

		// Allows clients to now send messages.
		sent.setEnabled(true);
		send.setEnabled(true);

	}

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

			// Disables sending messages.
			send.setEnabled(false);
			sent.setEnabled(false);

			// Enables connecting to a server.
			connect.setEnabled(true);
			ipaddress.setEnabled(true);
		}

	}

}
