package Messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client2 extends Network  {

	public void connect(String ip, int portnum){
		try {
			socket = new Socket(InetAddress.getByName(ip), portnum);
			dout= new DataOutputStream(socket.getOutputStream());
			dis= new DataInputStream(socket.getInputStream());
			System.out.println("Connected..");
			System.out.println("your are connected to "+socket.getRemoteSocketAddress().toString());
			con = new ArrayList<Connections>();
			con.add(new Connections(InetAddress.getByName(ip),portnum));
			
} catch (UnknownHostException e) {
	
	e.printStackTrace();
} catch (IOException e) {
	
	e.printStackTrace();
}
}

public void exit() {
	try {
		dout.close();
		socket.close();
		dis.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}}


