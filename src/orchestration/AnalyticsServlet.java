package orchestration;

import java.io.PrintWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class AnalyticsServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(AnalyticsServlet.class.getName());


	public void init() throws ServletException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("got request");
			PrintWriter out = response.getWriter();
			String action = request.getParameter("action");


			logger.info(action);

			System.out.println("here");
			out.write("<html><head><title>DAAR 2015</title></head><body>");
			out.write("hello world");
			out.write("</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("got request");
			PrintWriter out = response.getWriter();
			out.write("<html><head><title>DAAR 2015</title></head><body>");
			out.write("hello world");
			out.write("</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

