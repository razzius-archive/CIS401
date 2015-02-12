package orchestration;


public class Request {

	// requestID "r4"
	private String requestID; 

	// startnode "s3_0"
	private String startNode; 

	// endnode "s3_1" 
	private String endNode;   

	// packageRate(#packages/s) "72432"
	private int packageRate;

	// deadline(ms) "44"
	private int deadline;

	// services "s0-s1-s3"
	private String services;

	// PackageSize(bits) "12000"
	private int packageSize;

	// price($) "1"
	private int price;

	public Request(String requestID, String startNode, String endNode, int packageRate, int deadline, String services, int packageSize, int price) {
		this.requestID = requestID;
		this.startNode = startNode;
		this.endNode = endNode;
		this.packageRate = packageRate;
		this.deadline = deadline;
		this.services = services;
		this.packageSize = packageSize;
		this.price = price;
	}

	public String formatParams() {
		String params = "requestID=" + this.requestID + "&" +
						"startNode=" + this.startNode + "&" +
						"endNode=" + this.endNode + "&" +
						"packageRate=" + this.packageRate + "&" +
						"deadline=" + this.deadline + "&" +
						"services=" + this.services + "&" +
						"packageSize=" + this.packageSize + "&" +
						"price=" + this.price;
		return params;
	}
}