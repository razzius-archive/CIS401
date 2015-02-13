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

    private static StateManager statemanager;
	private static RequestHandler requestHandler;
    private static AlgorithmSolver algorithmsolver;
    private static HardwareCluster hardwarecluster;


    public static void main(String[] args) throws Exception {
        RequestHandler requestHandler = new RequestHandler();
        

    }



}
