package bg.sofia.uni.fmi.mjt.selfcare.command;

import bg.sofia.uni.fmi.mjt.selfcare.utilities.Journal;

import java.util.Arrays;
import java.util.List;

public class CommandParser {
    public static String[] parseCredentials(String arguments) { //return pair (user-pass)
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

    public static Journal parseJournal(String arguments) {
        String[] separatedArguments = arguments.split(" ", 2);

        if (!CommandValidator.isTitleValid(separatedArguments[0])) {
//            throw new Exception
        }

        if (!CommandValidator.isContentValid(separatedArguments[1])) {
//            throw new Exception
        }

        return new Journal(separatedArguments[0], separatedArguments[1]);
    }

    public static List<String> parseKeywords(String arguments) {
        List<String> separatedArguments = Arrays.stream(arguments.split(" ")).toList();
        //validate
        return separatedArguments;
    }
}
