package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NodeJSController {

	private List<NodeJSProcess> processes;
	ScheduledExecutorService deleteOldProcesses = Executors.newSingleThreadScheduledExecutor();
	
	public NodeJSController() {
		processes = new ArrayList<NodeJSProcess>();
		deleteOldProcesses.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		    	System.out.println("C: Starting cleanup");
		    	List<NodeJSProcess> oldProcesses = new ArrayList<NodeJSProcess>();
		        for (NodeJSProcess process : processes) {
		        	if (process.isNoLongerActive()) {
		        		process.stop();
		        		oldProcesses.add(process);
		        	}
		        }
		        for (NodeJSProcess process : oldProcesses) {
		        	processes.remove(process);
		        }
		    }
		}, 0, 10, TimeUnit.MINUTES);
	}
	
	public void startNewNodeInstance(String port, String url) {
		NodeJSProcess nodeProcess = new NodeJSProcess(port, url);
		processes.add(nodeProcess);
		System.out.println("P: " + port + " Starting node");
		nodeProcess.start();
	}
	
	public void stopNodeInstance(String port) {
		List<NodeJSProcess> oldProcesses = new ArrayList<NodeJSProcess>();
		for (NodeJSProcess process : processes) {
        	if (process.getPort().equals(port)) {
        		process.stop();
        		oldProcesses.add(process);
        	}
        }
		for (NodeJSProcess process : oldProcesses) {
        	processes.remove(process);
        }
	}
}
