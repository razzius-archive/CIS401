package orchestration;

import orchestration.VM;
import orchestration.RemoteHost;

/**
 * Interface for the Hardware Cluster to communicate with the State Manager.
 * reconfigure method reorganizes the hardware cluster based on components
 * specified by the State Manager.
 */

public interface HardwareClusterInterface {

    // VM Actions

    public void bootVM(RemoteHost host, VM vm);
    // public void modifyVM(VM vm, double coresAllocated, int memoryAllocated);
    public void shutdownVM(VM vm);

    // Service Actions

    public void startService(VM vm, ServiceInstance serviceInstance);
    public void terminateRunningService(VM vm, ServiceInstance serviceInstance);

    // Network Actions
    // public void addFlow(RemoteHost host, Flow flow);


}
