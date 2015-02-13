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


/**
 * Based on  http://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java-se-api
 * 
 *
 */
public class RequestHandler {
	public static void main(String[] args) throws Exception {
		int port = Integer.parseInt(args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/requests", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server running on port " + port);
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "hello world";
            System.out.println("RECIEVED: " + t.getRequestMethod());
            processRequest(t);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        public void processRequest(HttpExchange t) throws IOException {
            InputStream is = t.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close();

        }
    }
}
