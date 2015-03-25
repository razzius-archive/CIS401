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
    private Set<RemoteHost> hosts = new HashSet<RemoteHost>();
    private Map<Request, List<Node>> serviceChains = new HashMap<Request, List<Node>>();

    public State() {

    }
}
