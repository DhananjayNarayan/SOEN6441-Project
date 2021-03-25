package utils.logger;

import utils.Observable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * This class gets all the actions of the game. It is an Observable.
 *
 * @author Dhananjay Narayan
 * @author Surya Manian
 */
public class LogEntryBuffer implements Observable {
    /**
     * An object of LogEntryWriter
     */
    private LogEntryWriter d_Lew = new LogEntryWriter(); //d_lew is an observer object

    /**
     * This method gets the information from the game and notifies the Observer.
     *
     * @param p_s The message to be notified
     */
    public void logInfo(String p_s) {
        notifyObservers(p_s);
    }

    /**
     * This method updates the Observer with the message.
     *
     * @param p_s The message to be updated
     */
    public void notifyObservers(String p_s) {
        d_Lew.update(p_s);
    }

    /**
     * This method is used to clear the log file before a new game starts.
     */
    public void clearNewFile() {
        PrintWriter l_WriteData = null;
        String l_Filename = "demo";
        try {
            l_WriteData = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + l_Filename + ".log", false)));
        } catch (Exception ex) {

        }
    }
}
