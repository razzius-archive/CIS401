package orchestration;

/**
 * A bi-directional link between two network nodes.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class Link {

	/** Parameter specifying the Link ID. */
	private int linkID;

	/** Parameter specifying the first Node ID. */
	private int node1ID;

	/** Parameter specifying the second Node ID. */
	private int node2ID;

	/** Parameter specifying the link delay in milliseconds. */
	private int delay;

	/** Parameter specifying the maximum bandwidth of the link in GB/sec. */
	private double bandwidth;

	/**
     * Create a Link with the specified parameters.
     */
	public Link(int linkID, int node1ID, int node2ID, int delay, double bandwidth) {
		this.linkID = linkID;
		this.node1ID = node1ID;
		this.node2ID = node2ID;
		this.delay = delay;
		this.bandwidth = bandwidth;
	}

	/** @return the Link ID. */
	public int getID() {
		return linkID;
	}

	/** @return the first Node ID. */
	public int getNode1ID() {
		return node1ID;
	}

	/** @return the second Node ID. */
	public int getNode2ID() {
		return node2ID;
	}

	/** @return the Link delay in ms. */
	public int getDelay() {
		return delay;
	}

	/** @return the Link bandwidth in GB/sec. */
	public double getBandwidth() {
		return bandwidth;
	}
}