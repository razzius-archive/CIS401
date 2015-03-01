package orchestration;

import java.util.HashMap;
import java.util.ArrayList;

import orchestration.Link;
import orchestration.Switch;
import orchestration.Machine;
import orchestration.ServiceInstance;
import orchestration.VM;

public class HardwareCluster implements HardwareClusterInterface {

    public HardwareCluster() {
    }


    public void bootVM(VM vm, double coresAllocated, int memoryAllocated) {

    }
    public void modifyVM(VM vm, double coresAllocated, int memoryAllocated) {

    }
    public void shutdownVM(VM vm) {

    }
    public void startService(VM vm, ServiceInstance serviceInstance) {

    }
    public void terminateRunningService(VM vm, ServiceInstance serviceInstance) {

    }
    public void transferRunningService(VM oldVM, VM newVM, ServiceInstance serviceInstance) {

    }
}
