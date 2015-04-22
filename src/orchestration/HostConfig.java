package orchestration;

import java.util.UUID;

/**
 * A machine as specified in the config.json file. Stores properties of the machine's hardware and its IP address.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class HostConfig {

	/** Network Bandwidth in kB/s */
	private int bandwidth;

	/** RAM in MB */
	private int memory;

	/** Number of cores allocated */
	private int numCores;

	/** IPv4 address */
	private String ipAddress;

	/**
     * Create a HostConfig with the specified parameters.
     */
	public HostConfig(int bandwidth, int memory, int numCores, String ipAddress) {
		this.bandwidth = bandwidth;
		this.memory = memory;
		this.numCores = numCores;
		this.ipAddress = ipAddress;
	}

	/** Return the RAM of this host configuration in megabytes. */
	public int getMemory() {
		return this.memory;
	}

	/** Return the number of processor cores of this host configuration. */
	public int getNumCores() {
		return numCores;
	}

	/** Return the IPv4 address of this host configuration. */
	public String getIpAddress() {
		return this.ipAddress;
	}
}
