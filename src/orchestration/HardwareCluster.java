package orchestration;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import orchestration.Link;
import orchestration.Switch;
import orchestration.ServiceInstance;
import orchestration.VM;
import orchestration.RemoteHost;


public class HardwareCluster implements HardwareClusterInterface {
    private static Logger logger = Logger.getLogger(HardwareCluster.class.getName());

    public HardwareCluster() {
            
    }

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
    /*
    public void modifyVM(VM vm, double coresAllocated, int memoryAllocated) {
        logger.info("[HardwareCluster] Modifying the VM: " + vm.getID());
    }
    */
    public void shutdownVM(VM vm) {
        logger.info("[HardwareCluster] Shutting down the VM: " + vm.getID());
    }
    public void startService(VM vm, ServiceInstance serviceInstance) {
        logger.info("[HardwareCluster] Starting the ServiceInstance: " + serviceInstance.serviceInstanceID);
    }
    public void terminateRunningService(VM vm, ServiceInstance serviceInstance) {
        logger.info("[HardwareCluster] Terminating the ServiceInstance: " + serviceInstance.serviceInstanceID);
    }
    public void transferRunningService(VM oldVM, VM newVM, ServiceInstance serviceInstance) {
        logger.info("[HardwareCluster] Transferring the ServiceInstance: " + serviceInstance.serviceInstanceID);
    }

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

    public HashSet<Integer> getVMServiceInstancePIDs(RemoteHost host, VM vm) {
        return host.getVMServiceInstancePIDs(vm);
    }




}
