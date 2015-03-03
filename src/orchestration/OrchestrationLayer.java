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

        ArrayList<HostConfig> hostConfigs = new ArrayList<HostConfig>();

        // Create a HostConfig for the local machine with 1Mbps of bandwidth, 4 cores, 2048 MB of RAM
        HostConfig localhostConfig = new HostConfig(1048576, 2048, 4, "127.0.0.1");
        hostConfigs.add(localhostConfig);

        requestHandler = new RequestHandler();
        analytics = new Analytics();
        hardwareCluster = new HardwareCluster(hostConfigs);

        stateManager = new StateManager(hardwareCluster);

        stateManager.addService(0, new Service("0", 100, 8));
        stateManager.addService(1, new Service("1", 100, 8));
        stateManager.addService(2, new Service("2", 100, 16));
        stateManager.addService(3, new Service("3", 100, 16));
        stateManager.addService(4, new Service("4", 100, 32));
        stateManager.addService(5, new Service("5", 100, 64));
    }
}
