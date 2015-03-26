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

	public VM(VM other) {
		id = other.getID();
		coresAllocated = other.coresAllocated;
		memoryAllocated = other.memoryAllocated;
		ipAddress = other.ipAddress;
		status = other.status;
		for (String otherServiceInstanceID : other.serviceInstances.keySet()) {
			serviceInstances.put(otherServiceInstanceID, new ServiceInstance(other.getServiceInstances().get(otherServiceInstanceID)));
		}
	}

	public VM(double coresAllocated, int memoryAllocated) {
		this.coresAllocated = coresAllocated;
		this.memoryAllocated = memoryAllocated;
	}

	public String getID() {
		return id;
	}

	public int getMemory() {
		return memoryAllocated;
	}

	public HashMap<String, ServiceInstance> getServiceInstances() {
		return serviceInstances;
	}

}
