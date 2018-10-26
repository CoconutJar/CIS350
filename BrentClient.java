

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import java.util.StringTokenizer;

public class Client{
	public static void main(String[] args) throws IOException{
		
		Scanner scn = new Scanner(System.in); 
		System.out.println("Type a UserName to use in the chat room");
        String name = scn.nextLine();
        InetAddress ip = InetAddress.getByName("localhost"); 
          
        Socket s = new Socket(ip, 3158); 
          
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
        dos.writeUTF(name);
		
		Thread sendMessage = new Thread(new Runnable()  { 
            @Override
            public void run() { 
                while (true) { 
  
                    // read the message to deliver. 
                    String msg = scn.nextLine(); 
                      
                    try { 
                        // write on the output stream 
                        dos.writeUTF(msg); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
         
        Thread readMessage = new Thread(new Runnable()  { 
            @Override
            public void run() { 
  
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        String msg = br.readLine(); 
                        System.out.println(msg); 
                    } catch (IOException e) { 
  
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
        sendMessage.start();
        readMessage.start();
	}
}
