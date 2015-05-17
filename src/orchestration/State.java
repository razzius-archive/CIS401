package orchestration;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import orchestration.State;
import orchestration.Node;
import orchestration.Request;

/**
 * The current configuration of the hardware cluster.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class State {

    /** The remote hosts running on the hardware cluster. */
    private Map<String, RemoteHost> remoteHosts;

    /** The service chains running on the hardware cluster. */
    private Map<Request, List<Node>> serviceChains;

    /** Create a new State with the specified parameters. */
    public State(Map <String, RemoteHost> remoteHosts) {
    	this.remoteHosts = remoteHosts;
    	this.serviceChains = new HashMap<Request, List<Node>>();
    }

    /** @return the remote hosts running on the hardware cluster. */
    public Map<String, RemoteHost> getRemoteHosts() {
    	return this.remoteHosts;
    }

    /** @return the service chains running on the hardware cluster. */
    public Map<Request, List<Node>> getServiceChains() {
    	return this.serviceChains;
    }

}
