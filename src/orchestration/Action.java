package orchestration;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.apache.log4j.Logger;

/**
 * An action to be undertaken by the State Manager in order to resolve configuration changes from the Algorithm Solver.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class Action {

    /** Indicates the Type of Action specified in the constructor. */
    public enum Type {
        BOOTVM, SHUTDOWNVM, STARTSERVICE, STOPSERVICE, ADDSERVICECHAIN, REMOVESERVICECHAIN
    }

    /** The log4j logger associated with this Class. */
    private static Logger logger = Logger.getLogger(Action.class.getName());

    /** Parameter specifying the Type of Action. */
    private Type type;

    /** Parameter specifying the RemoteHost on which a VM should be modified, if the Action calls for booting or shutting down a VM. */
    private RemoteHost vmHost = null;

    /** Parameter specifying the properties of the VM to be booted or shut down, if the Action calls for booting or shutting down a VM. */
    private VM newVM = null;

    /** Parameter specifying the RemoteHost on which a ServiceInstance should be modified, if the Action calls for adding or removing a ServiceInstance. */
    private RemoteHost serviceInstanceHost = null;

    /** Parameter specifying the VM on which a ServiceInstance should be modified, if the Action calls for adding or removing a ServiceInstance. */
    private VM serviceInstanceVM = null;

    /** Parameter specifying the ServiceInstance to be modified, if the Action calls for adding or removing a ServiceInstance. */
    private ServiceInstance serviceInstance = null;

    /** Parameter specifying the Request to be acted upon, if the Action calls for adding or removing a service chain. */
    private Request request = null;

    /** Parameter specifying the list of nodes to comprise the service chain, if the Action calls for adding or removing a service chain. */
    private List<Node> serviceChain = null;

    /**
     * Create an Action with the specified parameters.
     */
    public Action(Type type, RemoteHost vmHost, VM newVM, RemoteHost serviceInstanceHost,
        VM serviceInstanceVM, ServiceInstance serviceInstance, Request request, List<Node> serviceChain) {
    	if (type == Type.BOOTVM || type == Type.SHUTDOWNVM) {
            this.type = type;
            this.vmHost = vmHost;
            this.newVM = newVM;
        } else if (type == Type.STARTSERVICE || type == Type.STOPSERVICE) {
            this.type = type;
            this.serviceInstanceHost = serviceInstanceHost;
            this.serviceInstanceVM = serviceInstanceVM;
            this.serviceInstance = serviceInstance;
        } else if (type == Type.ADDSERVICECHAIN || type == Type.REMOVESERVICECHAIN) {
            this.type = type;
            this.request = request;
            this.serviceChain = serviceChain;
        } else {
            logger.info("Null Type was Specified in Action Constructor!");
        }
    }

    /** @return the Type of Action specified; one of {BOOTVM, SHUTDOWNVM, STARTSERVICE, STOPSERVICE, ADDSERVICECHAIN, REMOVESERVICECHAIN} */
    public Type getType() {
        return type;
    }

    /** @return the RemoteHost on which a VM should be modified, if the Action calls for booting or shutting down a VM. */
    public RemoteHost getVmHost() {
        return vmHost;
    }

    /** @return the properties of the VM to be booted or shut down, if the Action calls for booting or shutting down a VM. */
    public VM getNewVM() {
        return newVM;
    }

    /** @return the RemoteHost on which a ServiceInstance should be modified, if the Action calls for adding or removing a ServiceInstance. */
    public RemoteHost getServiceInstanceHost() {
        return serviceInstanceHost;
    }

    /** @return the VM on which a ServiceInstance should be modified, if the Action calls for adding or removing a ServiceInstance. */
    public VM getServiceInstanceVM() {
        return serviceInstanceVM;
    }

    /** @return the ServiceInstance to be modified, if the Action calls for adding or removing a ServiceInstance. */
    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    /** @return the Request to be acted upon, if the Action calls for adding or removing a service chain. */
    public Request getRequest() {
        return request;
    }

    /** @return the list of nodes to comprise the service chain, if the Action calls for adding or removing a service chain. */
    public List<Node> getServiceChain() {
        return serviceChain;
    }






}
