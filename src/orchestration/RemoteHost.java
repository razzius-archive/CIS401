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

import orchestration.VM;
import orchestration.RemoteHostInterface;



public class RemoteHost extends Node {
    private final String ip;
    private static Logger logger = Logger.getLogger(RemoteHost.class.getName());
    private HostConfig hostConfig;
    private RemoteHostInterface rmiServer;
    private HashMap<String, VM> vms;

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

    public HashMap<String, VM> getVMs() {
        return this.vms;
    }

    public String getIp() {
        return this.ip;
    }

    public HostConfig getHostConfig() {
        return this.hostConfig;
    }

    /**
     * Boot a vm with the specified configuration.
     */
    public void bootVM(VM vm) throws RemoteException {
      rmiServer.bootVM(vm);
  }

  public void shutdownVM(VM vm) throws RemoteException {
    rmiServer.shutdownVM(vm);
}

public void startService(VM vm, ServiceInstance serviceInstance) throws RemoteException {
    rmiServer.startService(vm, serviceInstance);
}

public void stopService(VM vm, ServiceInstance serviceInstance) throws RemoteException {
    rmiServer.stopService(vm, serviceInstance);
}

public Set<VM> getRemoteHostVMs() throws RemoteException {
    return rmiServer.getRemoteHostVMs();
}

public double getRemoteHostMemoryUtilization() throws RemoteException {
    return rmiServer.getRemoteHostMemoryUtilization();
}

public double getRemoteHostCPUUtilization() throws RemoteException {
    return rmiServer.getRemoteHostCPUUtilization();
}

public double getRemoteHostNetworkUtilization() throws RemoteException {
    return rmiServer.getRemoteHostNetworkUtilization();
}

public Set<ServiceInstance> getVMServiceInstances() throws RemoteException {
    return rmiServer.getVMServiceInstances();
}

public double getVMMemoryUtilization() throws RemoteException {
    return rmiServer.getVMMemoryUtilization();
}

public double getVMCPUUtilization() throws RemoteException {
    return rmiServer.getVMCPUUtilization();
}

public void getServiceTrafficStatistics() throws RemoteException {
        // TODO : IMPLEMENT
}

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
