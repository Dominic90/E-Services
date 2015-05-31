package nodejs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import network.NetworkController;

public class NodeJSController {
	
	private List<NodeJSProcess> processes;
	ScheduledExecutorService deleteOldProcesses = Executors.newSingleThreadScheduledExecutor();
	
	public NodeJSController() {
		processes = new ArrayList<NodeJSProcess>();
	}
	
	public void startNewNodeInstance(String eventId, String port, String url) {
		NodeJSProcess nodeProcess = new NodeJSProcess(eventId, port, url);
		processes.add(nodeProcess);
		System.out.println("P: " + port + " Starting node");
		nodeProcess.start();
	}
	
	public void stopNodeInstance(String eventId) {
		List<NodeJSProcess> oldProcesses = new ArrayList<NodeJSProcess>();
		for (NodeJSProcess process : processes) {
        	if (process.getEventId().equals(eventId)) {
        		process.stop();
        		oldProcesses.add(process);
        	}
        }
		for (NodeJSProcess process : oldProcesses) {
        	processes.remove(process);
        }
	}
	
	public void startProcessStatusSupervisor(NetworkController networkController) {
		deleteOldProcesses.scheduleAtFixedRate(new ProcessStatusSupervisor(networkController, processes), 
				0, 10, TimeUnit.SECONDS);
	}
}
