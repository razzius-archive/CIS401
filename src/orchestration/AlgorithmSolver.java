package orchestration;

import java.util.HashMap;
import java.util.ArrayList;


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


    public AlgorithmSolution solve(HashMap<Integer, Link> links, HashMap<Integer, Switch> switches,
        HashMap<Integer, Machine> machines, HashMap<Integer, Service> services, Request req) throws IllegalStateException {

        // compile all requested services
        ArrayList<Service> requestedServices = new ArrayList<Service>();

        for (String serviceID : req.services) {

            // serviceID comes in form "s0" or "s2" need to get integer out
            int serviceint = Integer.parseInt(serviceID.substring(1));

            requestedServices.add(services.get(serviceint));
        }

        // update to put a different service onto a different VM
        AlgorithmSolution as = new AlgorithmSolution();

        for (Service s : requestedServices) {
            VM vm = new VM(1, s.maxMemory);
            as.vms.add(vm);
            as.requestedServices.add(s);
        }

        return as;

    }
}
