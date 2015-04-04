package orchestration;

import java.rmi.Naming;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class ServiceInstance {

	public String serviceInstanceID;
	public String vmID;
	public String serviceID;
	public int bandwidth;
	public int memory;
	public ServiceInstance.Status status = ServiceInstance.Status.PENDING; 
	public int port;
	public int PID;

	public enum Status {
		STARTING, PENDING, RUNNING, PAUSED, FINISHED, TERMINATED, CRASHED
	}

	public ServiceInstance(ServiceInstance other) {
		this.serviceInstanceID = other.serviceInstanceID;
		this.vmID = other.vmID;
		this.serviceID = other.serviceID;
		this.bandwidth = other.bandwidth;
		this.memory = other.memory;
		this.status = other.status;
		this.port = other.port;
		this.PID = other.PID;
	}

	public ServiceInstance(String serviceID, String vmID) {
		this.serviceID = serviceID;
		this.vmID = vmID;
	}

	public String getServiceInstanceID() {
		return serviceInstanceID;
	}

	public int getPID() {
		return PID;
	}

	public void setPID(int PID) {
		this.PID = PID;
	}
}