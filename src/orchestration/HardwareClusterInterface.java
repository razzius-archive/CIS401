import java.rmi.*;
/**
 * Interface for the Hardware Cluster to communicate with the State Manager.
 * ??? method ???
 * ??? method ???
 */

public interface HardwareClusterInterface {

    public void reconfigure(HashMap<Integer, Double> links, HashMap<Integer, Double> linkUtilization, HashMap<Integer, Double> machineUtilization);

    public Cloud getConfiguration(total workload) throws IllegalStateException;
}
