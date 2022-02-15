package bg.sofia.uni.fmi.mjt.selfcare.command;

import bg.sofia.uni.fmi.mjt.selfcare.utilities.Journal;

import java.util.Arrays;
import java.util.List;

//validation in here or outside???
public class CommandParser {
    public static String[] parseCredentials(String arguments) {
        String[] separatedArguments = arguments.split(" ");
        if (separatedArguments.length != 2) {
//            throw new Exception();
        }

        return separatedArguments;
    }

    public static Journal parseJournal(String arguments) {
        String[] separatedArguments = arguments.split(" ", 2);
        return new Journal(separatedArguments[0], separatedArguments[1]);
    }

    public static List<String> parseKeywords(String arguments) {
        return Arrays.stream(arguments.split(" ")).toList();
    }

}
