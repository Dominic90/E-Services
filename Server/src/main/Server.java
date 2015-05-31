package main;

import network.NetworkController;
import nodejs.NodeJSController;


public class Server {

	private NodeJSController nodeController;
	private NetworkController networkController;
	
	public Server() {
		System.out.println("M: Main");
		nodeController = new NodeJSController();
		networkController = new NetworkController(nodeController);
		networkController.start();
	}
	
	public static void main(String args[]) {
		Server server = new Server();
	}
}
