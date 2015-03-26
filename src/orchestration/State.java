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

    private Set<RemoteHost> remoteHosts;
    private Map<Request, List<Node>> serviceChains;

    public State() {
    	remoteHosts = new HashSet<RemoteHost>();
    	serviceChains = new HashMap<Request, List<Node>>();
    }

    public Set<RemoteHost> getRemoteHosts() {
    	return this.remoteHosts;
    }

    public Map<Request, List<Node>> getServiceChains() {
    	return this.serviceChains;
    }

    public void duplicate(State otherState) {
    	// Copy otherState's remote hosts into this state
    	for (RemoteHost otherHost : otherState.getRemoteHosts()) {
    		remoteHosts.add(new RemoteHost(otherHost));
    	}
    }

}
