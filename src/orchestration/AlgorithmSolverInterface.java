package orchestration;

import java.util.Map;
import java.util.Set;

import orchestration.State;

/**
 * Interface for the Algorithm Solver to be queried by the StateManager.
 */

public interface AlgorithmSolverInterface {
    /**
     * Solves for a configuration that fulfills the specification.
     * If none can be produced, returns null.
     */
    public State solve(
        Set<Link> links,
        Set<Switch> switches,
        Set<Service> services,
        State currentState
    );
}
