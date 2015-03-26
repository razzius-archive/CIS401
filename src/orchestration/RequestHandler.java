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
import org.apache.log4j.Logger;

import orchestration.OrchestrationLayer;

//
//
// Request Handler
//
// Stores Individual Tenant information
public class RequestHandler {

    private static Logger logger = Logger.getLogger(RequestHandler.class.getName());

    private StateManager stateManager;

    public RequestHandler(StateManager stateManager) throws IOException {
        this.stateManager = stateManager;
        startServer();
    }

    private void startServer() throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/requests", new RequestServer());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server running on port " + port);
    }

    private class RequestServer implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "hello world";
            System.out.println("\nRECIEVED: " + t.getRequestMethod()); 
            InputStream is = t.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            response = processRequest(t, reader);
            OutputStream os = t.getResponseBody();
            t.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
            

            reader.close();
            os.close();
        }

        public String processRequest(HttpExchange t, BufferedReader reader) throws IOException {
            System.out.println("In process request");
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String[] params = out.toString().split("&");
            System.out.println("Recieved Parameters");
            for (int i = 0; i < params.length; i++) {
                //System.out.println(params[i]);
                params[i] = params[i].substring(params[i].indexOf("=")+1);
            }
            // blank line after requests
            System.out.println();

            Request request = new Request("r4","s3_0","s3_1",72432,44,"s0-s1-s3",12000,1);
            System.out.println("Querying AlgorithmSolver...");
            CustomerResponse cr = stateManager.queryAlgorithmSolver(request);
            System.out.println("AlgorithmSolver returned");

            String response = "";
            if (cr.accepted) {
                response += "Request Accepted";
            } else {
                response += "Request Denied";
            }

            return response;

        }


    }
}
