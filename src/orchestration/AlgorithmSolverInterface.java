import java.rmi.*;
/**
 * Interface for the Algorithm Solver to communicate with the State
 * Manager.
 *
 * satisfiable method lets the Algorithm Solver tell the State Manager
 * if the desired workload is manageable.
 * updateConfig method updates the State Manager with the optimal
 * configuration, if one is possible.
 */

public interface AlgorithmSolverInterface {

    public boolean satisfiable(HashMap<Integer, Link> links, HashMap<Integer, Switch> switches,
    	HashMap<Integer, Machine> machines, HashMap<Integer, Service> services);


    public void updateConfig(HashMap<Integer, Link> links, HashMap<Integer, Switch> switches,
    	HashMap<Integer, Machine> machines, HashMap<Integer, Service> services) throws IllegalStateException;
}
