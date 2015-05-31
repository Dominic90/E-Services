package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import nodejs.NodeJSController;

public class ProcessRequest extends Thread {

	private NodeJSController nodeController; 
	private Socket socket;
	
	public ProcessRequest(NodeJSController nodeController, Socket socket) {
		this.nodeController = nodeController;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String type = bufferedReader.readLine();
			
			if (type.equals("start node")) {
				processStartNode(bufferedReader);
			}
			else {
				processStopNode(bufferedReader);
			}
		 	
		 	bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		 	bufferedWriter.write("ok");
		 	bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			closeRessources(bufferedReader, bufferedWriter);
		}
	}
	
	private void processStartNode(BufferedReader bufferedReader) throws IOException {
		String eventId = bufferedReader.readLine();
		String ip = bufferedReader.readLine();
		String port = bufferedReader.readLine();
		String url = bufferedReader.readLine();
		System.out.println("Start node: " + eventId + " " + ip + ":" + port + " " + url);
		
		nodeController.startNewNodeInstance(eventId, port, url);
	}
	
	private void processStopNode(BufferedReader bufferedReader) throws IOException {
		String eventId = bufferedReader.readLine();
		System.out.println("Stop node: " + eventId);
		
		nodeController.stopNodeInstance(eventId);
	}
	
	private void closeRessources(BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bufferedWriter != null) {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
