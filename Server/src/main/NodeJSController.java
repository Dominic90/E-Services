package main;

public class NodeJSController {

	private NodeJSProcess process;
	
	public void startNewNodeInstance(String port, String url) {
		process = new NodeJSProcess(port, url);
		System.out.println("Starting node");
		process.start();
	}
}
