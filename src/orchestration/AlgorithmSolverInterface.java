package orchestration;

import java.util.HashMap;

/**
 * Interface for the Algorithm Solver to be queried by the StateManager.
 */

public interface AlgorithmSolverInterface {
    /**
     * Solves for a configuration that fulfills the specification.
     * If none can be produced, returns null.
     */
    public AlgorithmSolution solve(HashMap<Integer, Link> links, HashMap<Integer, Switch> switches,
    	HashMap<Integer, RemoteHost> remoteHosts, HashMap<Integer, Service> services, Request req) throws IllegalStateException;
}
