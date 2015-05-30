package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkController extends Thread {

	private NodeJSController nodeController;
	private HttpController httpController;
	private static final int port = 9999;
	
	public NetworkController(NodeJSController nodeController) {
		this.nodeController = nodeController;
		this.nodeController.setNetworkController(this);
		httpController = new HttpController();
	}
	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket socket = serverSocket.accept();
				Thread processSocket = new Thread() {
					
					@Override
					public void run() {
						BufferedReader bufferedReader = null;
						BufferedWriter bufferedWriter = null;
						try {
							bufferedReader = new BufferedReader(
							 	 	new InputStreamReader(socket.getInputStream()));
							String type = bufferedReader.readLine();
							if (type.equals("start node")) {
								String eventId = bufferedReader.readLine();
								String ip = bufferedReader.readLine();
								String port = bufferedReader.readLine();
								String url = bufferedReader.readLine();
								System.out.println("Start node: " + eventId + " " + ip + ":" + port + " " + url);
								
								nodeController.startNewNodeInstance(eventId, port, url);								
							}
							
							else {
								String eventId = bufferedReader.readLine();
								System.out.println("Stop node: " + eventId);
								
								nodeController.stopNodeInstance(eventId);
							}
						 	
						 	bufferedWriter = new BufferedWriter(new OutputStreamWriter(
						 	 	    socket.getOutputStream()));
						 	bufferedWriter.write("ok");
						 	bufferedWriter.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						finally {
							if (bufferedReader != null) {
								try {
									bufferedReader.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							if (bufferedWriter != null) {
								try {
									bufferedWriter.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							if (socket != null) {
								try {
									socket.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				};
				processSocket.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void deleteProcess(NodeJSProcess process) {
		httpController.deleteEvent(process.getEventId());
	}
}
