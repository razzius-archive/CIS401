package orchestration;

//
//
// Machine
//
// Stores individual Machine (Hardware) information

public class Machine extends Node {

	private int numCores;
	private int memory;  // in MB

	public Machine(int nodeID, double bandwidth, int numCores, int memory) {
		this.nodeID = nodeID;
		this.bandwidth = bandwidth;
		this.numCores = numCores;
		this.memory = memory;
	}

	public int getNumCores() {
		return numCores;
	}

}