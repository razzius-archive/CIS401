package orchestration;


public class Request {

	// requestID "r4"
	public String requestID;

	// startnode "s3_0"
	public String startNode;

	// endnode "s3_1"
	public String endNode;

	// deadline(ms) "44"
	public int deadline;

	// services "s0-s1-s3"
	public String[] services;

	public Request(String requestID, String startNode, String endNode, int deadline, String[] services) {
		this.requestID = requestID;
		this.startNode = startNode;
		this.endNode = endNode;
		this.deadline = deadline;
		this.services = services;
	}
}
