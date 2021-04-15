package utils;

/**
 * An interface for implementation of Observer with an update function
 *
 * @author Dhananjay Narayan
 */
public interface Observer {

    /**
     * Function to update the message for the observer
     *
     * @param p_S the message to be updated
     */
    void update(String p_S);

    /**
     * clear all logs
     */
    void clearLogs();
}
