package orchestration;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import orchestration.Link;
import orchestration.Switch;
import orchestration.Machine;
import orchestration.ServiceInstance;

public class AlgorithmSolver implements AlgorithmSolverInterface {

    double myLoad;

    // TODO: Define SystemLoad class.
    public AlgorithmSolver() {
        //myLoad = load;
    }

    // TODO: Define Configuration class.
    // solve() function should use the current state of the fleet (myLoad)
    // and return a Configuration object that gives the suggested configuration
    // for the fleet.
    // If algorithm cannot solve given the load,
    // then the method returns null and an appropriate log message is set.

    // static topology includes links, switches, and services

    public State solve(
        List<Link> links,
        List<Switch> switches,
        List<Service> services,
        State currentState
    ) {
        return null;
    /* TODO Rewrite
        // compile all requested services
        ArrayList<Service> requestedServices = new ArrayList<Service>();

        for (String serviceID : req.services) {

            // serviceID comes in form "s0" or "s2" need to get integer out
            int serviceint = Integer.parseInt(serviceID.substring(1));

            requestedServices.add(services.get(serviceint));
        }


        // Naive solution: put each different service on a different VM, all on the first host
        AlgorithmSolution as = new AlgorithmSolution();

        // For testing purposes, the machine running the orchestration layer can act as a remote host
        // TODO ensure the localhost has id 0
        // TODO all these hashmaps should be hashsets, ask Razzi
        RemoteHost localhost = remoteHosts.iterator().next();
        ArrayList<VM> vmsAssignedToLocalhost = new ArrayList<VM>();
        as.vms.put(localhost, vmsAssignedToLocalhost);

        for (Service s : requestedServices) {
            VM vm = new VM(1, s.maxMemory);
            vmsAssignedToLocalhost.add(vm);

            ServiceInstance si = new ServiceInstance(s.serviceID, vm.getID());
            as.requestedServices.add(si);
        }
        return as;
    */
    }
}
