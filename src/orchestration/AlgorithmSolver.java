package orchestration;

import java.util.HashMap;

public class AlgorithmSolver implements AlgorithmSolverInterface {

    double myLoad;

    // TODO: Define SystemLoad class. 
    public AlgorithmSolver() {
        //myLoad = load;
    }
    
    // TODO: Define Configuration class.
    // solve() function should use the current state of the fleet (myLoad)
    //  and return a Configuration object that gives the suggested configuration
    //  for the fleet.
    // If algorithm cannot solve given the load, 
    //  then the method returns null and an appropriate log message is set.
    public void solve() {
        
    }

    public boolean satisfiable(HashMap<Integer, Link> links, HashMap<Integer, Switch> switches,
        HashMap<Integer, Machine> machines, HashMap<Integer, Service> services) {

        return false;
    }


    public void updateConfig(HashMap<Integer, Link> links, HashMap<Integer, Switch> switches,
        HashMap<Integer, Machine> machines, HashMap<Integer, Service> services) throws IllegalStateException {

    }
}
