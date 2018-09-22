
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class GUI {
//Note: Typically the main method will be in a
//separate class. As this is a simple one class
//example it's all in the one class.
public static void main(String[] args) {
	new GUI();
}

public GUI(){
	JFrame guiFrame = new JFrame();
	//make sure the program exits when the frame closes
	guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	guiFrame.setTitle("Example GUI");
	guiFrame.setSize(300,250);
	//This will center the JFrame in the middle of the screen
	guiFrame.setLocationRelativeTo(null);
	JTextField textField = new JTextField(20);
}
}