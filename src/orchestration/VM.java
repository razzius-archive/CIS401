package orchestration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

//
// VM
//
// Stores Virtual Machine information

public class VM extends Node implements Serializable {
	private String id = UUID.randomUUID().toString(); // uuid as string
	private double coresAllocated;	// VMs can request fractions of cores.
	private int memoryAllocated; 	// MB of memory allocated
	private String ipAddress;		// Get the IP address after booting
	private String status; 			// Booting, Running, Stopped, Crashed, Terminated
	// HashSet<Integer> serviceInstanceIDs = new HashSet<Integer>();
	private HashMap<String, ServiceInstance> serviceInstances = new HashMap<String, ServiceInstance>();

	public VM(double coresAllocated, int memoryAllocated) {
		id = "1";
		this.coresAllocated = coresAllocated;
		this.memoryAllocated = memoryAllocated;
	}

	public String getID() {
		return id;
	}

	public int getMemory() {
		return memoryAllocated;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public HashMap<String, ServiceInstance> getServiceInstances() {
		return serviceInstances;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
