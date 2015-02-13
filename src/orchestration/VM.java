package orchestration;

//
//
// VM
//
// Stores Virtual Machine information

public class VM {

	int vmID;
	double coresAllocated;	// VMs can request fractions of cores.
	
	public VM(double numCores) {
		coresAllocated = numCores;
	}

	public int getID() {
		return vmID;
	}

	public double getCores() {
		return coresAllocated;
	}


}