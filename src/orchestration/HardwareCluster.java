package orchestration;

import java.util.HashMap;
import java.util.ArrayList;

import orchestration.Link;
import orchestration.Switch;
import orchestration.Machine;
import orchestration.ServiceInstance;
import orchestration.VM;

public class HardwareCluster implements HardwareClusterInterface {
    
    private static Logger logger = Logger.getLogger(RequestHandler.class.getName());

    public HardwareCluster() {
    }

    public void bootVM(VM vm, double coresAllocated, int memoryAllocated) {
        logger.info("[HardwareCluster] Booting the VM: " + vm.getID());
    }
    public void modifyVM(VM vm, double coresAllocated, int memoryAllocated) {
        logger.info("[HardwareCluster] Modifying the VM: " + vm.getID());
    }
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
}
