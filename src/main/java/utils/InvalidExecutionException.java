package utils;

/**
 * This class manages invalid execution of commands in the game stage
 * @author Madhuvanthi Hemanathan
 */
public class InvalidExecutionException extends Exception {
    /**
     * initialising a string to hold message
     */
    String d_Message = "The command will not be available in current Game stage";

    /**
     * Invalid Execution Exception to throw in Reinforcement class
     */
    public InvalidExecutionException() {
        super();
    }

    /**
     *Exception Message
     * @param message exception message
     */
    public InvalidExecutionException(String message) {
        super(message);
    }

    /**
     * Get the message and check for exception
     * @return message
     */
    @Override
    public String getMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        }
        return d_Message;
    }
}
