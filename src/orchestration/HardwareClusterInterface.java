package orchestration;

/**
 * Defines the interface for the Hardware Cluster to communicate with the State Manager.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public interface HardwareClusterInterface {

    /** Tell the specified RemoteHost to boot a new virtual machine with the indicated parameters. */
    public void bootVM(RemoteHost host, VM vm);

    /** Tell the specified RemoteHost to shut down the virtual machine with the indicated parameters. */
    public void shutdownVM(VM vm);

    /** Tell the specified virtual machine to start a new service instance the indicated parameters. */
    public void startService(VM vm, ServiceInstance serviceInstance);

    /** Tell the specified virtual machine to terminate the running service instance the indicated parameters. */
    public void terminateRunningService(VM vm, ServiceInstance serviceInstance);

}
