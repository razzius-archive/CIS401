package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Set;

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
		logger.debug("bootVM message received");
		String command = "python scripts/boot_vm.py " + vm.getID() + " " + String.valueOf(vm.getMemory()) + " " + Analytics.getEndpoint();
		try {
			Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			vm.setIpAddress(stdOutput.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void shutdownVM(VM vm) throws RemoteException {
        logger.debug("shutdownVM message received");
        // String command = "python scripts/shutdown_vm.py " + vm.getID();
        // This command gets the list of active VMs and uses awk to get the number used to shut it down
        String command = "xl destroy `xl list | grep " + vm.getID() +  " | awk '{print $2}'`";
		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdInput.readLine()) != null) {
				logger.debug("Here is StdInput from the program: ");
                logger.debug(s);
            }
            while ((s = stdError.readLine()) != null) {
            	logger.debug("Here is StdError from the program: ");
                logger.debug(s);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void addNetworkRoute(VM vm) throws RemoteException {
        logger.debug("addNetworkRoute message received");
        String command = "python scripts/addNetworkRoute.py";
		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdInput.readLine()) != null) {
				logger.debug("Here is StdInput from the program: ");
                logger.debug(s);
            }
            while ((s = stdError.readLine()) != null) {
            	logger.debug("Here is StdError from the program: ");
                logger.debug(s);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void startService(VM vm, ServiceInstance serviceInstance) throws RemoteException {
        logger.debug("startService message received");
        String command = "python scripts/startService.py";
		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdInput.readLine()) != null) {
				logger.debug("Here is StdInput from the program: ");
                logger.debug(s);
            }
            while ((s = stdError.readLine()) != null) {
            	logger.debug("Here is StdError from the program: ");
                logger.debug(s);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void stopService(VM vm, ServiceInstance serviceInstance) throws RemoteException {
        logger.debug("stopService message received");
		String s = null;
		try {
			Process p = Runtime.getRuntime().exec("python stopService.py");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdInput.readLine()) != null) {
				logger.debug("Here is StdInput from the program: ");
                logger.debug(s);
            }
            while ((s = stdError.readLine()) != null) {
            	logger.debug("Here is StdError from the program: ");
                logger.debug(s);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public Set<VM> getRemoteHostVMs() throws RemoteException {
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

    public Set<ServiceInstance> getVMServiceInstances() throws RemoteException {
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
