package a;

import java.io.IOException;

public class runServer {
	public static void main(String args[]) {
		try {
			Server s = new Server();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
