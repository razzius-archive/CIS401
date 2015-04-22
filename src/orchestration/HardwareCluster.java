package orchestration;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;


/**
 * A simple Hardware Cluster which communicates with individual RemoteHosts.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class HardwareCluster implements HardwareClusterInterface {
    private static Logger logger = Logger.getLogger(HardwareCluster.class.getName());

    /**
     * Default Constructor creates a simple HardwareCluster object.
     */
    public HardwareCluster() {}

    /** Tell the specified RemoteHost to boot a new virtual machine with the indicated parameters. */
    public void bootVM(RemoteHost host, VM vm) {
        try {
            logger.info("[HardwareCluster] Booting the VM: " + vm.getID());
            host.bootVM(vm);
            logger.info("[HardwareCluster] Assigned IP address: " + vm.getIpAddress());
        } catch (RemoteException e) {
            // TODO think about how we will handle RemoteExceptions
            logger.fatal(e);
        }
    }

    /** Tell the specified RemoteHost to shut down the virtual machine with the indicated parameters. */
    public void shutdownVM(VM vm) {
        logger.info("[HardwareCluster] Shutting down the VM: " + vm.getID());
    }

    /** Tell the specified virtual machine to start a new service instance the indicated parameters. */
    public void startService(VM vm, ServiceInstance serviceInstance) {
        logger.info("[HardwareCluster] Starting the ServiceInstance: " + serviceInstance.serviceInstanceID);
    }

    /** Tell the specified virtual machine to terminate the running service instance the indicated parameters. */
    public void terminateRunningService(VM vm, ServiceInstance serviceInstance) {
        logger.info("[HardwareCluster] Terminating the ServiceInstance: " + serviceInstance.serviceInstanceID);
    }

    /** Tell the specified virtual machine to transfer the indicated service instance to another virtual machine. */
    public void transferRunningService(VM oldVM, VM newVM, ServiceInstance serviceInstance) {
        logger.info("[HardwareCluster] Transferring the ServiceInstance: " + serviceInstance.serviceInstanceID);
    }

    /** Return whether or not the specified virtual machine is running. */
    public boolean checkVMStatus(RemoteHost host, VM vm) {
        try {
            logger.info("Checking the status of VM: " + vm.getID());
            return host.checkVM(vm);
        } catch (RemoteException e) {
            // TODO think about how we will handle RemoteExceptions
            logger.fatal(e);
            return false;
        }
    }

    /** Return the Set of all ServiceInstances running on a specified virtual machine on the indicated RemoteHost. */
    public HashSet<Integer> getVMServiceInstancePIDs(RemoteHost host, VM vm) {
        return host.getVMServiceInstancePIDs(vm);
    }
}
