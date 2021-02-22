package utils;

public class ValidationException extends Exception {
    String d_Message = "Invalid command. Type help to know further";

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        if(super.getMessage() != null) {
            return super.getMessage();
        }
        return d_Message;
    }
}
