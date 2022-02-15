package bg.sofia.uni.fmi.mjt.selfcare.exceptions;

public class UsernameTakenException extends InvalidArgumentException {
    public UsernameTakenException(String message) {
        super(message);
    }
}
