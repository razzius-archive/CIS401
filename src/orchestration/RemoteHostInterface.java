package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.HashSet;
import java.util.Set;

/**
 * A Remote Host Interface for communicating on the network.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public interface RemoteHostInterface extends Remote {

    /** Boot a virtual machine with the specified parameters. */
    public void bootVM(VM vm) throws RemoteException;

    /** Shut down the existing virtual machine with the specified parameters. */
    public void shutdownVM(VM vm) throws RemoteException;

    /** Add a network route with the specified parameters. */
    public void addNetworkRoute(VM vm) throws RemoteException;

    /** Start a service instance with the specified parameters. */
    public void startService(VM vm, ServiceInstance serviceInstance) throws RemoteException;

    /** Terminate a running service instance with the specified parameters. */
    public void stopService(VM vm, ServiceInstance serviceInstance) throws RemoteException;

    /** @return the set of virtual machines on this Host. */
    public Set<VM> getRemoteHostVMs() throws RemoteException;

    /** @return the percentage memory utilization of this Host. */
    public double getRemoteHostMemoryUtilization() throws RemoteException;

    /** @return the percentage CPU utilization of this Host. */
    public double getRemoteHostCPUUtilization() throws RemoteException;

    /** @return the percentage network bandwidth utilization of this Host. */
    public double getRemoteHostNetworkUtilization() throws RemoteException;

    /** @return the set of all service instances running on this Host. */
    public Set<ServiceInstance> getVMServiceInstances() throws RemoteException;

    /** @return the percentage memory utilization of the specified VM. */
    public double getVMMemoryUtilization() throws RemoteException;

    /** @return the percentage CPU utilization of the specified VM. */
    public double getVMCPUUtilization() throws RemoteException;

    /** @return service traffic statistics associated with this Host. */
    public void getServiceTrafficStatistics() throws RemoteException;

    /** @return the status of the specified virtual machine. */
    public boolean checkVM(VM vm) throws RemoteException;

    /** @return the set of all service instances running on the specified VM. */
    public HashSet<Integer> getVMServiceInstancePIDs(VM vm) throws RemoteException;

}
