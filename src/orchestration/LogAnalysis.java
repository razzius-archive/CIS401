package orchestration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

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

}