================================================================================================
==========                          Simple RMI Example                                ==========                                                            
==========                           Author: G Jones                                  ==========
==========                          Created: 12/11/2011                               ==========
================================================================================================

Contents
1 - Introduction
2 - Use
    2.1 - Easy use with windows batch files
3 - Disclaimer


1 - Introduction:

	This set of classes is designed to show the reliability of UDP in Java.

	The way it does this is by trying to send a specified number of messages from the client
	to the server.  Each message contains the message sequence number and the total number of
	messages to be sent.  The server keeps track of the messages received and when there are 
	no more messages, outputs a summary of the number of messages received and also which
	ones were lost.

2 - Use - General:

	To use the system you must first start the server and then start the client.
	Faliure to do so will mean the client will not be able to find to the server
	and thus throw an error saying that it could not connect to the server.

	Note that openall.policy should be stored in the directory above bin.
	This allows AllPermissions to be set :: DO NOT USE THIS FOR CRITICAL SOFTWARE
	This is only useful for little test examples and I hold no responsibility for any damage
	that comes from using it.

	To start the server, at the command prompt:
		a) Navigate to the /bin/ directory (where the .class files are located)
		b) Run the server by typing the following command, note port_number should be the
			port number you want to open the server on.  Default is 1099.
			Remember ports 0-1023 are reserved as well known ports.
			
		java -Djava.security.policy=../openall.policy server/UDPServer port_num
		 
		 
	To start the client, at the command prompt:
		a) Navigate to the /bin/ directory
		b) Run the client by typing the following command. 
			Note hostname     - should be the hostname or IP address of the pc your server is on.
				 port_number  - should be the port number you want to connect to.
				 num_messages - should be the number of messages you wish to send to the server.
			Remember ports 0-1023 are reserved as well known ports.
			
		java -Djava.security.policy=../openall.policy client/UDPClient hostname port_number num_messages
		
2.1 - Easy use with windows batch files
	
	To compile the source files in command prompt run compile.bat
	To run the server type run-server port_num
	To run the client type run-client host_name port_num num_messages
	
	If you dont run them from inside the command prompt there is a chance the command prompt will 
	close before you see the results.

3 - Disclaimer

	USE THIS SOFTWARE AT YOUR OWN RISK. SOUCE FILES ARE INCLUDED FOR INSPECTION.