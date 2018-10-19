package Messaging;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Network{
	private static ServerSocket welcomeSocket;

	
	public static void main(String[] args) throws IOException{
	    try {
			welcomeSocket = new ServerSocket(3158); // CCPort established
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				ClientHandler ch = new ClientHandler(connectionSocket);
				System.out.println(connectionSocket.getRemoteSocketAddress().toString()+ " has connected!");
			}
		}catch(Exception e) {
				System.out.println("ERROR: Server could not be started.");
			}
	    finally{
	      try{
	        System.out.println("Server socket closed.");
	      } catch(Exception e){
	        e.printStackTrace();
	      }
	    }
	  
	}
	
	@Override
	protected void exit() {
		
		
	}
}
class ClientHandler extends Network implements Runnable	{
		  Socket connectionSocket;
		  String fromClient;
		  String clientCommand;
		  String frstln;
		  int port;

		  public ClientHandler(Socket connectionSocket){
		      this.connectionSocket = connectionSocket;

		      System.out.println("-- Client Connected --");
		      run();
		  }
		
		  public void run()	{
		    while(true){
		      try{
		        
		       
		      }catch(Exception e) {
		    	  exit();
		      }
		    }
		  }

		@Override
		protected void exit() {
			// TODO Auto-generated method stub
			
		}
}
	

