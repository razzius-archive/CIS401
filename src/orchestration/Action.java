package orchestration;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.apache.log4j.Logger;

import orchestration.State;
import orchestration.Node;
import orchestration.Request;

// An Action is undertaken by the state manager to resolve changes from the Algorithm Solver.

public class Action {

    public enum Type {
        BOOTVM, SHUTDOWNVM, STARTSERVICE, STOPSERVICE, ADDSERVICECHAIN, REMOVESERVICECHAIN
    }

    private static Logger logger = Logger.getLogger(Action.class.getName());

    private Type type;

    // If the Action calls for booting or shutting down a VM, use these parameters.

    private RemoteHost vmHost = null;
    private VM newVM = null;

    // If the Action calls for adding or removing a ServiceInstance on a VM, use these parameters.

    private RemoteHost serviceInstanceHost = null;
    private VM serviceInstanceVM = null;
    private ServiceInstance serviceInstance = null;

    // If the Action calls for adding or removing a service chain, use these parameters.

    private Request request = null;
    private List<Node> serviceChain = null;

    // Constructor fills in parameters based on the type of Action that is requested.

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

    public Type getType() {
        return type;
    }

    public RemoteHost getVmHost() {
        return vmHost;
    }

    public VM getNewVM() {
        return newVM;
    }

    public RemoteHost getServiceInstanceHost() {
        return serviceInstanceHost;
    }

    public VM getServiceInstanceVM() {
        return serviceInstanceVM;
    }

    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    public Request getRequest() {
        return request;
    }

    public List<Node> getServiceChain() {
        return serviceChain;
    }






}
