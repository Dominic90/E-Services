package main;

public class Server {

	private NodeJSController nodeController;
	private NetworkController networkController;
	
	public Server() {
		System.out.println("Main");
		nodeController = new NodeJSController();
		networkController = new NetworkController(nodeController);
		networkController.start();
	}
	
	public static void main(String args[]) {
		Server server = new Server();
		
	}
}
