package orchestration;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.net.InetSocketAddress;
import com.google.gson.Gson;
import org.apache.log4j.Logger;


import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(RequestServlet.class.getName());

	public static class Container {
    	public List<Map> hosts;
      	public List<Map> services;
   	}

    private static StateManager stateManager;
    private static AlgorithmSolver algorithmSolver;
    private static Analytics analytics;
    private static HardwareCluster hardwareCluster;

	public void init() throws ServletException {
		ServletContext context = getServletContext();
		try {
			InputStream configJson = context.getResourceAsStream("/WEB-INF/config.json");
			// new FileInputStream("config.json")
			BufferedReader br = new BufferedReader(new InputStreamReader(configJson));
	        Gson g = new Gson();
	        Container c = g.fromJson(br, Container.class);
	        System.out.println(c.hosts.get(0).get("memory"));
	        System.out.println(c.services.get(0).get("command"));

	        // set up hosts from config.json
	        ArrayList<HostConfig> hostConfigs = new ArrayList<HostConfig>();
	        for (Map host : c.hosts) {
	            int bandwidth = Integer.parseInt((String) host.get("bandwidth"));
	            int memory = Integer.parseInt((String) host.get("memory"));
	            int numCores = Integer.parseInt((String) host.get("numCores"));
	            String ipAddress = (String) host.get("ipAddress");
	            HostConfig hc = new HostConfig(bandwidth, memory, numCores, ipAddress);
	            hostConfigs.add(hc);
	        }

	        // Create a HostConfig for the local machine with 1Mbps of bandwidth, 4 cores, 2048 MB of RAM
	        // TODO clean this up
	        HostConfig localhostConfig = new HostConfig(1048576, 2048, 4, "127.0.0.1");
	        hostConfigs.add(localhostConfig);

	        analytics = new Analytics();
	        hardwareCluster = new HardwareCluster();
	        logger.info("Starting StateManager");
	        stateManager = new StateManager(hardwareCluster, hostConfigs);

	        // set up services from config.json
	        for (Map service : c.services) {
	            String name = (String) service.get("name");
	            int wcet = Integer.parseInt((String) service.get("wcet"));
	            String command = (String) service.get("command");

	            stateManager.addService(new Service(name, wcet, command));
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();

			Request req = new Request(
				request.getParameter("requestID"),
				request.getParameter("startNode"),
				request.getParameter("endNode"),
				Integer.parseInt(request.getParameter("packageRate")),
				Integer.parseInt(request.getParameter("deadline")),
				request.getParameter("services"),
				Integer.parseInt(request.getParameter("packageSize")),
				Integer.parseInt(request.getParameter("price")));

			out.write("<html><head><title>DAAR 2015</head></title><body>");
			out.write("Received parameters:");
			out.write("\n  requestID = " + request.getParameter("requestID"));
			out.write("\n, startNode = " + request.getParameter("startNode"));
			out.write("\n, endNode = " + request.getParameter("endNode"));
			out.write("\n, packageRate = " + Integer.parseInt(request.getParameter("packageRate")));
			out.write("\n, deadline = " + Integer.parseInt(request.getParameter("deadline")));
			out.write("\n, services = " + request.getParameter("services"));
			out.write("\n, packageSize = " + Integer.parseInt(request.getParameter("packageSize")));
			out.write("\n, price = " + Integer.parseInt(request.getParameter("price")));

			CustomerResponse cr = stateManager.queryAlgorithmSolver(req);
			String res = "";
            if (cr.accepted) {
                res += "Request Accepted";
            } else {
                res += "Request Denied";
            }

            logger.info("Server responding with: " + res);
            out.write("<p>" + res + "</p>");
            out.write("</body></html>");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		// try {
		// 	System.out.println("got request");
		// 	PrintWriter out = response.getWriter();
		// 	out.write("<html><head><title>DAAR 2015</title></head><body>");
		// 	out.write("SEND POST REQUEST TO THIS ADDRESS WITH PARAMETERS");
		// 	out.write("</body></html>");
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }

		try {

			HashMap<String, VM> vms = new HashMap<String, VM>();

			for (RemoteHost host : stateManager.getState().getRemoteHosts().values()) {
				for (String vmID : host.getVMs().keySet()) {
					vms.put(vmID, host.getVMs().get(vmID));
				}
			}

			PrintWriter out = response.getWriter();
			out.write("<html><body>"
					
				+ "<form method=\"GET\">"
				+ "<input type=\"submit\" value=\"Get Virtual Machine Statuses\">"
				+ "</form>"
				+ "<br>");

				for (String vmID : vms.keySet()) {
					out.write("Found VM " + vmID + "<br>");

				}

					
				out.write("</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}