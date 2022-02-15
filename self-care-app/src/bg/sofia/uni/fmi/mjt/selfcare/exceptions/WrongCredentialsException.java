package bg.sofia.uni.fmi.mjt.selfcare.exceptions;

public class WrongCredentialsException extends InvalidArgumentException {
    public WrongCredentialsException(String message) {
        super(message);
    }
}
