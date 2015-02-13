package orchestration;

//
//
// Switch
//
// Stores individual Network Switch (Hardware) information

public class Switch extends Node {

	// From highest to lowest level of cloud topology: Top-of-rack, Top-of-row, Core

	public enum SwitchType {
    	TOP_OF_ROW, TOP_OF_RACK, CORE
	}

	private SwitchType type;

	public Switch(int nodeID, double bandwidth, SwitchType type) {
		this.nodeID = nodeID;
		this.bandwidth = bandwidth;
		this.type = type;
	}

	public SwitchType getSwitchType() {
		return type;
	}

}