package a;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*******************************************************************************************
 * @author Ashley Hendrickson
 * @author 
 * @author 
 * Chat+ interface. This is messaging application that allows for the client and server to communicate back and forth
 ******************************************************************************************/
public class GUI extends JFrame implements ActionListener {
	/**send is for sending information and connecting to a client **/
	private static JButton send ;
	/**connect is for connecting to the client **/
	private static JButton connect;
	/**chat this is were the messages will be displayed **/
	static JTextArea chat;
	/**ipaddress is were you can type in the information to connected**/
	private static JTextField ipaddress; 
	/**sent is were you can type in the information to be sent to a target client**/
	private static JTextField sent;
	/** client is represents the other client**/
	private static Client client;
	
	public static void main(String args[]) {
		// Starts the program
		GUI gui = new GUI();
		gui.setTitle("Chat Screen");
		gui.pack();
		gui.setVisible(true);
		gui.setResizable(false);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

/******************************************************************************************* 
 * GUI Interface 
 * initalize all buttons, labels and menu items
 ******************************************************************************************/
	public GUI() {
		//menubar
		JMenuBar menuBar = new JMenuBar();
		//file on menu bar
		JMenu file= new JMenu("FILE");
		//sendfile menu option from file. This is the option to send files 
		JMenuItem sendfile = new JMenuItem("sendfile");
		//help option from file allows user to get help
		JMenuItem help = new JMenuItem("help");
		//this allows the user to play chess
		JMenuItem game = new JMenuItem("play Chess");
		file.add(sendfile);
		file.add(help);
		file.add(game);
		menuBar.add(file);
		
		// creates client classs
		client = new Client();
		//sets up constrains
		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();

		// TEXT AREA
		
		// add the scrollpane to the chat
		chat = new JTextArea(20, 20);
		JScrollPane scrollPane = new JScrollPane(chat);
		loc.gridx = 0;
		loc.gridy = 1;
		loc.gridheight = 10;
		loc.insets.left = 20;
		loc.insets.right = 20;
		loc.insets.bottom = 20;
		add(scrollPane, loc);

		// adds the chat label to the pane
		loc = new GridBagConstraints();
		loc.gridx = 0;
		loc.gridy = 0;
		loc.insets.bottom = 20;
		loc.insets.top = 20;
		add(new JLabel("CHAT"), loc);

		// adds the ipaddress text field to the pane
		ipaddress = new JTextField(15);
		loc.gridx = 2;
		loc.gridy = 3;
		loc.gridwidth = 1;
		add(ipaddress, loc);
		
		ipaddress.addMouseListener((ClickedListener)(e)->{
			if(e.getClickCount()==2) {
				ipaddress.setText("");
			}
		});
		
		// adds the send text field to the pane 
		sent = new JTextField(15);
		loc.gridx = 2;
		loc.gridy = 9;
		loc.gridwidth = 1;
		sent.setText("Enter Message Here!");
		add(sent, loc);
		sent.setEnabled(false);
		
		sent.addMouseListener((ClickedListener)(e)->{
			if(e.getClickCount()==2) {
				sent.setText("");
			}
		});
		// adds the send button to the sends
		send = new JButton("Send Chat");
		loc.gridx = 2;
		loc.gridy = 10;
		loc.gridwidth = 1;
		add(send, loc);
		send.setEnabled(false);
		
		// add connection button to the chat
		connect = new JButton("Connect");
		loc.gridx = 2;
		loc.gridy = 1;
		loc.gridwidth = 1;
		add(connect, loc);
		
		setJMenuBar(menuBar);
		
		//adds an action listener to the send button 
		sendfile.addActionListener(t->{	
			SendfileGUI send = new SendfileGUI(client);
			send.run();
		});
		// adds an action listener to the help button
		
		/******************************************************************************************* 
		 * Anonymous class for help button when clicked create a frame with help information that helps
		 * the user with chat++ 
		 * Initialize all buttons, labels and menu items
		 ******************************************************************************************/
		help.addActionListener(t->{
			try { 
				JFrame instructions = new JFrame();	
				String html="<html><head><title>Simple Page</title></head>"
						+ "<body><hr/><font size=50>Welcome to chat++ this is the help page!!</font><hr/> "
						+ "<p><font size=25> Description: </p>"
						+ "<p>Chat++ is an online chat serivce that allows you to chat locally with your friends"
						+ "its possable to chat with mulitiple people if you on the service."
						+ "Its also possable to send files back and forth accross the network having any extendtions "
						+ ".txt, .docxs, .pdf .jpg. <p/>"
						+ "<br/><p><font size=25>Getting started:</p>"
						+ "<p>In order to uses this you need ensure a few things:<br/><br/>"
						+ "1) Make sure the server is running if the server isn't running then there will be know way to connect to anyone accross the network. "
						+ "Also make sure that only one servers ares running if two people are connected to two servers the "
						+ "chat++ will fail to run and no information will be sent accross the network</p><br/>"
						+ "2) You must be connected to the same nework ensure that you and the server are on the same network chat++"
						+ " is a simple network that works on a simple local area network method.<br/>Ensure these to be true before you continue</p>"
						+ "<p><font size=5> Having the proper Syntax</p>"
						+ "<p>The first step in communcating data is to connected to the server."
						+ "follow the following syntax for connecting to the server<br/><b> &lt Ip address &gt &lt username &gt"
						+ "&lt port number &gt</b> <br/> Then click connect<br/>"
						+ "Sending files: go to file under files click sendfile. Fill out the ip filename and portnumber then click send"
						+ "If that file dosn't exist a pop up box will ask you if you want to write to that file you can write to the file before you send it.</p>"
						+ "<p>IP ADDRESS: If you are on windows open the terminal and type ipconfig your ip "
						+ "address is under IP4 if you are on a unix os type ifconfig and your ip will be under inet"
						+ "<br/>USERNAME: type a user name a user name cannot consist of special characters"
						+ "<br/PORTNUMBER: your port number cannot be the same as anyone else port number and your port number"
						+ "must be between 1023 and 65535 <br/>"
						+ "</p>"
						+ "</body></html>"; 
				// creates another pane for help instructions in html
				JEditorPane ed1=new JEditorPane("text/html",html); 
				instructions.add(ed1); 
				instructions.setVisible(true); 
				instructions.setSize(800,800); 
				} catch(Exception e) { 
					e.printStackTrace();
					System.out.println("Some problem has occured"+e.getMessage()); 
					}
		});
		// ACTIONLISTENERS
		send.addActionListener(this);
		connect.addActionListener(this);
	}
	
	/*******************************************************************************************
	 * JComponent action caused by pressing button for connect and send
	 * This function is called when the send button is invoked 
	 * @param e what happens when the connect button is pressed  
	 * @exception e2 throw in error if the client is unable to send the other client a message
	 * @return void 
	 *******************************************************************************************/
	public void actionPerformed(ActionEvent e) {
		JComponent buttonPressed = (JComponent) e.getSource();

		// Grabs the text from a textfield and calls sendMessage.
		if (buttonPressed == send) {

			// holds message to send to server.
			String msg = sent.getText();
			try {
				client.sendMessage(msg);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			if (msg.equals("QUIT")) {

				// Disabled the connect button and field so you can connect again.
				ipaddress.setEnabled(true);
				connect.setEnabled(true);

				// Allows clients to now send messages.
				sent.setEnabled(false);
				send.setEnabled(false);
			}

		}

		// Try to form a connection using the information in the IPaddress text field.
		else if (buttonPressed == connect) {
			String ip ="",name="",port="";
			try {
			StringTokenizer st = new StringTokenizer(ipaddress.getText());
			ip = st.nextToken();
			name = st.nextToken();
			port = st.nextToken();
			
			try {
				if(client.reachable(ip, name, Integer.parseInt(port))) {
				// Disabled the connect button and field so you can connect again.
				client.makeConnection(ipaddress.getText());
				ipaddress.setEnabled(false);
				connect.setEnabled(false);

				// Allows clients to now send messages.
				sent.setEnabled(true);
				send.setEnabled(true);
				}
				
			} catch (Exception e1) {
				// if the client is unable to reach a server send error 
				JOptionPane.showMessageDialog(new Frame(), "\nServer failed to connect check to see if the server is up");
				e1.printStackTrace();
			}
			}catch(Exception e1) {
				// if the client is unable to reach the file send error
				JOptionPane.showMessageDialog(new Frame(), "\nEnter in all the data. for help go to file>help for formating help \n"+e1.getStackTrace());
			}
		}
	}

}