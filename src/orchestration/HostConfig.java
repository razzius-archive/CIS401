package orchestration;

import java.util.UUID;

/**
 * Represents a machine as specified in the config.json file.
 *
 * Stores the specifics of the machine's hardware and its IP address.
 */

public class HostConfig {

	/**
	 * Network Bandwidth in kB/s
	 */
	private int bandwidth;

	/**
	 * RAM in MB
	 */
	private int memory;

	/**
	 * Number of cores
	 */
	private int numCores;

	/**
	 * IPv4 address
	 */
	private String ipAddress;

	public HostConfig(int bandwidth, int memory, int numCores, String ipAddress) {
		this.bandwidth = bandwidth;
		this.memory = memory;
		this.numCores = numCores;
		this.ipAddress = ipAddress;
	}

	public int getMemory() {
		return this.memory;
	}

	public int getNumCores() {
		return numCores;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}
}
