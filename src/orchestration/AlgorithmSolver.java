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
        State currentState,
        Request request
    ) {
        try {

            State newState = new State(currentState.getRemoteHosts());
            newState.copyServiceChains(currentState);

            Map<String, RemoteHost> remoteHosts = currentState.getRemoteHosts();
            logger.info("zize " + remoteHosts.size());
            logger.info("key " + remoteHosts.keySet().iterator().next());
            logger.info("startNode " + request.startNode);
            logger.info("endNode " + request.endNode);

            // initialize startNode and endNode
            RemoteHost currentHost = remoteHosts.get(request.startNode);
            logger.info("currentHost " + currentHost);
            RemoteHost endHost = remoteHosts.get(request.endNode);

            logger.info("endHost " + currentHost);

            logger.info("startnode " + currentHost.getID() + " -> " + endHost.getID());

            List<Node> path = new ArrayList<Node>();
            path.add(currentHost);
            path.add(new VirtualSwitch());

            // loop through services and create VMs for each on startnode
            // if startnode no longer has space move to a different remoteHost
            for (String service : request.services) {

                // TODO: update parameters
                VM vm = new VM(1, 1);

                currentHost.getVMs().put(vm.getID(), vm);
                logger.info("Put vm " + vm.getID());

                ServiceInstance si = new ServiceInstance(service, vm.getID());
                path.add(vm);
                path.add(new VirtualSwitch());

            }

            path.add(endHost);

            // add serviceChain path to new state
            newState.getServiceChains().put(request, path);
            logger.info("done making new ideal state");

            return newState;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
