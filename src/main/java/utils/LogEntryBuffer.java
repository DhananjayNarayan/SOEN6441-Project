package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class LogEntryBuffer {

    private LogEntryWriter d_lew = new LogEntryWriter(); //d_lew is an observer object

    public void logInfo(String p_s) {
        notifyObservers(p_s);
    }

    public void notifyObservers(String p_s) {
        d_lew.update(p_s);
    }

    public void clearNewFile() {
        PrintWriter l_writeData = null;
        String l_filename ="demolog";
        try {
            l_writeData = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + l_filename + ".txt", false)));
        } catch (Exception ex) {

        }
    }
}
