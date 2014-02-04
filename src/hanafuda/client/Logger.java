package hanafuda.client;

import java.io.File;
import java.io.IOException;

import java.util.Date;

public class Logger {
	
	static final String logName = "client.log";
	static String logPath;
	static File logFile;
	static boolean functional;
	
	/**
	 * Initializes the logger, creating a log file and opening the fileWriter.
	 * @param logPath : The path to the /log/ folder.
	 */
	
	static void initialize(String logPath) {
		Logger.logPath = logPath;
		logFile = new File(logPath + logName);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
				functional = true;
			} catch (IOException e) {
				ClientGUI.updateStatus("Failed to create log file.\n" +
						"Ensure you have write priveleges\n" +
						"for the log folder.");
				functional = false;
				return;
			}
		}
	}
	
	/**
	 * Logs the passed message to the logfile, prepending a timestamp.
	 * @param message : The message to be logged.
	 */
	
	static void log(String message) {
		@SuppressWarnings("unused")
		String prefix = "[" + new Date().toString().substring(12,19) + "]:";
		if (functional) {
			//TODO Add file-writing functionality
			// i.e. logFile.write(prefix + message);
		}
	}
	
	
}
