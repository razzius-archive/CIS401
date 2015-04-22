package orchestration;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * A simple Algorithm Solver which provides instructions for configuring the State of the cluster to handle incoming traffic.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class AlgorithmSolver implements AlgorithmSolverInterface {

    private static Logger logger = Logger.getLogger(AlgorithmSolver.class.getName());

    /**
     * Default Constructor creates a simple AlgorithmSolver object.
     */
    public AlgorithmSolver() {
    }

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
     * @see orchestration.Node
     * @see orchestration.Request
     */
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

            RemoteHost startHost = remoteHosts.values().iterator().next();
            RemoteHost endHost = startHost;

            // Begin building the new service chain to add to the existing state object.

            List<Node> newServiceChain = new ArrayList<Node>();
            newServiceChain.add(startHost);
            newServiceChain.add(new VirtualSwitch());

            // Add a new VM to the startHost and Action List.

            VM newVM = new VM(1, 1024);    // Cores and Memory
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
