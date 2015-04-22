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

public class LogAnalysis {

	HashMap<String, Date> packetsSent;
	HashMap<String, Date> packetsReceived;
	Date lastPacketSent = new Date(0);
	Date lastPacketReceived = new Date(0);

	
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

	public HashMap<String, Date> getPacketsReceived() {
		return packetsReceived;
	}

	public HashMap<String, Date> getPacketsSent() {
		return packetsSent;
	}
	
	public boolean allPacketsMadeIt() {
		if (packetsReceived.size() == packetsSent.size()) return true;
		else return false;
	}
	
	public long calculateMaxLatency() {
		return (lastPacketReceived.getTime() - lastPacketSent.getTime());
	}
	
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
	
	// public static void main(String args[]) {
	// 	LogAnalysis la = new LogAnalysis("sent9.txt", "received9.txt");
	// 	for (String packet : la.getPacketsSent().keySet()) {
	// 		System.out.println("Packet sent: " + packet + " at time: " + la.getPacketsSent().get(packet).getTime());
	// 	}
	// 	for (String packet : la.getPacketsReceived().keySet()) {
	// 		System.out.println("Packet received: " + packet + " at time: " + la.getPacketsReceived().get(packet).getTime());
	// 	}
	// 	System.out.println("Maximum Packet Latency: " + la.calculateMaxLatency());
	// 	System.out.println("Average Packet Latency: " + la.calculateAverageLatency());
	// 	la.generateLatencyScatterGraph("LogAnalysisScatter.csv");
	// 	la.generateLatencyFrequencyGraph("LogAnalysisFrequency.csv");
	// 	la.generateLatencyPercentileGraph("LogAnalysisPercentile.csv");
	// }
	
}