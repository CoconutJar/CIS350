package Messaging;

public class ServerMessaging {
	public static void main(String args[]) {
		Server net = new Server();
		net.openPort(6666);
		net.openPort(6655);
		
		net.accept("s");

		net.message();
	}
}
