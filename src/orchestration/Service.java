package orchestration;

/**
 * A service which can be run on network machines.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class Service {

	/** A unique ID assigned to this service. */
	String serviceID;

	/** The worst-case execution time for this service, in ms. */
	int wcet;

	/** The UNIX command used to run the service. */
	String command;
	
	/** Create a service with the specified parameters. */
	public Service(String serviceID, int wcet, String command) {
		this.serviceID = serviceID;
		this.wcet = wcet;
		this.command = command;
	}

	/** @return the ID assigned to this service. */
	public String getID() {
		return serviceID;
	}

	/** @return the worst-case execution time for this service, in ms. */
	public int getWcet() {
		return wcet;
	}


}