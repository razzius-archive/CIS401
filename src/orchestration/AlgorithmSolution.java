package orchestration;

import java.util.HashMap;
import java.util.ArrayList;

/*

AlgorithmSolution objects are created by the AlgorithmSolver.

*/

public class AlgorithmSolution {
	public HashMap<RemoteHost, ArrayList<VM>> vms = new HashMap<RemoteHost, ArrayList<VM>>();
	public ArrayList<ServiceInstance> requestedServices = new ArrayList<ServiceInstance>();

	public AlgorithmSolution() {

	}
}
