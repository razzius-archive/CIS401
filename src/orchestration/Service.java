package orchestration;

//
//
// Service
//
// Stores Service information

public class Service {

	int serviceID;
	int wcet;		// Worst-Case Execution Time in ms
	
	public Service(int serviceID, int wcet) {
		this.serviceID = serviceID;
		this.wcet = wcet;
	}

	public int getID() {
		return serviceID;
	}

	public int getWcet() {
		return wcet;
	}


}