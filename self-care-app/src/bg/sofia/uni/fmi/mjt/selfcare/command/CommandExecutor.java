package bg.sofia.uni.fmi.mjt.selfcare.command;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

//make it builder
public class CommandExecutor {
    //think of getting them in an enum
    private final String REGISTER = "register";
    private final String LOGIN = "login";
    private final String DISCONNECT = "disconnect";
    private final String CREATE_JOURNAL = "create-journal";
    private final String LIST_ALL_JOURNALS = "list-all-journals";
    private final String FIND_BY_TITLE = "find-by-title";
    private final String FIND_BY_KEYWORDS = "find-by-keywords";
    private final String FIND_BY_DATE = "find-by-date";
    private final String SORT_BY_DATE = "sort-by-date";
    private final String SORT_BY_TITLE = "sort-by-title";
    private final String GET_QUOTE = "get-quote";
    private final String CHECK_MOOD = "check-mood";

    public CommandExecutor() {

    }

    public String execute(Command command) {
        return switch (command.name()) {
            case DISCONNECT -> disconnect();
            case REGISTER -> register(command.arguments());
            case LOGIN -> login(command.arguments());

            case CREATE_JOURNAL -> createJournal(command.arguments());
            case LIST_ALL_JOURNALS -> listAllJournals(command.arguments());
            case FIND_BY_TITLE -> findByTitle(command.arguments());
            case FIND_BY_KEYWORDS -> findByKeywords(command.arguments());
            case FIND_BY_DATE -> findByDate(command.arguments());
            case SORT_BY_TITLE -> sortByTitle(command.arguments());
            case SORT_BY_DATE -> sortByDate(command.arguments());

            case GET_QUOTE -> getQuote();
            case CHECK_MOOD -> checkMood();

            default -> "Invalid";
        };
    }

    private String disconnect() {
        return "Disconnected.";
    }

    private String register(String arguments) {
        String[] separatedArguments = arguments.split(" ");
        if (separatedArguments.length != 2) {
//            throw new Exception();
        }
        String username = separatedArguments[0];
        String password = separatedArguments[1];
        //validate


        try {
            Files.createFile(Path.of("./credentials.txt"));
        } catch (Exception ignored) {
            //log?
        }

        //read every line
        try (Reader fr = new FileReader(Path.of("./credentials.txt").toString());
             BufferedReader reader = new BufferedReader(fr)) {

            String usernameFile;
            String passwordFile;
            while ((usernameFile = reader.readLine()) != null &&
                    (passwordFile = reader.readLine()) != null) {
                if (usernameFile.equals(username)) {
                    return "Username already exists.";
                }
            }

            //append username and password
            try (Writer fw = new FileWriter(Path.of("./credentials.txt").toString(), true);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                bw.write(username);
                bw.newLine();

                bw.write(password);
                bw.newLine();
                //indicate login
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Successfully registered.";
    }

    private String login(String arguments) {
        String[] separatedArguments = arguments.split(" ");
        if (separatedArguments.length != 2) {
//            throw new Exception();
        }
        String username = separatedArguments[0];
        String password = separatedArguments[1];
        //validate

        try (Reader fr = new FileReader(Path.of("./credentials.txt").toString());
             BufferedReader reader = new BufferedReader(fr)) {

            String usernameFile;
            String passwordFile;
            while ((usernameFile = reader.readLine()) != null &&
                    (passwordFile = reader.readLine()) != null) {
                if (usernameFile.equals(username) && passwordFile.equals(password)) {
                    //indicate login
                    return "Successfully logged in";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Incorrect credentials.";
    }

    private String createJournal(String arguments) {
        return null;
    }

    private String listAllJournals(String arguments) {
        return null;
    }

    private String findByTitle(String arguments) {
        return null;
    }

    private String findByKeywords(String arguments) {
        return null;
    }

    private String findByDate(String arguments) {
        return null;
    }

    private String sortByTitle(String arguments) {
        return null;
    }

    private String sortByDate(String arguments) {
        return null;
    }

    private String getQuote() {
        return null;
    }

    private String checkMood() {
        return null;
    }

}
