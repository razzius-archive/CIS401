package orchestration;

import orchestration.VM;
import orchestration.RunningService;
/**
 * Interface for the Hardware Cluster to communicate with the State Manager.
 * reconfigure method reorganizes the hardware cluster based on components
 * specified by the State Manager.
 */

public interface HardwareClusterInterface {

    public void bootVM(VM vm, double coresAllocated, int memoryAllocated);
    public void modifyVM(VM vm, double coresAllocated, int memoryAllocated);
    public void shutdownVM(VM vm);
    public void startService(VM vm, RunningService runningservice);
    public void terminateRunningService(VM vm, RunningService runningservice);
    public void transferRunningService(VM old, VM new, RunningService runningservice);

    // Networking and routing methods

}
