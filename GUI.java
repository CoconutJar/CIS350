package Messaging;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class GUI extends JFrame implements ActionListener {
// buttons
JButton IPaddress, Send, Connect;
// text field
JTextArea Chat;
JTextField ipaddress, sent;
JLabel Ipaddress;

public static void main (String args[]) {
	GUI gui = new GUI();
	gui.setTitle("Chat Screen");
	gui.pack();
	gui.setVisible(true);
}
public GUI() {
	setLayout(new GridBagLayout());
	GridBagConstraints loc = new GridBagConstraints();
	// TEXT AREA
	Chat = new JTextArea(20,20);
	 JScrollPane scrollPane = new JScrollPane(Chat);
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
     add(ipaddress,loc);
     sent = new JTextField(15);
     loc.gridx = 2;
     loc.gridy = 9;
     loc.gridwidth = 1;
     add(sent,loc);
     //BUTTONS
     IPaddress = new JButton (" Send IPaddress");
     loc.gridx = 2;
     loc.gridy = 4;
     loc.gridwidth = 1;
     add(IPaddress,loc);
     Send = new JButton ("Send Chat");
     loc.gridx = 2;
     loc.gridy = 10;
     loc.gridwidth = 1;
     add(Send,loc);
     Connect = new JButton("Connect");
     loc.gridx = 2;
     loc.gridy = 1;
     loc.gridwidth = 1;
     add(Connect,loc);
     // ACTIONLISTENERS
     Send.addActionListener(this);
     IPaddress.addActionListener(this);     
}
public void actionPerformed (ActionEvent e) {
	JComponent buttonPressed = (JComponent) e.getSource();
	
	if(buttonPressed == Send)
		displaychat();
	else if(buttonPressed == IPaddress)
		displayIPaddress();
	else if(buttonPressed == Connect)
		runcode();
}		
private void displaychat()
{
	String str = sent.getText();
	if (str.length() == 0)
	{
		JOptionPane.showMessageDialog(this, "Type a message");
	}
	else 
		Chat.append(str);
}
private void displayIPaddress() 
{

}
private void runcode()
{
	
}
     
     
     
}
