package orchestration;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import orchestration.State;
import orchestration.Node;
import orchestration.Request;

public class State {

    private Map<String, RemoteHost> remoteHosts;
    private Map<Request, List<Node>> serviceChains;

    public State(Map <String, RemoteHost> remoteHosts) {
    	this.remoteHosts = remoteHosts;
    	this.serviceChains = new HashMap<Request, List<Node>>();
    }

    public Map<String, RemoteHost> getRemoteHosts() {
    	return this.remoteHosts;
    }

    public Map<Request, List<Node>> getServiceChains() {
    	return this.serviceChains;
    }

}
