package orchestration;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

import orchestration.Link;
import orchestration.Switch;
import orchestration.ServiceInstance;

public class AlgorithmSolver implements AlgorithmSolverInterface {


    private static Logger logger = Logger.getLogger(AlgorithmSolver.class.getName());

    public AlgorithmSolver() {
    }

    // solve() function uses the current optimal (goal) state from the StateManager.
    // The method return a List of Actions and modifies the state in place.
    // If algorithm cannot solve given the load,
    // then the method returns null and an appropriate log message is set.
    // solve() should return null if no solution is possible.

    // static topology includes links, switches, and services

    public ArrayList<Action> solve(
        Set<Link> links,
        Set<Switch> switches,
        Set<Service> services,
        State currentState,
        Request request
    ) {
        try {

            ArrayList<Action> actions = new ArrayList<Action>();

            // Get the list of Remote Hosts from the current state

            Map<String, RemoteHost> remoteHosts = currentState.getRemoteHosts();
            logger.info("Algorithm Solver found this many hosts: " + remoteHosts.size());
            logger.info("Request Start Node: " + request.startNode);
            logger.info("Request End Node: " + request.endNode);
            
            // Identify the start and end hosts.

            RemoteHost startHost = remoteHosts.get(request.startNode);
            RemoteHost endHost = remoteHosts.get(request.endNode);

            // Begin building the new service chain to add to the existing state object.

            List<Node> newServiceChain = new ArrayList<Node>();
            newServiceChain.add(startHost);
            newServiceChain.add(new VirtualSwitch());

            // Add a new VM to the startHost and Action List.

            VM newVM = new VM(1, 1);
            startHost.getVMs().put(newVM.getID(), newVM);
            logger.info("Updating state and queueing an action to boot the VM: " + newVM.getID());
            newServiceChain.add(newVM);
            newServiceChain.add(new VirtualSwitch());
            actions.add(new Action(Action.Type.BOOTVM, startHost, newVM, null, null, null, null, null));

            // Loop through services requested. For each one, add it to the indicated VM and Action List.

            for (String service : request.services) {

                ServiceInstance newServiceInstance = new ServiceInstance(service, newVM.getID());
                newVM.getServiceInstances().put(newServiceInstance.getServiceInstanceID(), newServiceInstance);
                actions.add(new Action(Action.Type.STARTSERVICE, null, null, startHost, newVM, newServiceInstance, null, null));

            }

            // Finally, add the endHost to the new service chain.

            newServiceChain.add(endHost);

            // Now that we have built the new service chain, add it to the state and Action List.

            currentState.getServiceChains().put(request, newServiceChain);
            actions.add(new Action(Action.Type.ADDSERVICECHAIN, null, null, null, null, null, request, newServiceChain));

            return actions;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
