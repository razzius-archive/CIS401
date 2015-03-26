package orchestration;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import orchestration.Link;
import orchestration.Switch;
import orchestration.ServiceInstance;

public class AlgorithmSolver implements AlgorithmSolverInterface {

    public AlgorithmSolver() {
    }

<<<<<<< Updated upstream
    // TODO: Define Configuration class.
    // solve() function should use the current state of the fleet (myLoad)
    // and return a Configuration object that gives the suggested configuration
    // for the fleet.
    // If algorithm cannot solve given the load,
    // then the method returns null and an appropriate log message is set.

    // static topology includes links, switches, and services

    public State solve(
        Set<Link> links,
        Set<Switch> switches,
        Set<Service> services,
        State currentState
    ) {
        return null;

        
        // initialize startnode and endnode
        RemoteHost currentHost = remoteHosts.get(request.startNode);
        RemoteHost endHost = remoteHosts.get(request.endNode);

        List<Node> path = new ArrayList<Node>();
        path.add(currentHost);
        path.add(new VirtualSwitch());

        // loop through services and create VMs for each on startnode
        // if startnode no longer has space move to a different remoteHost
        for (String service : request.services) {
            
            // TODO: update parameters
            VM vm = new VM(1, 1);
            

            ServiceInstance si = new ServiceInstance(service, vm.getID());
            path.add(vm);
            path.add(new VirtualSwitch());

    
        }
    }
}
