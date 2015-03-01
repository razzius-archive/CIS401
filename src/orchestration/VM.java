package orchestration;

import java.util.ArrayList;
import java.util.HashSet;

//
//
// VM
//
// Stores Virtual Machine information

public class VM {

	int vmID;				// Unique ID
	double coresAllocated;	// VMs can request fractions of cores.
	int memoryAllocated; 	// MB of memory allocated
	String ipAddress;		// Get the IP address after booting
	String status; 			// Booting, Running, Stopped, Crashed, Terminated
	private static int id = 0;
	HashSet<Integer> serviceInstanceIDs = new HashSet<Integer>();
	
	public VM(double coresAllocated, int memoryAllocated) {
		id++;
		this.vmID = id;
		this.coresAllocated = coresAllocated;
		this.memoryAllocated = memoryAllocated;
		// Set IP Address automatically
	}

	public int getID() {
		return vmID;
	}

	public double getCores() {
		return coresAllocated;
	}


}