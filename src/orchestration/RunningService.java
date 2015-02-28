package orchestration;

//
//
// RunningService
//
// Stores RunningService information - WORK IN PROGRESS

public class RunningService {

	private int serviceID;			// References one of the services
	private int vmID;				// Indicates the VM that the service is running on
	private int wcet;				// Worst-Case Execution Time in ms
	private int trafficLevel;		// Current memory required in MB
	
	public Service(int serviceID, int wcet, int maxMemory) {
		this.serviceID = serviceID;
		this.wcet = wcet;
		this.maxMemory = maxMemory;
	}

	public int getTrafficLevel() {
		return trafficLevel;
	}

	public void setTrafficLevel(int level) {
		trafficLevel = level;
	}

	public int getID() {
		return serviceID;
	}

	public int getWcet() {
		return wcet;
	}


}