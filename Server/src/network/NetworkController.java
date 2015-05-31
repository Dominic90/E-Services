package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import nodejs.NodeJSController;
import nodejs.NodeJSProcess;

public class NetworkController extends Thread {

	private NodeJSController nodeController;
	private HttpController httpController;
	private static final int port = 9999;
	
	public NetworkController(NodeJSController nodeController) {
		this.nodeController = nodeController;
		this.nodeController.startProcessStatusSupervisor(this);
		httpController = new HttpController();
	}
	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket socket = serverSocket.accept();
				ProcessRequest processSocket = new ProcessRequest(nodeController, socket);
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
