package bg.sofia.uni.fmi.mjt.selfcare.command;

public class CommandParser {
    public static String[] parseCredentials(String arguments) {
        String[] separatedArguments = arguments.split(" ");
        if (separatedArguments.length != 2) {
//            throw new Exception();
        }

        if (!CommandValidator.isUsernameValid(separatedArguments[0])) {
            //throw new Exception
        }

        if (!CommandValidator.isPasswordValid(separatedArguments[1])) {
            //throw new Exception
        }

        return separatedArguments;
    }
}
