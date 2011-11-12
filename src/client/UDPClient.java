package client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import common.MessageInfo;

public class UDPClient {

	private DatagramSocket sendSoc;

	public static void main(String[] args) {
		InetAddress	serverAddr = null;
		int			recvPort;
		int 		countTo;
		String 		message;

		// Get the parameters
		if (args.length < 3) {
			System.err.println("Arguments required: server name/IP, recv port, message count");
			System.exit(-1);
		}

		try {
			serverAddr = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[1]);
		countTo = Integer.parseInt(args[2]);


		// TO-DO: Construct UDP client class and try to send messages
		System.out.println("Constructing udp client");
		UDPClient client = new UDPClient();
		System.out.println("Sending messages");
		client.testLoop(serverAddr, recvPort, countTo);
	}

	public UDPClient() {
		// TO-DO: Initialise the UDP socket for sending data
		try {
			sendSoc = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Error creating socket for sending data.");
		}
		

	}

	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) {
		MessageInfo m;
		ByteArrayOutputStream byteStream;
		ObjectOutputStream os;
		// TO-DO: Send the messages to the server
		for(int i = 0; i < countTo; i++) {
			m = new MessageInfo(countTo,i);
			
			byteStream = new ByteArrayOutputStream(5000);
			try {
				os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
				os.flush();
				os.writeObject(m);
				os.flush();
			} catch (IOException e) {
				System.out.println("Error serializing object for transmition.");
				System.exit(-1);
			}
			
			//retrieves byte array
			byte[] sendBuf = byteStream.toByteArray();    
			send(sendBuf, serverAddr, recvPort);
			
			
		}
	}

	private void send(byte[] data, InetAddress destAddr, int destPort) {
		DatagramPacket		pkt;

		// TO-DO: build the datagram packet and send it to the server
		pkt = new DatagramPacket(data, data.length, destAddr, destPort);
		try {
			sendSoc.send(pkt);
		} catch (IOException e) {
			System.out.println("Error transmitting packet over network.");
			System.exit(-1);
		}
	}
}
