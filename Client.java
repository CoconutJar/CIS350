package a;
import java.awt.Frame;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

/*****************************************************************************
 * The client accepts packets when connected to the server this allows for 
 * network communication between client and server 
 * *****************************************************************************/
public class Client  {
	
	/* 's' is the socket that will be connection to the server */
	private Socket s;
	/* 'dis' data Input Stream allows for the client to receive input */
	private DataInputStream dis;
	/* loggedIN tells the server if the client is logged in or not*/
	boolean loggedIn;
	/* 'data out put stream allows the client to send packets to the the server */
	private DataOutputStream dos;

	/*****************************************************************************
	 * Sends the messages to the server if the client wants the end then connection they must type quit
	 * @param message is a string that is sent to the server from the client if the client types in quit 
	 * then the connection will end
	 * *****************************************************************************/	
	public void sendMessage(String message) throws IOException {
		// Send the message using TCP.
		dos.writeUTF(message);
		// type quit to end the connection allows user to end connection
		if (message.equals("QUIT")) {
			// Closes the connection.
			s.close();
			// the client is now not logged into the servers to so the connection is false 
			loggedIn = false;
		}

	}
	/*****************************************************************************
	This method is used for sending files from one client to another client if the 
	file doesn't exist the file is created with input 
	
	 @param userIP takes in the other clients ip as a string 
	 @param port takes in the other clients port number 
	 @prama filename takes in the filename you wish to transfer
	 @exception IOException throw an exception if the file cannot be sent 
	 @return void
	 *****************************************************************************/
	public void sendFile(String userIP, int port, String filename) throws IOException {
		if(new File(filename).exists()) {
			Socket sock = new Socket(userIP, port);
			DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
			FileInputStream fis = new FileInputStream(filename);
			byte[] buffer = new byte[4096];
			
			while (fis.read(buffer) > 0) {
				dos.write(buffer);
			}
			// closes file input stream and data output stream and socket
			fis.close();
			dos.close();
			sock.close();
		}
		else {
			Files.write(Paths.get(filename),JOptionPane.showInputDialog(new Frame(), "This file is currently empty. Write something to it?").getBytes());
		}
	}
	
	/*****************************************************************************
	 * makes a connection with the client 
	 * @param Name is a string the contains the <ip> <username> <port>
	*****************************************************************************/
	public void makeConnection(String Name) throws IOException {

		// IP of the server to connect to.
		//InetAddress ip = InetAddress.getByName("localhost");
		
		StringTokenizer st = new StringTokenizer(Name);
		String ip = st.nextToken();
		Name = st.nextToken();
		String port = st.nextToken();
		
		
		// Connection to server.
		s = new Socket(ip, 3158);

		// Set up input and output stream to send and receive messages.
		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());

		// Sets logged in status to true.
		loggedIn = true;

		// Sends the UserName of the client to the server.
		dos.writeUTF(Name + " " + port);
		// creates a new thread for reading in input to a file
		// this file program will constantly run in the back group waiting for a file until
		// its closed
		Thread recieveFiles = new Thread(new Runnable() {
			public void run() {
				// server socket this waits for a connection from another client
				ServerSocket sock = null;
				try {
					sock = new ServerSocket(Integer.parseInt(port));
				
				} catch (NumberFormatException e1) {
				
					e1.printStackTrace();
				} catch (IOException e1) {
				
					e1.printStackTrace();
				}

				// If the client is loggedOff they wont receive any messages.
				while (loggedIn) {

					// If the socket is still open.
					// Read the message sent to this client.
					try {
						Socket s = sock.accept();
							
						try {
							// reads all the data send from the client
							DataInputStream datainput = new DataInputStream(s.getInputStream());
							// sends all the data from the client
							FileOutputStream fos = new FileOutputStream("file");
							byte[] buffer = new byte[4096];
							int filesize = 15123; // Send file size in separate msg
							int read = 0;
							int totalRead = 0;
							int remaining = filesize;
							while((read = datainput.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
								totalRead += read;
								remaining -= read;
								System.out.println("read " + totalRead + " bytes.");
								fos.write(buffer, 0, read);
							}
							
							fos.close();
							datainput.close();
							s.close();
						}
						// if the file failes to be created throw an error
						catch(IOException e) {
							e.printStackTrace();
						}
						// if the socket fails throw an error
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

		// Starts the thread.
		recieveFiles.start();
		
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
						}// if the data fails to be read throw an error
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

		// Starts the thread.
		recieveMessages.start();
	}
	/*****************************************************************************
	 Test the validity of a connection test weather a program can connect or not 
	 @param ip String that holds the value of an ip address
	 @param name holds the value of the name or file name
	 @param port number and test if its valid or not 
	 * *****************************************************************************/
	public boolean reachable(String ip, String name, int port) {
		// test if the port its in between 1023 and 65535 
		if(port<1023||port>65535) {
			JOptionPane.showMessageDialog(new Frame(), "invalid port ports must be between 1023 and 65535");
			return false;}
				try { 
			// test if the client can connect to the network if the client can't then return false and say 
			// the connection failed
			if(InetAddress.getByName(ip).isReachable(5000)) {
			}else {
			// ip address failed to connect
			JOptionPane.showMessageDialog(new Frame(), "destination host unreachable.");
			return false;
			} 
			// if the ip isn't formated properly show error to users
				}catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new Frame(), "FORMATTING ERROR. Enter: Format ERROR \n"+e.getStackTrace());
			return false;	
		}
				return true;
				}
	}