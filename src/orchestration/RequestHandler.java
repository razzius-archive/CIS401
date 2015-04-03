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
        try {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/requests", new RequestServer());
        server.setExecutor(null); // creates a default executor
        server.start();
        logger.info("server running on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class RequestServer implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "hello world";
            logger.info("RECIEVED: " + t.getRequestMethod());
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
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String[] params = out.toString().split("&");
            String paramsStr = "";
            for (int i = 0; i < params.length; i++) {
                paramsStr += params[i] + ", ";
                params[i] = params[i].substring(params[i].indexOf("=")+1);
            }
            logger.info("Request: " + paramsStr);

            Request request = new Request("r4","1","1",72432,44,"s0-s1-s3",12000,1);
            logger.info("Sending request to AlgorithmSolver");
            CustomerResponse cr = stateManager.queryAlgorithmSolver(request);
            logger.info("Algorithm responded with " + cr.accepted);

            String response = "";
            if (cr.accepted) {
                response += "Request Accepted";
            } else {
                response += "Request Denied";
            }

            logger.info("Server responding with: " + response);

            return response;

        }


    }
}
