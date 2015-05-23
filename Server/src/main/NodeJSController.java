package main;

public class NodeJSController {

	private NodeJSProcess process;
	
	public void startNewNodeInstance(int port) {
		process = new NodeJSProcess(port);
		System.out.println("Starting node");
		process.start();
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("stopping node");
//		process.stop();
	}
}