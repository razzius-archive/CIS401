package orchestration;

import java.io.Serializable;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * An instance of a service which is running on a network machine.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class ServiceInstance implements Serializable {

	/** A unique ID assigned to this service instance. */
	public String serviceInstanceID;

	/** A ID assigned to this associated VM. */
	public String vmID;

	/** A ID assigned to this associated Service. */
	public String serviceID;

	/** The bandwidth required by this service instance. */
	public int bandwidth;

	/** The memory required by this service instance. */
	public int memory;

	/** A message which indicates service instance status */
	public ServiceInstance.Status status = ServiceInstance.Status.PENDING;

	/** The port used by this service instance. */
	public int port;

	/** The process ID dynamically assigned to this service instance. */
	public int PID;

	public enum Status {
		STARTING, PENDING, RUNNING, PAUSED, FINISHED, TERMINATED, CRASHED
	}

	/** Create a service instance from an existing service instance. */
	public ServiceInstance(ServiceInstance other) {
		this.serviceInstanceID = other.serviceInstanceID;
		this.vmID = other.vmID;
		this.serviceID = other.serviceID;
		this.bandwidth = other.bandwidth;
		this.memory = other.memory;
		this.status = other.status;
		this.port = other.port;
		this.PID = other.PID;
	}

	/** Create a service instance with the specified parameters. */
	public ServiceInstance(String serviceID, String vmID) {
		this.serviceID = serviceID;
		this.vmID = vmID;
	}

	/** @return the unique ID assigned to this service instance. */
	public String getServiceInstanceID() {
		return serviceInstanceID;
	}

	/** @return the process ID dynamically assigned to this service instance. */
	public int getPID() {
		return PID;
	}

	/** Set the process ID of this service instance. */
	public void setPID(int PID) {
		this.PID = PID;
	}
}