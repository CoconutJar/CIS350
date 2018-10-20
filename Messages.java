package Messaging;
public class Messages {
public static void main(String args[]) {
	
	Client net = new Client();

	net.connect("localhost", 6666);
	//net.send("test5");
	net.message();
	
	
}
}
