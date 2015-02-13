
//
//
// Link
//
// Link between network nodes

public class Link {

	private int linkID;
	private int node1ID;
	private int node2ID;
	private int delay;			// Link Delay in ms
	private double bandwidth;	// Link Bandwidth in GB/sec

	public Link(int linkID, int node1ID, int node2ID, double bandwidth) {
		this.linkID = linkID;
		this.node1ID = node1ID;
		this.node2ID = node2ID;
		this.node3ID = node3ID;
	}

	public int getID() {
		return linkID;
	}

	public int getNode1ID() {
		return node1ID;
	}

	public int getNode2ID() {
		return node2ID;
	}

	public double getBandwidth() {
		return bandwidth;
	}
}