package orchestration;

import java.lang.IllegalArgumentException;

import java.util.HashMap;

import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

@SuppressWarnings("serial")
public class webInterfaceServlet extends HttpServlet {
	
	/* 
	 * The doPost method is not used.
	 */
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		throw new IllegalArgumentException();
	}
	
	/* 
	 * The doGet method generates an HTML form.
	 */

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {

			HashMap<String, VM> vms = new HashMap<String, VM>();

			for (RemoteHost host : OrchestrationLayer.getStateManager().getState().getRemoteHosts().values()) {
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