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
            while (true) {
                try {
                    synchronized (changesFlag) {
                        changesFlag.wait();
                        updateCluster();
                        changesFlag = false;
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }

        public void updateCluster() {
            logger.info("Updating the cluster");

            logger.info("Ideal remote hosts: " + idealState.getRemoteHosts().size());
            logger.info("Current remote hosts: " + currentState.getRemoteHosts().size());
            for (RemoteHost idealHost : idealState.getRemoteHosts().values()) {

                logger.info("Updating ideal host " + idealHost.getID());
	            RemoteHost currentHost = currentState.getRemoteHosts().get(idealHost.getID());

	            // Get the VM Maps from each host

            	HashMap<String, VM> idealVMs = idealHost.getVMs();
            	HashMap<String, VM> currentVMs = currentHost.getVMs();

            	// Boot the new VMs and install services on them

	            for (String idealVMID : idealVMs.keySet()) {
	            	VM idealVM = idealVMs.get(idealVMID);

	            	// If there is already a VM with the requisite ID, then get it from the current host and just boot services.
	            	// Otherwise, boot the VM, then boot the services.
	            	if (currentVMs.keySet().contains(idealVMs.get(idealVMID))) {
	            		VM currentVM = currentVMs.get(idealVMID);
	            		for (String idealServiceInstanceID : idealVM.getServiceInstances().keySet()) {
	            			// Check if there already exists a ServiceInstance with the requisite ID.
	            			if (!currentVM.getServiceInstances().keySet().contains(idealServiceInstanceID)) {
		                		ServiceInstance idealService = idealVM.getServiceInstances().get(idealServiceInstanceID);
			                	hardwareCluster.startService(idealVM, idealService);
			                }
		                }
	            	} else {
	            		logger.info("Booting new VM");
	            		hardwareCluster.bootVM(currentHost, idealVM);
	                	for (String idealServiceInstanceID : idealVM.getServiceInstances().keySet()) {
	                		ServiceInstance idealService = idealVM.getServiceInstances().get(idealServiceInstanceID);
		                	hardwareCluster.startService(idealVM, idealService);
		                }
	            	}
	            }
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
    private Boolean changesFlag = false;
    private ClusterUpdateThread clusterUpdateThread;

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

    private State currentState;
    private State idealState;

    public StateManager(HardwareCluster hardwareCluster) {
        this.hardwareCluster = hardwareCluster;
        this.currentState = new State(hardwareCluster.getRemoteHosts());
        this.idealState = new State(hardwareCluster.getRemoteHosts());
        this.clusterUpdateThread = new ClusterUpdateThread();
        clusterUpdateThread.start();
        logger.info("clusterUpdateThread started");
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
    private void setChangesFlag() {
        synchronized (changesFlag) {
            changesFlag.notify();
        }
        logger.info("changes ready set");
    }

    public CustomerResponse queryAlgorithmSolver(Request request) {
        State newState = algorithmSolver.solve(
            links,
            switches,
            services,
            idealState,
            request);
        logger.info("AlgorithmSolver returned: " + newState);
        if (newState != null) {
            this.idealState = newState;
            try {
                setChangesFlag();
            } catch (Exception e) {
                e.printStackTrace();
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
