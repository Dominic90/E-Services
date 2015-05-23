package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
	public static void main(String[] args) {
	 	ServerTest server = new ServerTest();
	 	try {
	 	    server.test();
	  	} catch (IOException e) {
	  	    e.printStackTrace();
	  	} 
    }
	
	public void test() throws IOException {
 		int port = 11111;
 		ServerSocket serverSocket = new ServerSocket(port);
 		Socket client = warteAufAnmeldung(serverSocket);
// 		while (true) {
	 		String nachricht = leseNachricht(client);
	 		System.out.println(nachricht);
	 		schreibeNachricht(client, nachricht);	 		
//	 	}
    }
	    
	private Socket warteAufAnmeldung(ServerSocket serverSocket) throws IOException {
	 	Socket socket = serverSocket.accept(); // blockiert, bis sich ein Client angemeldet hat
	 	return socket;
	   }
	    
	private String leseNachricht(Socket socket) throws IOException {
	  	BufferedReader bufferedReader = 
	 	    new BufferedReader(
	 	 	new InputStreamReader(
	 		    socket.getInputStream()));
	 	char[] buffer = new char[1000];
//	 	int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
	 	String test1 = bufferedReader.readLine();
	 	String test2 = bufferedReader.readLine();
	 	System.out.println(test1 + " " + test2);
//	 	String nachricht = new String(buffer, 0, anzahlZeichen);
	 	return test1;
	}
	    
	private void schreibeNachricht(Socket socket, String nachricht) throws IOException {
	 	PrintWriter printWriter =
	 	    new PrintWriter(
	 	        new OutputStreamWriter(
	 	 	    socket.getOutputStream()));
	 	printWriter.print("Hello");
	 	printWriter.flush();
	   }
}
