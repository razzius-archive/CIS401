package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

import java.rmi.Naming;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * A Remote Host on the network.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class RemoteHost extends Node {

    /** Parameter specifying the Host's IPv4 address */
    private final String ip;

    /** The log4j logger associated with this Class. */
    private static Logger logger = Logger.getLogger(RemoteHost.class.getName());

    /** The Host Configuration object associated with this Class. */
    private HostConfig hostConfig;

    /** Parameter specifying the Remote Host interface used for communicating. */
    private RemoteHostInterface rmiServer;

    /** Parameter specifying the virtual machines on this Host. */
    private HashMap<String, VM> vms;

    /**
     * Create a Remote Host with a specified configuration.
     */
    public RemoteHost(HostConfig config) {
        // TODO set static parameters like memory, numcores, bandwidth
        this.vms = new HashMap<String, VM>();
        this.ip = config.getIpAddress();
        this.nodeID = UUID.randomUUID().toString(); // uuid as string
        logger.info("ip is: " + ip);
        try {
        	rmiServer = (RemoteHostInterface)Naming.lookup("rmi://" + ip + "/remote");
        } catch (RemoteException e) {
            e.printStackTrace();
            logger.fatal("Unable to connect to remote server " + e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            System.exit(1);
        }
    }

    /** @return the virtual machines on this Host. */
    public HashMap<String, VM> getVMs() {
        return this.vms;
    }

    /** @return the IP address of this Host. */
    public String getIp() {
        return this.ip;
    }

    /** @return the Host Configuration associated with this Host. */
    public HostConfig getHostConfig() {
        return this.hostConfig;
    }

    /** Boot a virtual machine with the specified parameters. */
    public void bootVM(VM vm) throws RemoteException {
      rmiServer.bootVM(vm);
    }

    /** Shut down the virtual machine. */
    public void shutdownVM(VM vm) throws RemoteException {
        rmiServer.shutdownVM(vm);
    }

    /** Add a network route with the specified parameters. */
    public void addNetworkRoute(VM vm) throws RemoteException {
        rmiServer.addNetworkRoute(vm);
    }

    /** Start a service instance with the specified parameters. */
    public void startService(VM vm, ServiceInstance serviceInstance) throws RemoteException {
        rmiServer.startService(vm, serviceInstance);
    }

    /** Terminate a running service instance with the specified parameters. */
    public void stopService(VM vm, ServiceInstance serviceInstance) throws RemoteException {
        rmiServer.stopService(vm, serviceInstance);
    }

    /** @return the set of virtual machines on this Host. */
    public Set<VM> getRemoteHostVMs() throws RemoteException {
        return rmiServer.getRemoteHostVMs();
    }

    /** @return the percentage memory utilization of this Host. */
    public double getRemoteHostMemoryUtilization() throws RemoteException {
        return rmiServer.getRemoteHostMemoryUtilization();
    }

    /** @return the percentage CPU utilization of this Host. */
    public double getRemoteHostCPUUtilization() throws RemoteException {
        return rmiServer.getRemoteHostCPUUtilization();
    }

    /** @return the percentage network bandwidth utilization of this Host. */
    public double getRemoteHostNetworkUtilization() throws RemoteException {
        return rmiServer.getRemoteHostNetworkUtilization();
    }

    /** @return the set of all service instances running on this Host. */
    public Set<ServiceInstance> getVMServiceInstances() throws RemoteException {
        return rmiServer.getVMServiceInstances();
    }

    /** @return the percentage memory utilization of the specified VM. */
    public double getVMMemoryUtilization() throws RemoteException {
        return rmiServer.getVMMemoryUtilization();
    }

    /** @return the percentage CPU utilization of the specified VM. */
    public double getVMCPUUtilization() throws RemoteException {
        return rmiServer.getVMCPUUtilization();
    }

    /** UNIMPLIMENTED -- Return service traffic statistics associated with this Host. */
    public void getServiceTrafficStatistics() throws RemoteException {
            // TODO : IMPLEMENT
    }

    /** @return the status of the specified virtual machine. */
    public boolean checkVM(VM vm) throws RemoteException {
        logger.info("Attempting to check the VM: " + vm.getID());

        boolean vmRunning = false;

        try {
            vmRunning = rmiServer.checkVM(vm);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return vmRunning;
    }

    /** @return the set of all service instances running on the specified VM. */
    public HashSet<Integer> getVMServiceInstancePIDs(VM vm) {
        logger.info("Attempting to get running Service Instance PIDs from the VM: " + vm.getID());

        HashSet<Integer> serviceInstancePIDs = new HashSet<Integer>();

        try {
            serviceInstancePIDs = rmiServer.getVMServiceInstancePIDs(vm);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return serviceInstancePIDs;
    }
}
