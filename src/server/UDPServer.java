package server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import common.MessageInfo;


public class UDPServer {

	private DatagramSocket socket;
	private int port;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() throws SocketTimeoutException {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	packet;

		// Receive the messages and process them by calling processMessage(...)
		System.out.println("Ready to receive...");
		
		while(!close){

		    //Receive request from client
			pacSize = 5000;
			pacData = new byte[5000];
								  
			packet = new DatagramPacket(pacData, pacSize);
			try {
			  socket.setSoTimeout(30000);
			  socket.receive(packet);
			} catch (IOException e) {
				System.out.println("Error IO exception receiving packet.");
				System.out.println("Could be due to timeout.");
				System.out.println("Now closing server...");
				System.exit(-1);
			}
			
			processMessage(packet.getData());
			
		}
		
	}

	public void processMessage(byte[] data) {

		MessageInfo msg = null;

		// Use the data to construct a new MessageInfo object
	    ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
	    ObjectInputStream is;
	    
		try {
			is = new ObjectInputStream(new BufferedInputStream(byteStream));
			msg = (MessageInfo) is.readObject();	
			is.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error: Could not find class match for transmitted message.");
		} catch (IOException e) {
			System.out.println("Error: IO exception creating ObjectInputStream.");
		}
	    
		// On receipt of first message, initialize the receive buffer
		if (receivedMessages == null) {
			totalMessages = msg.totalMessages;
			receivedMessages = new int[totalMessages];
		}
		
		// Log receipt of the message
		receivedMessages[msg.messageNum] = 1;
		
		// If this is the last expected message, then identify
		//        any missing messages
		if (msg.messageNum + 1 == msg.totalMessages) {
			close = true;
			
			String s = "Lost packet numbers: ";
			int count = 0;
			for (int i = 0; i < totalMessages; i++) {
				if (receivedMessages[i] != 1) {
					count++;
					s = s + " " + (i+1) + ", ";
				}
			}
			
			if (count == 0) s = s + "None";
			
			System.out.println("Messages processed...");
			System.out.println("Of " + msg.totalMessages + ", " + (msg.totalMessages - count) + " received successfully...");
			System.out.println("Of " + msg.totalMessages + ", " + count + " failed...");
			System.out.println(s);
			System.out.println("Test finished.");
		}
		

	}


	public UDPServer(int rp) {
		// Initialize UDP socket for receiving data
		try {
			port = rp;
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Error: Could not create socket on port " + port);
			System.exit(-1);
		}
		// Make it so the server can run.
		close = false;
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Error: Arguments required - recv-port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// Initialize Server object and start it by calling run()
		UDPServer udpsrv = new UDPServer(recvPort);
		try {
			udpsrv.run();
		} catch (SocketTimeoutException e) {}
	}

}
