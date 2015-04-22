package orchestration;

/**
 * Abstract class for a network node specified in config.json.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public abstract class Node {

    /** Parameter specifying the Node's maximum processing bandwidth in bits/second. */
    protected int bandwidth;

    /** Parameter specifying the Node ID. */
    protected String nodeID;

    /** Return the Node's maximum processing bandwidth in bits/second. */
	public int getBandwidth() {
		return this.bandwidth;
	}

    /** Return the Node ID. */
	public String getID() {
		return nodeID;
	}
}
