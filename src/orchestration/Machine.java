
//
//
// Machine
//
// Stores individual Machine (Hardware) information

public class Machine extends Node {

	private int numCores;

	public Machine(int nodeID, double bandwidth, int numCores) {
		this.nodeID = nodeID;
		this.bandwidth = bandwidth;
		this.numCores = numCores;
	}

	public int getNumCores() {
		return numCores;
	}

}