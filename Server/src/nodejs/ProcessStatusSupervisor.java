package nodejs;

import java.util.ArrayList;
import java.util.List;

import network.NetworkController;

public class ProcessStatusSupervisor implements Runnable {

	private NetworkController networkController;
	private List<NodeJSProcess> processes;
	
	public ProcessStatusSupervisor(NetworkController networkController, List<NodeJSProcess> processes) {
		this.networkController = networkController;
		this.processes = processes;
	}
	
	@Override
	public void run() {
		System.out.println("C: Starting cleanup");
    	List<NodeJSProcess> oldProcesses = new ArrayList<NodeJSProcess>();
        for (NodeJSProcess process : processes) {
        	if (process.isNoLongerActive()) {
        		process.stop();
        		networkController.deleteProcess(process);
        		oldProcesses.add(process);
        	}
        }
        for (NodeJSProcess process : oldProcesses) {
        	processes.remove(process);
        }
	}
}
