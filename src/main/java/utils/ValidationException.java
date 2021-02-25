package utils;

/**
 * This class manages invalid console commands
 * @author Madhuvanthi Hemanathan
 */
public class ValidationException extends Exception {
    /**
     * initialising a string to hold message
     */
    String d_Message = "Invalid command. Type help to know further";

    /**
     * Validation Exception Constructor
     */
    public ValidationException() {
        super();
    }

    /**
     * Exception message
     * @param message exception message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Get the message and check for exception
     * @return the message
     */
    @Override
    public String getMessage() {
        if(super.getMessage() != null) {
            return super.getMessage();
        }
        return d_Message;
    }
}
