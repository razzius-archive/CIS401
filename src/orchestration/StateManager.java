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
import orchestration.Machine;
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



	class ClusterUpdateThread extends Thread {
    
	    public ClusterUpdateThread() {}

	    public void run() {
	    	while (true) {
		    	while (changesFlag == false) {
		    		try {
						wait();
					} catch (InterruptedException e) {

					}
		    	}
                checkUpdate();
		    }
	    }

        public synchronized void checkUpdate() {
            if (changesFlag == true) {
                setChangesFlag(false);
                updateCluster();
            }
        }

	    public void updateCluster() {
	    	
	    }
	}

	/**
	 * State Manager contains instances of the algorithm solver and hardware cluster.
	 * The changesFlag indicates if the hardware cluster needs to be modified.
	 * The State Manager sets the flag when it receives input from the algorithm solver.
	 * The State Manager also maintains a worker thread which updates the cluster and resets the flag.
	 */

    private static AlgorithmSolver algorithmSolver = new AlgorithmSolver();
    private static HardwareCluster hardwareCluster;
    private static boolean changesFlag = false;
    private static ClusterUpdateThread clusterUpdateThread;

    /**
     * Static Network Attributes that do not change.
     */

    private static Set<Link> links = new HashSet<Link>();
    private static Set<Switch> switches = new HashSet<Switch>();
    private static Set<Machine> machines = new HashSet<Machine>();
    private static Set<Tenant> tenants = new HashSet<Tenant>();
    private static Set<Service> services = new HashSet<Service>();

    /**
     * Dynamic Network Attributes that change over the course of a trial.
     */

    private static Map<RemoteHost, Set<VM>> vmAssignments = new HashMap<RemoteHost, Set<VM>>();
    private static Map<VM, Set<ServiceInstance>> serviceAssignments = new HashMap<VM, Set<ServiceInstance>>();
    private static Map<Request, List<Node>> serviceChainAssignments = new HashMap<Request, List<Node>>();


    public StateManager(HardwareCluster hardwareCluster) {
        this.hardwareCluster = hardwareCluster;
        clusterUpdateThread = new ClusterUpdateThread();
        clusterUpdateThread.run();
    }

    /*
    * Initialize virtual machine cluster
    */

    public void initializeCluster() {
        // Perform Network Operations
        // Update the internal state accordingly.
    }

    /**
     * Let the update thread know that there are changes to be enacted.
     */
    private static synchronized void setChangesFlag(boolean flag) {
        changesFlag = flag;
    }

    public static CustomerResponse queryAlgorithmSolver(Request request) {
        boolean solvable = algorithmSolver.solve(
            links,
            switches,
            services,
            vmAssignments,
            serviceAssignments,
            serviceChainAssignments,
            request);
        if (solvable) {
            setChangesFlag(true);
            clusterUpdateThread.notify();
            return new CustomerResponse(true);
        } else {
            return new CustomerResponse(false);
        }
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

    public Set<Machine> getMachines() {
        return machines;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public Set<Service> getServices() {
        return services;
    }

}
