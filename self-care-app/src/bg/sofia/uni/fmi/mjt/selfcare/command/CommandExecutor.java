package bg.sofia.uni.fmi.mjt.selfcare.command;

import bg.sofia.uni.fmi.mjt.selfcare.utilities.Journal;
import bg.sofia.uni.fmi.mjt.selfcare.utilities.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//make class for initial files creation
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

    private User currentUser;

    public CommandExecutor() {

    }

    public String execute(Command command, User user) {
        currentUser = user;
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
        String[] separatedArguments = CommandParser.parseCredentials(arguments);
        String username = separatedArguments[0];
        String password = separatedArguments[1];


        try {
            Files.createFile(Path.of("./credentials.txt"));
        } catch (Exception ignored) {
            //log?
        }

        //check if username exists
        try (Reader fr = new FileReader(Path.of("./credentials.txt").toString());
             BufferedReader br = new BufferedReader(fr)) {

            String usernameFile;
            String passwordFile;

            while ((usernameFile = br.readLine()) != null && (passwordFile = br.readLine()) != null) {
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
            }
        } catch (IOException e) {
            e.printStackTrace(); //rethrow
        }

        loadUser(username);
        return "Successfully registered.";
    }

    private String login(String arguments) {
        String[] separatedArguments = CommandParser.parseCredentials(arguments); //make it a user-pass pair
        String username = separatedArguments[0];
        String password = separatedArguments[1];

        try (Reader fr = new FileReader(Path.of("./credentials.txt").toString());
             BufferedReader reader = new BufferedReader(fr)) {

            String usernameFile;
            String passwordFile;
            while ((usernameFile = reader.readLine()) != null && (passwordFile = reader.readLine()) != null) {
                if (usernameFile.equals(username) && passwordFile.equals(password)) {
                    loadUser(username);
                    return "Successfully logged in";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Incorrect credentials.";
    }

    private String createJournal(String arguments) {
        //<title> <content>
        Journal journal = CommandParser.parseJournal(arguments);
        Path path = Path.of("./users/" + currentUser.getUsername() + ".txt");

        //make it store data in a ??????????? csv format, separated by | ???????????????????????????
        
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

    private void loadUser(String username) {
        currentUser.setUsername(username);
        currentUser.login();

        Path path = Path.of("./users/" + username + ".txt");

        if (Files.exists(path)) {
            List<Journal> fileJournals = new ArrayList<>();

            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(path.toString()))) {
                Journal currentJournal;
                while ((currentJournal = (Journal) is.readObject()) != null) {
                    fileJournals.add(currentJournal);
                }
            } catch (Exception e) {
                e.printStackTrace(); //change "Unavailable service"
            }

            currentUser.setJournals(fileJournals);
        }
    }
}
