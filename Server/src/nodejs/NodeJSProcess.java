package nodejs;

import java.io.IOException;

public class NodeJSProcess {

//	24 * 60 * 60 * 1000
	private static final long maxRunningTimeInMs = 120 * 1000;
	
	private Process process;
	private String eventId;
	private String port;
	private String url;
	
	private long startingTime;
	
	public NodeJSProcess(String eventId, String port, String url) {
		this.eventId = eventId;
		this.port = port;
		this.url = url;
		
		startingTime = System.currentTimeMillis();
	}
	
	public void start() {
		ProcessBuilder pb = new ProcessBuilder("node", "/home/dominic/git/E-Services/kurento/server.js", port, url);
		
		try {
			process = pb.start();
			new NodeJSProcessStatusLogger(process, port).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isNoLongerActive() {
		// process is older than an day
		if (System.currentTimeMillis() > startingTime + maxRunningTimeInMs) { // 1 minute
			System.out.println("P: " + port + " Process is to old");
			return true;
		}
		// extends to when connection to 1 closed than 1 hour
		System.out.println("P: " + port + " Process is not old enought");
		return false;
	}
	
	public void stop() {
		System.out.println("P: " + port + " Process stop");
		process.destroy();
		System.out.println("P: " + port + " Process stopped");
	}
	
	public String getEventId() {
		return eventId;
	}
}
