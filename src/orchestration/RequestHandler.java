package orchestration;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

//
//
// Request Handler
//
// Stores Individual Tenant information

class RequestHandler implements HttpHandler {

        public RequestHandler() {
            startServer();
        }

        private void startServer() {
            int port = 8080;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/requests", new RequestHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Server running on port " + port);
        }

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