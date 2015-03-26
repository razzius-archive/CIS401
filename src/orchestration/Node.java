package orchestration;

/**
 * Abstract class for any node specified in config.json.
 */

public abstract class Node {
    /**
     * Bandwidth in bps
     *
     * Example: bandwidth of 1024 = 1 Kbps
     */
    protected int bandwidth;
    protected String nodeID;

	public int getBandwidth() {
		return this.bandwidth;
	}

	public String getID() {
		return nodeID;
	}
}
