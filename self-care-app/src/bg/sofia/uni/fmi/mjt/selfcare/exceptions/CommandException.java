package bg.sofia.uni.fmi.mjt.selfcare.exceptions;

//add constructor with exception object
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

}
