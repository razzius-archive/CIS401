package orchestration;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import orchestration.Link;
import orchestration.Switch;
import orchestration.Tenant;
import orchestration.CustomerResponse;

//
// State Manager
//
// The state manager is central component for the orchestration
// layer and the interface with the computing cluster. Parsed
// customer requests will come in from the request handler and
// the state manager decides whether the system can handle them
// or not by communicating with the algorithm solver. If the
// algorithm solver is able to produce a configuration that is
// expected to meet the requirements, the state manager will modify
// the fleet of machines to prepare to process traffic. As the
// configuration changes, the state manager will monitor the
// cluster of machines. The state manager will send information
// about the current state of the machines to the analytics component.
//

public class StateManager {

    private class ClusterUpdateThread extends Thread {

        public ClusterUpdateThread() {}

        public void run() {
        	logger.info("clusterUpdateThread started");
            while (true) {
                try {
                    synchronized (updates) {
                        updates.wait();
                        updateCluster();
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }

        public void updateCluster() {
            logger.info("Updating the cluster");

            Action update = updates.remove(0);

            if (update.getType() == Action.Type.BOOTVM) {
            	// Boot the requisite VM
            	RemoteHost targetHost = update.getVmHost();
            	VM targetVM = update.getNewVM();
            	hardwareCluster.bootVM(targetHost, targetVM);
            } else if (update.getType() == Action.Type.STARTSERVICE) {
            	// Start the requisite service on the VM
            	RemoteHost targetHost = update.getServiceInstanceHost();
            	VM targetVM = update.getServiceInstanceVM();
            	ServiceInstance targetServiceInstance = update.getServiceInstance();
            	hardwareCluster.startService(targetVM, targetServiceInstance);
            } else if (update.getType() == Action.Type.ADDSERVICECHAIN) {
            	// Start the service chain
            	logger.info("Attempting to add a service chain -- not implemented!");



            } else {
            	logger.info("Unimplemented Action!");
            }
        }
    }



    private static Logger logger = Logger.getLogger(StateManager.class.getName());

    /**
     * State Manager contains instances of the algorithm solver and hardware cluster.
     * The changesFlag indicates if the hardware cluster needs to be modified.
     * The State Manager sets the flag when it receives input from the algorithm solver.
     * The State Manager also maintains a worker thread which updates the cluster and resets the flag.
     */

    private AlgorithmSolver algorithmSolver = new AlgorithmSolver();
    private HardwareCluster hardwareCluster;
    private ArrayList<Action> updates = new ArrayList<Action>();;
    private ClusterUpdateThread clusterUpdateThread = new ClusterUpdateThread();

    /**
     * Network Attributes that do not change.
     */

    private Set<Link> links = new HashSet<Link>();
    private Set<Switch> switches = new HashSet<Switch>();
    private Set<Tenant> tenants = new HashSet<Tenant>();
    private Set<Service> services = new HashSet<Service>();

    /**
     * Dynamic Network Attributes that change over the course of a trial are contained in the currentState.
     */

    private State state;

    public StateManager(HardwareCluster hardwareCluster, ArrayList<HostConfig> hostConfigs) {
    	Map<String, RemoteHost> remoteHosts = new HashMap<String, RemoteHost>();
		for (HostConfig config : hostConfigs) {
            RemoteHost host = new RemoteHost(config);
            remoteHosts.put(host.getID(), host);
        }
        this.state = new State(remoteHosts);
        this.hardwareCluster = hardwareCluster;
        clusterUpdateThread.start();
    }

    /*
    * Initialize virtual machine cluster
    */

    public void initializeCluster() {
        // Perform Network Operations
        // Update the internal state accordingly.
    }

    public CustomerResponse queryAlgorithmSolver(Request request) {
        List<Action> actions = algorithmSolver.solve(
            links,
            switches,
            services,
            state,
            request);
        if (actions != null) {
        	synchronized (updates) {
        		for (Action action : actions) {
        			updates.add(action);
        		}
        		updates.notify();
        	}
            return new CustomerResponse(true);
        } else {
            return new CustomerResponse(false);
        }
    }

    /**
     *  Poll the hardware cluster to update ideal state
     */

    public void poll() {
        //for (RemoteHost host : currentState.getRemoteHosts()) {
            // update ideal state (as well as current state)
        //}
    }

    /**
     * Methods to read and update state parameters.
     */

    public void addLink(Link link) {
        links.add(link);
    }

    public void addSwitch(Switch netswitch) {
        switches.add(netswitch);
    }

    public void addTenant(Tenant tenant) {
        tenants.add(tenant);
    }

    public void addService(Service service) {
        services.add(service);
    }

    public Set<Link> getLinks() {
        return links;
    }

    public Set<Switch> getSwitches() {
        return switches;
    }

    public Set<Tenant> getTenants() {

        return tenants;
    }

    public Set<Service> getServices() {
        return services;
    }

}
