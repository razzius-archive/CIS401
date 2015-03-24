package orchestration;

import java.util.Map;
import java.util.List;
import java.util.Set;

/**
 * Interface for the Algorithm Solver to be queried by the StateManager.
 */

public interface AlgorithmSolverInterface {
    /**
     * Solves for a configuration that fulfills the specification.
     * If none can be produced, returns null.
     */
    public boolean solve(
        Set<Link> links,
        Set<Switch> switches,
        Set<Service> services,
        Map<RemoteHost, Set<VM>> vmAssignments,
        Map<VM, Set<ServiceInstance>> serviceAssignments,
        Map<Request, List<Node>> serviceChainAssignments,
        Request request
    );
}
