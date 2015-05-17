package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashSet;
import java.util.Set;

import java.io.*;

import org.apache.log4j.Logger;

import orchestration.VM;
import orchestration.RemoteHostInterface;

/**
 * To be instlaled on Xen hosts; provides commands that can be accepted.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class RemoteHostServer extends UnicastRemoteObject implements RemoteHostInterface {
	static final long serialVersionUID = 0;
	private static transient Logger logger = Logger.getLogger(RemoteHostServer.class.getName());

	/** Create a remote host server on a Xen host with the specified parameters. */
	public RemoteHostServer() throws RemoteException {
		super();
	}

	/** Boot a virtual machine with the specified parameters. */
	public void bootVM(VM vm) throws RemoteException {
		logger.debug("bootVM message received");
		String command = "python scripts/boot_vm.py " + vm.getID() + " " + String.valueOf(vm.getMemory()) + " " + Analytics.getEndpoint();
		try {
			Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			vm.setIpAddress(stdInput.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Shut down the virtual machine. */
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

    /** Add the specified network route on the indicated VM. */
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

    /** Start a service instance with the specified parameters. */
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

    /** Stop the service instance with the specified parameters. */
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

    /** UNIMPLEMENTED */
    public Set<VM> getRemoteHostVMs() throws RemoteException {
        // TODO : IMPLEMENT
        return null;
    }

    /** UNIMPLEMENTED */
    public double getRemoteHostMemoryUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    /** UNIMPLEMENTED */
    public double getRemoteHostCPUUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    /** UNIMPLEMENTED */
    public double getRemoteHostNetworkUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    /** UNIMPLEMENTED */
    public Set<ServiceInstance> getVMServiceInstances() throws RemoteException {
        // TODO : IMPLEMENT
        return null;
    }

    /** UNIMPLEMENTED */
    public double getVMMemoryUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    /** UNIMPLEMENTED */
    public double getVMCPUUtilization() throws RemoteException {
        // TODO : IMPLEMENT
        return 0;
    }

    /** UNIMPLEMENTED */
    public void getServiceTrafficStatistics() throws RemoteException {
        // TODO : IMPLEMENT
    }

    /** @return the status of the specified virtual machine; true if running, false if stopped. */
    public boolean checkVM(VM vm) throws RemoteException {
		logger.debug("checkVM message received");
		String command = "xl list | grep " + vm.getID();
		logger.info("Running the command: " + command);
		try {

			Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			if (stdInput.readLine() == null) {
				return false;
			} else return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/** @return the set of all service instances running on the specified VM. */
	public HashSet<Integer> getVMServiceInstancePIDs(VM vm) throws RemoteException {
		logger.debug("getVMServiceInstancePIDs message received");
		// String command = "python scripts/get_vm_service_instance_pids.py " + vm.getID() + " " + String.valueOf(vm.getMemory()) + " " + Analytics.getEndpoint();
		// try {

		// 	// TODO : PASS IN ARGUMENT

		// 	Process p = Runtime.getRuntime().exec(command);
  //           BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		// 	BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		// 	BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		// 	return stdInput.readLine();
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// }
		return null;
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
