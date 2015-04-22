package orchestration;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.net.*;

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


public class CustomerServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(CustomerServlet.class.getName());

	public void init() throws ServletException {

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {

			response.setStatus(response.SC_OK);
			String packetContent = request.getParameter("packetContent");
			String ip = request.getParameter("ip");
			int port = Integer.parseInt(request.getParameter("port"));


			DatagramSocket clientSocket = new DatagramSocket();
      		InetAddress IPAddress = InetAddress.getByName(ip);
      		byte[] sendData = new byte[1024];
      		byte[] receiveData = new byte[1024];
	      	sendData = packetContent.getBytes();
	      	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
	      	clientSocket.send(sendPacket);
	      	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	      	clientSocket.receive(receivePacket);
	      	String modifiedSentence = new String(receivePacket.getData());
	      	System.out.println("FROM SERVER:" + modifiedSentence);
	      	clientSocket.close();
	    } catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

	}
}