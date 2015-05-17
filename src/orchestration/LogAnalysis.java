package orchestration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * A standalone class for generating graphs from packet data.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class LogAnalysis {

	/** Timestamps of packets sent through the network */
	HashMap<String, Date> packetsSent;

	/** Timestamps of packets received from the network */
	HashMap<String, Date> packetsReceived;

	/** Timestamp of the last packet sent through the network */
	Date lastPacketSent = new Date(0);

	/** Timestamp of the last packet received from the network */
	Date lastPacketReceived = new Date(0);

	/**
     * Create a LogAnalysis object by parsing two files: one contains the timestamps
     * of sent packets, the other contains the timestamps of received packets.
     */
	public LogAnalysis(String sentFileName, String receivedFileName) {
		packetsReceived = new HashMap<String, Date>();
		packetsSent = new HashMap<String, Date>();
		File sentFile = new File(sentFileName);
		File receivedFile = new File(receivedFileName);
		
		BufferedReader reader;
		String line = null;
		try {
		    reader = new BufferedReader(new FileReader(sentFile));
		    while ((line = reader.readLine()) != null) {
		    	int i = line.lastIndexOf("\t");
		    	String contentString = line.substring(i + 1);
		    	String dateString = line.substring(0, 23);
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		    	Date date = (Date)sdf.parse(dateString);
		    	if (date.getTime() > lastPacketSent.getTime()) lastPacketSent = date;
		    	packetsSent.put(contentString, date);
		    }
		    reader.close();
		    reader = new BufferedReader(new FileReader(receivedFile));
		    while ((line = reader.readLine()) != null) {
		    	int i = line.lastIndexOf("\t");
		    	String contentString = line.substring(i + 1);
		    	String dateString = line.substring(0, 23);
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		    	Date date = (Date)sdf.parse(dateString);
		    	if (date.getTime() > lastPacketReceived.getTime()) lastPacketReceived = date;
		    	packetsReceived.put(contentString, date);
		    }
		    reader.close();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (ParseException e) {
		    e.printStackTrace();
		}
	}

	/** @return the map if packets received and their timestamps. */
	public HashMap<String, Date> getPacketsReceived() {
		return packetsReceived;
	}

	/** @return the map of packets sent and their timestamps. */
	public HashMap<String, Date> getPacketsSent() {
		return packetsSent;
	}
	
	/** @return whether or not all sent packets were received. */
	public boolean allPacketsMadeIt() {
		if (packetsReceived.size() == packetsSent.size()) return true;
		else return false;
	}
	
	/** @return the maximum latency observed from any data packet. */
	public long calculateMaxLatency() {
		return (lastPacketReceived.getTime() - lastPacketSent.getTime());
	}
	
	/** @return the mean latency observed across all data packets. */
	public double calculateAverageLatency() {
		double numPackets = Math.min(packetsSent.size(), packetsReceived.size());
		double sumLatency = 0;
		for (String packet : packetsSent.keySet()) {
			if (packetsReceived.keySet().contains(packet)) {
				long sent = packetsSent.get(packet).getTime();
				long received = packetsReceived.get(packet).getTime();
				sumLatency += (received - sent);
			}
		}
		return (sumLatency / numPackets);
	}
	
	/** Generates a file containing (x,y) coordinates representing a scatter plot of each packet against its latency. */
	public void generateLatencyScatterGraph(String fileName) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			int i = 1;
			for (String packet : packetsSent.keySet()) {
				if (packetsReceived.keySet().contains(packet)) {
					long sent = packetsSent.get(packet).getTime();
					long received = packetsReceived.get(packet).getTime();
					long latency = received - sent;
					writer.println(i + "," + latency);
					i++;
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/** Generates a file containing (x,y) coordinates representing a frequency plot of each amount of latency
	 * against how many packets observed that amount.
	 */

	public void generateLatencyFrequencyGraph(String fileName) {
		HashMap<Long, Integer> latencies = new HashMap<Long, Integer>();
		for (String packet : packetsSent.keySet()) {
			if (packetsReceived.keySet().contains(packet)) {
				long sent = packetsSent.get(packet).getTime();
				long received = packetsReceived.get(packet).getTime();
				long latency = received - sent;
				if (latencies.containsKey(latency)) {
					latencies.put(latency, 1 + latencies.get(latency));
				} else latencies.put(latency, 1);
			}
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			for (Long l : latencies.keySet()) {
				writer.println(l + "," + latencies.get(l));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/** Generates a file containing (x,y) coordinates representing the percentage of packets which were returned by each measure of latency. */
	public void generateLatencyPercentileGraph(String fileName) {
		
		ArrayList<Long> latencies = new ArrayList<Long>();
		
		for (String packet : packetsSent.keySet()) {
			if (packetsReceived.keySet().contains(packet)) {
				long sent = packetsSent.get(packet).getTime();
				long received = packetsReceived.get(packet).getTime();
				long latency = received - sent;
				latencies.add(latency);
			}
		}
		Collections.sort(latencies);
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			for (int i = 1; i < 101; i++) {
				int index = (int)(latencies.size() * i / 100.0);
				if (index == latencies.size()) index -= 1;
				writer.println(i + "," + latencies.get(index));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}