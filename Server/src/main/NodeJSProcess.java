package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NodeJSProcess {

	private Process process;
	private String port;
	private String url;
	
	private long startingTime;
	
	public NodeJSProcess(String port, String url) {
		this.port = port;
		this.url = url;
		
		startingTime = System.currentTimeMillis();
	}
	
	public void start() {
		ProcessBuilder pb = new ProcessBuilder("node", "/home/dominic/git/E-Services/kurento/server.js", port, url);
		
		try {
			process = pb.start();
			new Thread() {
				
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
//								process.destroy();
								// TODO: inform rails / db
							}
						}
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isNoLongerActive() {
		
		// process is older than an day
//		if (System.currentTimeMillis() > startingTime + 24 * 60 * 60 * 1000) {
		if (System.currentTimeMillis() > startingTime + 60 * 1000) { // 1 minute
			System.out.println("P: " + port + " Process is to old");
			return true;
		}
		// extends to when connection to 1 closed than 1 hour
		System.out.println("P: " + port + " Process is not old enought");
		return false;
	}
	
	public void stop() {
		process.destroy();
	}
	
	public String getPort() {
		return port;
	}
}
