package nodejs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NodeJSProcessStatusLogger extends Thread {

	private Process process;
	private String port;
	
	public NodeJSProcessStatusLogger(Process process, String port) {
		this.process = process;
		this.port = port;
	}
	
	@Override
	public void run() {
		setName("Screencapture Thread");
		InputStreamReader isr = new InputStreamReader(process.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("P: " + port + " " + line);
				if (line.equals("Connection 1 closed")) {
//					process.destroy();
					// TODO: inform rails / db
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
