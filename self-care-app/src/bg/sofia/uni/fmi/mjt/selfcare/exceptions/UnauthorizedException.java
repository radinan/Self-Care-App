package bg.sofia.uni.fmi.mjt.selfcare.exceptions;

public class UnauthorizedException extends CommandException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
