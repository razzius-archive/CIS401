package orchestration;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

/**
 * This class defines the interface for the AlgorithmSolver to be queried by the StateManager.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public interface AlgorithmSolverInterface {

    /**
     * Solves for a network configuration that fulfills the customer specification.
     * 
     * @param links         a set of all the links in the network
     * @param switches      a set of all the switches in the network
     * @param services      a set of all the services already running on the network hardware
     * @param currentState  the current State of the deployment, incorporating running RemoteHosts and ServiceChains
     * @param request       the request object constructed from the customer input
     * @return              a list of modifications to the state needed to achieve the specification, or null if none is available.
     * @see orchestration.RemoteHost
     * @see orchestration.ServiceChain
     * @see orchestration.Request
     */
    public ArrayList<Action> solve(
        Set<Link> links,
        Set<Switch> switches,
        Set<Service> services,
        State currentState,
        Request request
    );
}
