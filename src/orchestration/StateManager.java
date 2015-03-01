package orchestration;

import java.rmi.Naming;
import java.util.HashMap;

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
	private static AlgorithmSolver algorithmSolver = new AlgorithmSolver();
	// private static HardwareCluster hardwareCluster = new HardwareCluster();

	/*
	* Static Network Attributes do not change during the course
	* of program execution. Links, Switches, Machines, and Tenants
	* are all static attributes with their IDs mapped to the
	* object.
	*/

	private static HashMap<Integer, Link> links = new HashMap<Integer, Link>();
	private static HashMap<Integer, Switch> switches = new HashMap<Integer, Switch>();
	private static HashMap<Integer, Machine> machines = new HashMap<Integer, Machine>();
	private static HashMap<Integer, Tenant> tenants = new HashMap<Integer, Tenant>();

	/*
	* Dynamic Network Attributes change over the course of program
	* execution. VMs spawned, services processed, and the
	* utilization of different network hardware components
	* are all dynamic attributes. VMs and Services have their IDs
	* mapped to the object. Utilization maps component ID to
	* percent utilization.
	*/

	private static HashMap<Integer, VM> vms = new HashMap<Integer, VM>();
	private static HashMap<Integer, Service> services = new HashMap<Integer, Service>();
	private static HashMap<Integer, ServiceInstance> serviceInstances = new HashMap<Integer, ServiceInstance>();
	private static HashMap<Integer, Double> switchUtilization = new HashMap<Integer, Double>();
	private static HashMap<Integer, Double> machineUtilization = new HashMap<Integer, Double>();
	private static HashMap<Integer, Double> linkUtilization = new HashMap<Integer, Double>();

	/*
	* StateManager Class constructor
	*/

	public StateManager() {
	}

	/*
	* Initialize virtual machine cluster
	*/

	public void initializeCluster() {
		// Network Operations
	}

	// TODO
	private static void updateCluster(AlgorithmSolution solution) throws IllegalStateException {
		// Calculate the differences between the current configuration and the new configuration.
		// Direct the Hardware Cluster as necessary.

		// Step 1: Boot new VMs
			for (VM vm : solution.vms) {
				if (!this.vms.contains(vm)) {
					hardwareCluster.boot(vm, vm.getCores, vm.getMemory);
				}
			}

		// Step 2: Start or Transfer services

		// Step 3: Shutdown VMs

	}

	public static CustomerResponse queryAlgorithmSolver(Request request) {
		AlgorithmSolution solution = algorithmSolver.solve(
			links,
			switches,
			machines,
			services,
			request);
		if (solution == null) {
			return new CustomerResponse(false);
		} else {
			updateCluster(solution);
			return new CustomerResponse(true);
		}
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
