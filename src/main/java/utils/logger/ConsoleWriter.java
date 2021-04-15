package utils.logger;
import utils.Observer;
import java.io.Serializable;

/**
 * A class to enable writing to console using the observer patter.
 * @author Madhuvanthi
 */
public class ConsoleWriter implements Observer, Serializable {

    /**
     * A function to update the string to observers
     * @param p_s the message to be updated
     */
    @Override
    public void update(String p_s) {
        System.out.println(p_s);
    }

    /**
     * A function to clear the logs
     */
    @Override
    public void clearLogs() {
        System.out.print("\033[H\033[2J");
    }
}
