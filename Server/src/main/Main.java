package main;

public class Main {

	public static void main(String args[]) {
		System.out.println("Main");
		NodeJSController controller = new NodeJSController();
		controller.startNewNodeInstance(10000);
	}
}
