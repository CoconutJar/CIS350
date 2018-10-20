package Messaging;

public class Messages2 {
public static void main(String args[]) {
	Client net = new Client();
	net.connect("localhost", 6666);
	net.message();
}}
