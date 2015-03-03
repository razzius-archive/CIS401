package orchestration;

//
//
// Service
//
// Stores Service information

public class Service {

	String serviceID;
	int wcet;		// Worst-Case Execution Time in ms
	int maxMemory;	// Maximum memory required
	
	public Service(String serviceID, int wcet, int maxMemory) {
		this.serviceID = serviceID;
		this.wcet = wcet;
		this.maxMemory = maxMemory;
	}

	public String getID() {
		return serviceID;
	}

	public int getWcet() {
		return wcet;
	}


}