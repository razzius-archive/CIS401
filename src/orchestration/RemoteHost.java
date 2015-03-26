package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

import java.rmi.Naming;

import java.util.List;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import orchestration.VM;
import orchestration.RemoteHostInterface;


public class RemoteHost extends Node {
    private final String ip;
    private static Logger logger = Logger.getLogger(RemoteHost.class.getName());
    private HostConfig hostConfig;
    private RemoteHostInterface rmiServer;
    private HashMap<String, VM> vms;

    public RemoteHost(RemoteHost other) {
        this.hostConfig = other.getHostConfig();
        this.ip = hostConfig.getIpAddress();
        this.nodeID = "1";
        logger.info("ip is: " + ip);
        try {
            rmiServer = (RemoteHostInterface)Naming.lookup("rmi://" + ip + "/remote");
        } catch (RemoteException e) {
            e.printStackTrace();
            logger.fatal("Unable to connect to remote server " + e);
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
            logger.fatal(e);
            System.exit(1);
        }


        HashMap<String, VM> otherVMs = other.getVMs();
        for (String i : otherVMs.keySet()) {
            vms.put(i, new VM(otherVMs.get(i)));
        }
    }

    public RemoteHost(HostConfig config) {
        // TODO set static parameters like memory, numcores, bandwidth
        this.ip = config.getIpAddress();
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

    public void bootVM(VM vm) throws RemoteException {
      rmiServer.bootVM(vm);
  }

  public void shutdownVM(VM vm) throws RemoteException {
    rmiServer.shutdownVM(vm);
}

public void addNetworkRoute(VM vm) throws RemoteException {
    rmiServer.addNetworkRoute(vm);
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


}
