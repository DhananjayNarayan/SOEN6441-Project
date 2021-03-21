package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;


public class LogEntryWriter implements Observer {

    public void update(String p_s) {
        writeLogFile(p_s);

    }

    public void writeLogFile(String p_str) {

        PrintWriter l_writeData = null;
        String l_filename ="demolog";
        try {
            l_writeData = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + l_filename + ".txt", true)));

            l_writeData.println(p_str);

        } catch (Exception ex) {

        } finally {
            l_writeData.close();
        }

    }
}

