
import java.io.BufferedInputStream;
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
import java.util.StringTokenizer;

public class Server{
	private static ServerSocket welcomeSocket;
	public static ArrayList<ClientHandler> con = new ArrayList<ClientHandler>();

	
	public static void main(String[] args) throws IOException{
	    try {
			welcomeSocket = new ServerSocket(3158); // CCPort established
			int i = 1;
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				DataInputStream dis = new DataInputStream(connectionSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
				String name = dis.readUTF();
				ClientHandler client = new ClientHandler(connectionSocket,name, dis, dos);
				con.add(client);
				System.out.println(connectionSocket.getRemoteSocketAddress().toString()+ " has connected!");
				Thread t = new Thread(client);
				t.start();
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
}
class ClientHandler implements Runnable	{
		  Socket connectionSocket;
		  String fromClient;
		  String clientName;
		  String frstln;
		  int port;
		  String clientMsg;
		  DataInputStream dis;
		  DataOutputStream dos;
		  boolean loggedIn;

		  public ClientHandler(Socket connectionSocket, String name, DataInputStream dis, DataOutputStream dos){
		      this.connectionSocket = connectionSocket;
		      this.dis = dis;
		      this.dos = dos;
		      this.clientName = name;
		      this.loggedIn = true;

		      System.out.println("-- Client Connected --");
		  }
		  @Override
		  public void run()	{
		    while(true){
		      try{
		    	  
		    	  do {
		    		  	fromClient = dis.readUTF(); 
	                  
		                System.out.println(fromClient); 
		              
		                  
		                // break the string into message and recipient part 
		                StringTokenizer st = new StringTokenizer(fromClient); 
		                String message = st.nextToken(); 
		                String recipient = st.nextToken(); 
		    		  
		    		  for (ClientHandler mc : Server.con){ 
			                   
		        		  if (mc.clientName.equals(recipient) && mc.loggedIn==true){ 
		        			  mc.dos.writeUTF(this.clientName+" : "+ message); 
		        			  break;
			              } 
			          }
		        		  
		         }while(!clientMsg.equals("QUIT"));
		    	  this.loggedIn=false; 
                  this.connectionSocket.close(); 
		      }catch(Exception e) {
		    	  System.exit(1);
		      }
		    }
		  }
}
	

