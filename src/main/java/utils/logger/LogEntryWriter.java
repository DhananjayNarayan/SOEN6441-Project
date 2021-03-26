package utils.logger;

import utils.Observer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * A class implementing Observer which observes LogEntryBuffer and writes to log file
 *
 * @author Dhananjay Narayan
 * @author Surya Manian
 */
public class LogEntryWriter implements Observer {
    /**
     * File name for logger
     */
    private String l_Filename = "demo";

    /**
     * Writer buffer
     */
    private PrintWriter l_WriteData;

    public LogEntryWriter() {
        clearLogs();
    }

    /**
     * A function to receive the update from Subject. It then sends the message to be written to LogFile.
     *
     * @param p_s the message to be updated
     */
    public void update(String p_s) {
        writeLogFile(p_s);
    }

    /**
     * A function to write the actions of the game to a logfile named demolog.
     *
     * @param p_str The message to be written to the log file.
     */
    public void writeLogFile(String p_str) {
        try {
            l_WriteData = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + l_Filename + ".log", true)));

            l_WriteData.println(p_str);

        } catch (Exception p_Exception) {
            System.out.println(p_Exception.getMessage());
        } finally {
            l_WriteData.close();
        }

    }

    /**
     * This method is used to clear the log file before a new game starts.
     */
    @Override
    public void clearLogs() {
        try {
            File l_File = new File("logFiles/" + l_Filename + ".log");
            if (l_File.exists()) {
                l_File.delete();
            }
        } catch (Exception ex) {

        }
    }
}

