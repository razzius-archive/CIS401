package orchestration;


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
//
// Orchestration Layer
//
// Top-Level Platform Organization

public class OrchestrationLayer {

    private static StateManager stateManager;
	private static RequestHandler requestHandler;
    private static AlgorithmSolver algorithmSolver;
    // private static HardwareCluster hardwareCluster;
    private static Analytics analytics;

    public static void main(String[] args) throws IOException {
        requestHandler = new RequestHandler();
        stateManager = new StateManager();
        algorithmSolver = new AlgorithmSolver();
        // hardwareCluster = new HardwareCluster();
        analytics = new Analytics();
    }

    public static void processRequest(Request request) {
        stateManager.queryAlgorithmSolver(algorithmSolver, request);
    }



}
