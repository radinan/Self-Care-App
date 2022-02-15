package bg.sofia.uni.fmi.mjt.selfcare.command;


import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandParser {
    public static Map.Entry<String, String> parseCredentials(String arguments) {
        String[] separatedArguments = arguments.split(" ");
        if (separatedArguments.length != 2) {
//            throw new Exception();
        }

        return new AbstractMap.SimpleEntry<>(separatedArguments[0], separatedArguments[1]);
    }

    public static Map.Entry<String, String> parseJournal(String arguments) {
        String[] separatedArguments = arguments.split(" ", 2);
        if (separatedArguments.length != 2) {
//            throw new Exception();
        }
        return new AbstractMap.SimpleEntry<>(separatedArguments[0], separatedArguments[1]);
    }

    public static List<String> parseKeywords(String arguments) {
        return Arrays.stream(arguments.split(" ")).toList();
    }

}
