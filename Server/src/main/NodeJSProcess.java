package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;

public class NodeJSProcess {

	private Process process;
	private String port;
	private String url;
	
	public NodeJSProcess(String port, String url) {
		this.port = port;
		this.url = url;
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
							System.out.println(line);
							if (line.equals("Connection 1 closed")) {
								process.destroy();
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
	
//	public void stop() {
//		process.destroy();
////		OutputStream os = process.getOutputStream();
////		try {
////			os.write(("q/n".getBytes()));
////			os.flush();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//	}
}
