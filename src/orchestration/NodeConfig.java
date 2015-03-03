package orchestration;

/**
 * Abstract class for any node specified in config.json.
 */

public abstract class NodeConfig {
    /**
     * Bandwidth in bps
     *
     * Example: bandwidth of 1024 = 1 Kbps
     */
    protected int bandwidth;

	public int getBandwidth() {
		return this.bandwidth;
	}
}
