package orchestration;

//
//
// VM
//
// Stores Virtual Machine information

public class VM {

	int vmID;
	double coresAllocated;	// VMs can request fractions of cores.
	String ipAddress;
	
	public VM(int vmID, double coresAllocated) {
		this.vmID = vmID;
		this.coresAllocated = coresAllocated;
		// Set IP Address automatically
	}

	public int getID() {
		return vmID;
	}

	public double getCores() {
		return coresAllocated;
	}


}