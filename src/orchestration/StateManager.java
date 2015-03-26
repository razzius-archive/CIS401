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
            System.out.println("thread run");
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
            System.out.println("ACTUALLY UPDATE THE CLUSTER");
            // ACTUALLY UPDATE THE CLUSTER
            // use idealState

            

            for (RemoteHost idealHost : idealState.getRemoteHosts().values()) {
	            RemoteHost currentHost = currentState.getRemoteHosts().get(idealHost.getID());

	            // Get the VM deltas from each host

            	Set<VM> idealVMs = (Set<VM>) idealHost.getVMs().values();
            	Set<VM> currentVMs = (Set<VM>) currentHost.getVMs().values();

            	// Boot the new VMs

	            for (VM idealVM : idealHost.getVMs().values()) {
	                if (!currentVMs.contains(idealVM)) {
	                	hardwareCluster.bootVM(currentHost, idealVM);
	                }
	            }

	            // Boot the new services


	        }
        }
    }

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
     * Dynamic Network Attributes that change over the course of a trial.
     */

    // State
    private State currentState = new State();
    private State idealState = new State();

    public StateManager(HardwareCluster hardwareCluster) {
        System.out.println("[stateManager] starting stateManager");
        this.hardwareCluster = hardwareCluster;
        this.clusterUpdateThread = new ClusterUpdateThread();
        System.out.println("clusterUpdateThread: " + clusterUpdateThread);
        clusterUpdateThread.start();
        System.out.println("thread going");
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
        System.out.println("Changes ready set");
    }

    public CustomerResponse queryAlgorithmSolver(Request request) {
        System.out.println("[queryAlgorithmSolver] calling solve");
        State newState = algorithmSolver.solve(
            links,
            switches,
            services,
            idealState,
            request);
        System.out.println("[queryAlgorithmSolver] solve returned");
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
