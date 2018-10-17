package Messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Network  {
	
	public void openPort(int port) {
		try {
			server = new ServerSocket(port);
			System.out.print("Waiting for a connection...");
			 socket =server.accept();
			dout = new DataOutputStream(socket.getOutputStream());
			System.out.println("Connected...");
			System.out.println("Your connected "+socket.getRemoteSocketAddress().toString());
			dis = new DataInputStream(socket.getInputStream());
			con=new ArrayList<Connections>();
		
			con.add(new Connections(InetAddress.getLocalHost(),port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exit() {
		try {
			dout.close();
			server.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
