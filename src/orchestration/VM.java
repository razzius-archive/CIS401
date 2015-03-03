package orchestration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

//
// VM
//
// Stores Virtual Machine information

public class VM implements Serializable {
	private String id = UUID.randomUUID().toString(); // uuid as string
	double coresAllocated;	// VMs can request fractions of cores.
	int memoryAllocated; 	// MB of memory allocated
	String ipAddress;		// Get the IP address after booting
	String status; 			// Booting, Running, Stopped, Crashed, Terminated
	HashSet<Integer> serviceInstanceIDs = new HashSet<Integer>();

	public VM(double coresAllocated, int memoryAllocated) {
		this.coresAllocated = coresAllocated;
		this.memoryAllocated = memoryAllocated;
		status = "booting";
	}

	public String getID() {
		return id;
	}

	public double getCores() {
		return coresAllocated;
	}

	public int getMemory() {
		return memoryAllocated;
	}


}
