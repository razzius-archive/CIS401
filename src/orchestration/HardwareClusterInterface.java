package orchestration;

import java.rmi.*;
/**
 * Interface for the Hardware Cluster to communicate with the State Manager.
 * reconfigure method reorganizes the hardware cluster based on components
 * specified by the State Manager.
 */

public interface HardwareClusterInterface {

    public void bootVM(VM vm);
    public void shutdownVM(VM vm);
    public void startService(VM vm, RunningService runningservice);
    public void terminateRunningService(VM vm, RunningService runningservice);
    public void transferRunningService(VM old, VM new, RunningService runningservice);

    // Networking and routing methods

}
