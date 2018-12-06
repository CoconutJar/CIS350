package a;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/*******************************************************************************************
 * Sets up the file portion of the GUI for sending files back and forth between client and 
 * server. 
 *******************************************************************************************/
public class SendfileGUI{
/* Client represents the other client you wish to send information to */
	private Client client;

/*******************************************************************************************
 * Constructor for sendfileGUI takes in the target client
 * @param Client sets the target client  
 *******************************************************************************************/
	
  public SendfileGUI(Client client) {
	  this.client = client;
  }
  
  /*******************************************************************************************
   * Sets up the file sending gui 
   *******************************************************************************************/
  public void run() {
	 JFrame f = new JFrame();
	 JPanel panel = new JPanel();
	 f.getContentPane().add(panel);
	 f.setSize(400, 300);
	 f.setVisible(true);
	 // sets up the constraints
    GridBagConstraints constraints = new GridBagConstraints();
    // sets the layout scheme
    GridBagLayout layout = new GridBagLayout();
    panel.setLayout(layout);
    constraints.insets= new Insets(2,2,2,2);
    constraints.anchor = GridBagConstraints.WEST;
    constraints.gridy = 0;
    JLabel label = new JLabel("Ip address: ");
    panel.add(label, constraints);
    
    // sets the ip text filed
    JTextField tf1 = new JTextField(20);
    tf1.setText("localhost");
    /*******************************************************************************************
     * Anonymous class for adding to the mouse
     * @param e action for text filed if set text screen is double tapped the text is erased 
     ****************************************************************************************** */
    tf1.addMouseListener((ClickedListener)(e)->{
    	if(e.getClickCount()==2) {
			tf1.setText("");
		}
    });
        panel.add(tf1, constraints);
    

    constraints.gridy = 1;
    label = new JLabel("port");
    panel.add(label, constraints);
    // port text field 
    JTextField tf2 = new JTextField(20);
    
    /*******************************************************************************************
     * Anonymous class for adding action to the file text field
     * @param e action for text filed if text field is double tapped the text is erased 
     ****************************************************************************************** */
    tf2.addMouseListener((ClickedListener)(e)->{
    	if(e.getClickCount()==2) {
			tf2.setText("");
		}
	
    });
    panel.add(tf2, constraints);
    
    constraints.gridy=2;
    label = new JLabel("file");
    panel.add(label, constraints);
    
    
    /***********************************************************************************************
     * Anonymous class for adding in action to the port text field
     * @param e action for text filed if text field is double tapped the text is erased
     **********************************************************************************************/
    JTextField tf3 = new JTextField(20);
    tf3.addMouseListener((ClickedListener)(e)->{
    	if(e.getClickCount()==2) {
			tf3.setText("");
		}
    });
    
    panel.add(tf3, constraints);
    
    constraints.gridy=3;
    constraints.gridx=1;
    JButton connect = new JButton("connect");
      panel.add(connect, constraints);

      /***********************************************************************************************
       * The action for the connect field when the connect button is pressed action will take place
       * @exceptions e throw exception if the data entered is invalid 
       **********************************************************************************************/
      connect.addActionListener(t->{
    	  try {
    	  String ip = tf1.getText();
    	  int port=Integer.parseInt(tf2.getText());
    	  String file=tf3.getText();
    	 
    	if(client.reachable(ip, file, port)) {	
			client.sendFile(ip, port, file);
			}
    	else return;
    		// if text is invalid throw error 
    	  } catch (Exception e) {
    		  e.printStackTrace();
    		  JOptionPane.showMessageDialog(new Frame(), "process failed, the data isn't valied");
    	  }
    	  
      });

  }
} 
