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
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class LogAnalysis {

	private static Logger logger = Logger.getLogger(LogAnalysis.class.getName());
	private File file;
	private ArrayList<Date> packetsIn;
	private ArrayList<Date> packetsOut;

	public LogAnalysis(String fileName) {
		packetsIn = new ArrayList<Date>();
		packetsOut = new ArrayList<Date>();
		file = new File(fileName);
		analyze();
	}

	private void analyze() {
		BufferedReader reader;
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	int i = line.lastIndexOf(" ");
		    	String dateString = line.substring(0, i);
		    	String infoString = line.substring(i+1);
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		    	Date date = (Date)sdf.parse(dateString);
		    	if (infoString.contains("received")) packetsIn.add(date);
		    	else if (infoString.contains("sent")) packetsOut.add(date);
		    }
		    reader.close();
		} catch (FileNotFoundException e) {
			logger.debug("File Not Found");
		    e.printStackTrace();
		} catch (IOException e) {
			logger.debug("Analytics encountered an IO Exception!");
		    e.printStackTrace();
		} catch (ParseException e) {
			logger.debug("Error when parsing date.");
		    e.printStackTrace();
		}
	}


	public ArrayList<Date> getPacketsIn() {
		return packetsIn;
	}

	public ArrayList<Date> getPacketsOut() {
		return packetsOut;
	}

		public boolean allPacketsMadeIt() {
		if (packetsIn.size() == packetsOut.size()) return true;
		else return false;
	}
	
	public long calculateMaxLatency() {
		if (!allPacketsMadeIt()) return -1;
		long lastPacketSent = packetsOut.get(packetsOut.size() - 1).getTime();
		long lastPacketReceived = packetsIn.get(packetsIn.size() - 1).getTime();
		return (lastPacketReceived - lastPacketSent);
	}
	
	public double calculateAverageLatency() {
		if (!allPacketsMadeIt()) return -1;
		double sumLatency = 0;
		for (int i = 0; i < packetsIn.size(); i++) {
			long packetSent = packetsOut.get(i).getTime();
			long packetReceived = packetsIn.get(i).getTime();
			sumLatency += (packetReceived - packetSent);
		}
		double numDatapoints = new Double(packetsIn.size());
		return (sumLatency/numDatapoints);
	}
	
	public void generateLatencyScatterGraph(String fileName) {
		if (!allPacketsMadeIt()) return;
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			for (int i = 0; i < packetsIn.size(); i++) {
				long packetSent = packetsOut.get(i).getTime();
				long packetReceived = packetsIn.get(i).getTime();
				long latency = packetReceived - packetSent;
				writer.println(i + "," + latency);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void generateLatencyFrequencyGraph(String fileName) {
		if (!allPacketsMadeIt()) return;
		HashMap<Long, Integer> latencies = new HashMap<Long, Integer>();
		for (int i = 0; i < packetsIn.size(); i++) {
			long packetSent = packetsOut.get(i).getTime();
			long packetReceived = packetsIn.get(i).getTime();
			long latency = packetReceived - packetSent;
			if (latencies.containsKey(latency)) {
				latencies.put(latency, 1 + latencies.get(latency));
			} else latencies.put(latency, 1);
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

}