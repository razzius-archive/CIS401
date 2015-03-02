package orchestration;

import java.util.ArrayList;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

//
// Orchestration Layer
//
// Top-Level Platform Class

public class OrchestrationLayer {

    private static StateManager stateManager;
	private static RequestHandler requestHandler;
    private static AlgorithmSolver algorithmSolver;
    private static Analytics analytics;
    private static HardwareCluster hardwareCluster;

    public static void main(String[] args) throws IOException {
        // TODO read network topology file from arg values
        ArrayList<String> topology = new ArrayList<String>();
        topology.add("127.0.0.1");
        requestHandler = new RequestHandler();
        analytics = new Analytics();
        hardwareCluster = new HardwareCluster(topology);
        stateManager = new StateManager(hardwareCluster); // Contains AlgorithmSolver

        stateManager.addService(0, new Service(0, 100, 8));
        stateManager.addService(1, new Service(1, 100, 8));
        stateManager.addService(2, new Service(2, 100, 16));
        stateManager.addService(3, new Service(3, 100, 16));
        stateManager.addService(4, new Service(4, 100, 32));
        stateManager.addService(5, new Service(5, 100, 64));
    }
}
