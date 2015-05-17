package orchestration;

/**
 * A request for services sent by a customer.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class Request {

	// requestID "r4"
	/** A unique ID assigned to the request. */
	public String requestID;

	// startnode "s3_0"
	/** Indicate the network node to which data packets will be passed. */
	public String startNode;

	// endnode "s3_1"
	/** Indicate the network node from which data packets will be received. */
	public String endNode;

	// deadline(ms) "44"
	/** Indicate the maximum allowable latency associated with the request. */
	public int deadline;

	// services "s0-s1-s3"
	/** Indicates the services arranged in the order in which they are desired. */
	public String[] services;

	/** Create a request with the specified parameters. */
	public Request(String requestID, String startNode, String endNode, int deadline, String[] services) {
		this.requestID = requestID;
		this.startNode = startNode;
		this.endNode = endNode;
		this.deadline = deadline;
		this.services = services;
	}
}
