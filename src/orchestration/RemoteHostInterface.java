package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface RemoteHostInterface extends Remote {

    public void bootVM(VM vm) throws RemoteException;
    public void shutdownVM(VM vm) throws RemoteException;
    public void addNetworkRoute(VM vm) throws RemoteException;
    public void startService(VM vm, ServiceInstance serviceInstance) throws RemoteException;
    public void stopService(VM vm, ServiceInstance serviceInstance) throws RemoteException;
    public List<VM> getRemoteHostVMs() throws RemoteException;
    public double getRemoteHostMemoryUtilization() throws RemoteException;
    public double getRemoteHostCPUUtilization() throws RemoteException;
    public double getRemoteHostNetworkUtilization() throws RemoteException;
    public List<ServiceInstance> getVMServiceInstances() throws RemoteException;
    public double getVMMemoryUtilization() throws RemoteException;
    public double getVMCPUUtilization() throws RemoteException;
    public void getServiceTrafficStatistics() throws RemoteException;

}
