package orchestration;

import java.rmi.Naming;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class ServiceInstance {

	public int vmID;
	public int serviceID;
	public int bandwidth;
	public int memory;
	public ServiceInstance.Status status = ServiceInstance.Status.STARTING;
	public int port;
	public int PID;


	public enum Status {
		STARTING, PENDING, RUNNING, PAUSED, FINISHED, TERMINATED, CRASHED
	}

	public ServiceInstance(int serviceID, int vmID) {
		this.serviceID = serviceID;
		this.vmID = vmID;
	}
}