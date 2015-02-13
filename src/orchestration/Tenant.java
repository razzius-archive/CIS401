package orchestration;

//
//
// Tenant
//
// Stores Individual Tenant information

public class Tenant {

	int tenantID;
	int deadline;		// Fixed deadline in ms
	
	public Tenant(int tenantID, int deadline) {
		this.tenantID = tenantID;
		this.deadline = deadline;
	}

	public int getID() {
		return tenantID;
	}

	public int getDeadline() {
		return deadline;
	}


}