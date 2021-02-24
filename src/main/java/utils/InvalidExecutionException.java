package utils;

public class InvalidExecutionException extends Exception {
    String d_Message = "The command will not be available in current Game stage";

    public InvalidExecutionException() {
        super();
    }

    public InvalidExecutionException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        }
        return d_Message;
    }
}
