package orchestration;

//
//
// Node
//
// Abstract class for a network hardware node (Switch or Machine)

public abstract class Node {

	protected int nodeID;
	protected double bandwidth;

	public int getID() {
		return nodeID;
	}

	public double getBandwidth() {
		return bandwidth;
	}
}