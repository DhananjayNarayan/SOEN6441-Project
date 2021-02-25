package utils;

/**
 * This class manages invalid console commands
 */
public class ValidationException extends Exception {
    String d_Message = "Invalid command. Type help to know further";

    /**
     * Validation Exception
     */
    public ValidationException() {
        super();
    }

    /**
     * Exception message
     * @param message
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
