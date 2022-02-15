package bg.sofia.uni.fmi.mjt.selfcare.command;


import bg.sofia.uni.fmi.mjt.selfcare.exceptions.InvalidArgumentException;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandParser {
    public static Map.Entry<String, String> parseCredentials(String arguments) throws InvalidArgumentException {
        String[] separatedArguments = arguments.split(" ");
        if (separatedArguments.length != 2) {
            throw new InvalidArgumentException("Enter credentials in format: <username> <password>");
        }

        return new AbstractMap.SimpleEntry<>(separatedArguments[0], separatedArguments[1]);
    }

    public static Map.Entry<String, String> parseJournal(String arguments) throws InvalidArgumentException {
        String[] separatedArguments = arguments.split(" ", 2);
        if (separatedArguments.length < 2) {
            throw new InvalidArgumentException("Enter credentials in format: <title> <content>");
        }
        return new AbstractMap.SimpleEntry<>(separatedArguments[0], separatedArguments[1]);
    }

    public static List<String> parseKeywords(String arguments) {
        return Arrays.stream(arguments.split(" ")).toList();
    }

}
