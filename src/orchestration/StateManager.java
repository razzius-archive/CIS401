package orchestration;

import java.io.*;
import java.rmi.*;
import java.rmi.Naming;
import java.util.HashMap;

//
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

	/* 
	* Static Network Attributes do not change during the course
	* of program execution. Links, Switches, Machines, and Tenants
	* are all static attributes with their IDs mapped to the
	* object.
	*/

	private static HashMap<Integer, Link> links;
	private static HashMap<Integer, Switch> switches;
	private static HashMap<Integer, Machine> machines;
	private static HashMap<Integer, Tenant> tenants;

	/* 
	* Dynamic Network Attributes change over the course of program
	* execution. VMs spawned, services processed, and the
	* utilization of different network hardware components
	* are all dynamic attributes. VMs and Services have their IDs
	* mapped to the object. Utilization maps component ID to
	* percent utilization.
	*/

	private static HashMap<Integer, VM> vms;
	private static HashMap<Integer, Service> services;
	private static HashMap<Integer, Double> switchUtilization;
	private static HashMap<Integer, Double> machineUtilization;
	private static HashMap<Integer, Double> linkUtilization;

	/*
	* StateManager Class constructor
	*/

	public StateManager() {
		links = new HashMap<Integer,Link>();
		switches = new HashMap<Integer,Switch>();
		machines = new HashMap<Integer,Machine>();
		tenants = new HashMap<Integer,Tenant>();
		vms = new HashMap<Integer,VM>();
		services = new HashMap<Integer,Service>();
		switchUtilization = new HashMap<Integer,Double>();
		machineUtilization = new HashMap<Integer,Double>();
		linkUtilization = new HashMap<Integer,Double>();

		// Spawn 5 Virtual Machines
		addVM(0, new VM(0, 1.0, 32));
		addVM(1, new VM(1, 1.0, 32));
		addVM(2, new VM(2, 1.0, 32));
		addVM(3, new VM(3, 1.0, 32));
		addVM(4, new VM(4, 1.0, 32));

		// Create 6 Services
		addService(0, new Service(0, 100, 8));
		addService(1, new Service(1, 100, 8));
		addService(2, new Service(2, 100, 16));
		addService(3, new Service(3, 100, 16));
		addService(4, new Service(4, 100, 32));
		addService(5, new Service(5, 100, 64));

		// Create a bunch of switches, machines, and links
		

		// Create some sample requests to take in from the server

		// Use Log4J to log significant events

	}

	/*
	* Initialize virtual machine cluster
	*/
	
	public void initializeCluster() {
		// Network Operations
	}

	public void queryAlgorithmSolver(AlgorithmSolver algorithmSolver, Request request) {
		System.out.println("[StateManager] Error: No Algorithm Solver Implemented");
	}

	/*
	* Read and Update State Parameters
	*/

	public void addLink(int linkID, Link link) {
		links.put(linkID, link);
	}

	public void addSwitch(int switchID, Switch netswitch) {
		switches.put(switchID, netswitch);
	}

	public void addMachine(int machineID, Machine machine) {
		machines.put(machineID, machine);
	}

	public void addTenant(int tenantID, Tenant tenant) {
		tenants.put(tenantID, tenant);
	}

	public void addVM(int vmID, VM vm) {
		vms.put(vmID, vm);
	}

	public void addService(int serviceID, Service service) {
		services.put(serviceID, service);
	}

	public void updateSwitchUtilization(int switchID, double utilization) {
		switchUtilization.put(switchID, utilization);
	}

	public void updateMachineUtilization(int machineID, double utilization) {
		machineUtilization.put(machineID, utilization);
	}

	public void updateLinkUtilization(int linkID, double utilization) {
		linkUtilization.put(linkID, utilization);
	}

	public HashMap<Integer, Link> getLinks() {
		return links;
	}

	public HashMap<Integer, Switch> getSwitches() {
		return switches;
	}

	public HashMap<Integer, Machine> getMachines() {
		return machines;
	}

	public HashMap<Integer, Tenant> getTenants() {
		return tenants;
	}

	public HashMap<Integer, VM> getVMs() {
		return vms;
	}

	public HashMap<Integer, Service> getServices() {
		return services;
	}

	public HashMap<Integer, Double> getSwitchUtilization() {
		return switchUtilization;
	}

	public HashMap<Integer, Double> getMachineUtilization() {
		return machineUtilization;
	}

	public HashMap<Integer, Double> getLinkUtilization() {
		return linkUtilization;
	}

}