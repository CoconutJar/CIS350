
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

	public static void main(String args[]) {
		GUI gui = new GUI();
		gui.setTitle("Chat Screen");
		gui.pack();
		gui.setVisible(true);
	}

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

	public void actionPerformed(ActionEvent e) {
		JComponent buttonPressed = (JComponent) e.getSource();

		if (buttonPressed == send) {

			String msg = sent.getText();
			try {
				sendMessage(msg);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		}

		else if (buttonPressed == iPaddress) {

			displayIPaddress();

		}

		else if (buttonPressed == connect) {
			try {
				makeConnection(ipaddress.getText());
			} catch (IOException e1) {
			}

			Thread recieveMessages = new Thread(new Runnable() {
				@Override
				public void run() {
					String chatText = "";
					while (loggedIn) {
						try {
							// read the message sent to this client
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

			recieveMessages.start();
		}
	}

	private void displayIPaddress() {

	}

	public void makeConnection(String Name) throws IOException {

		InetAddress ip = InetAddress.getByName("localhost");

		s = new Socket(ip, 3158);

		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());

		loggedIn = true;

		dos.writeUTF(Name);

		ipaddress.setEnabled(false);
		connect.setEnabled(false);
		sent.setEnabled(true);
		send.setEnabled(true);

	}

	public void sendMessage(String message) throws IOException {

		dos.writeUTF(message);

		if (message.equals("QUIT")) {
			s.close();
			loggedIn = false;
			connect.setEnabled(true);
			ipaddress.setEnabled(true);
			send.setEnabled(false);
			sent.setEnabled(false);
		}

	}

}
