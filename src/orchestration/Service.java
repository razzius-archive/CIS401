package orchestration;

//
//
// Service
//
// Stores Service information

public class Service {

	String serviceID;
	int wcet;		// Worst-Case Execution Time in ms
	String command;	// Maximum memory required
	
	public Service(String serviceID, int wcet, String command) {
		this.serviceID = serviceID;
		this.wcet = wcet;
		this.command = command;
	}

	public String getID() {
		return serviceID;
	}

	public int getWcet() {
		return wcet;
	}


}