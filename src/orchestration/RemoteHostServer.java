package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.List;

import java.io.*;

import org.apache.log4j.Logger;

import orchestration.VM;
import orchestration.RemoteHostInterface;


public class RemoteHostServer extends UnicastRemoteObject implements RemoteHostInterface {
	static final long serialVersionUID = 0;
	private static transient Logger logger = Logger.getLogger(RemoteHostServer.class.getName());

	public RemoteHostServer() throws RemoteException {
		super();
	}

	public void bootVM(VM vm) throws RemoteException {
		System.out.println("bootVM message received");
		try {
			Process p = Runtime.getRuntime().exec("bootVM.py");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		} catch (Exception e) {

		}
		
	}

	public void shutdownVM(VM vm) throws RemoteException {
        // TODO : IMPLEMENT
    }

    public void addNetworkRoute(VM vm) throws RemoteException {
        // TODO : IMPLEMENT
    }

    public void startService(VM vm, ServiceInstance serviceInstance) throws RemoteException {
        // TODO : IMPLEMENT
    }

    public void stopService(VM vm, ServiceInstance serviceInstance) throws RemoteException {
        // TODO : IMPLEMENT
    }

    public List<VM> getRemoteHostVMs() throws RemoteException {
        // TODO : IMPLEMENT
        return null;
    }

    public double getRemoteHostMemoryUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    public double getRemoteHostCPUUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    public double getRemoteHostNetworkUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    public List<ServiceInstance> getVMServiceInstances() throws RemoteException {
        // TODO : IMPLEMENT
        return null;
    }

    public double getVMMemoryUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    public double getVMCPUUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    public void getServiceTrafficStatistics() throws RemoteException {
        // TODO : IMPLEMENT
    }

	public static void main(String[] args) {
		try {
			RemoteHostInterface server = new RemoteHostServer();
			Registry registry = LocateRegistry.createRegistry(1099);

			Naming.rebind("rmi://localhost/remote", server);
			logger.info("Server is running...");
		} catch (Exception e) {
			System.out.println(e);
			logger.fatal(e);
		}
	}
}
