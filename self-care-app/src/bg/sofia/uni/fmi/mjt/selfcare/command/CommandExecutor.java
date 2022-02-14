package bg.sofia.uni.fmi.mjt.selfcare.command;

import bg.sofia.uni.fmi.mjt.selfcare.utilities.FileEditor;
import bg.sofia.uni.fmi.mjt.selfcare.utilities.Journal;
import bg.sofia.uni.fmi.mjt.selfcare.utilities.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    private FileEditor fileEditor;

    public CommandExecutor() {
        fileEditor = new FileEditor();
    }

    public String execute(Command command, User user) { //enum responses?
        currentUser = user;
        return switch (command.name()) {
            case DISCONNECT -> disconnect();
            case REGISTER -> register(command.arguments());
            case LOGIN -> login(command.arguments());

            case CREATE_JOURNAL -> createJournal(command.arguments());
            case LIST_ALL_JOURNALS -> listAllJournals();
            case FIND_BY_TITLE -> findByTitle(command.arguments());
            case FIND_BY_KEYWORDS -> findByKeywords(command.arguments());
            case FIND_BY_DATE -> findByDate(command.arguments());
            case SORT_BY_TITLE -> sortByTitle(command.arguments());
            case SORT_BY_DATE -> sortByDate(command.arguments());

            case GET_QUOTE -> getQuote();
//            case CHECK_MOOD -> checkMood();

            default -> "Invalid";
        };
    }

    private String disconnect() {
        return "Disconnected.";
    }

    private String register(String arguments) {
        String[] separatedArguments = CommandParser.parseCredentials(arguments); //make it return a pair
        String username = separatedArguments[0];
        String password = separatedArguments[1];

        if (fileEditor.isUsernameFree(username)) {
            fileEditor.addNewUser(username, password);
            loadUser(username);
            return "Successfully registered.";
        } else {
            //exception?
            return "Username is taken";
        }
    }

    private String login(String arguments) {
        String[] separatedArguments = CommandParser.parseCredentials(arguments);
        String username = separatedArguments[0];
        String password = separatedArguments[1];

        if (fileEditor.areCredentialsCorrect(username, password)) {
            loadUser(username);
            return "Successfully logged in";
        } else {
            //exception?
            return "Incorrect credentials.";
        }
    }

    private String createJournal(String arguments) {
        Journal journal = CommandParser.parseJournal(arguments);
        fileEditor.addNewJournal(currentUser.getUsername(), journal);
        currentUser.addJournal(journal);

        return "Success";
    }

    private String listAllJournals() {
        String delimiter = ", ";
        return currentUser.getJournals().stream()
                .map(Journal::getTitle)
                .collect(Collectors.joining(delimiter));
    }

    private String findByTitle(String argument) {
        String delimiter = ",\n";
        //parse and validate
        return currentUser.getJournals().stream()
                .filter(o -> o.getTitle().equals(argument))
                .map(Journal::toString)
                .collect(Collectors.joining(delimiter));
    }

    private String findByKeywords(String arguments) {
        List<String> keywords = CommandParser.parseKeywords(arguments);
        List<Map.Entry<Long, Journal>> journalPairs = new ArrayList<>();

        for (Journal journal : currentUser.getJournals()) {
            String separatorRegex = "[\\p{IsPunctuation}\\p{IsWhite_Space}]+";

            List<String> contentWords = Arrays.stream(journal.getContent().split(separatorRegex))
                    .distinct()
                    .toList();

            Long matchCount = keywords.stream().filter(contentWords::contains).count();
            journalPairs.add(new AbstractMap.SimpleEntry<>(matchCount, journal));
        }

        String delimiter = ",\n";
        return journalPairs.stream()
                .sorted(Map.Entry.<Long, Journal>comparingByKey().reversed())
                .map(Map.Entry::getValue)
                .map(Journal::toString)
                .collect(Collectors.joining(delimiter));
    }

    private String findByDate(String argument) {
        LocalDate creationDate = LocalDate.parse(argument);
        //parse and validate
        String delimiter = ",\n";
        return currentUser.getJournals().stream()
                .filter(o -> o.getCreationDate().equals(creationDate))
                .map(Journal::toString)
                .collect(Collectors.joining(delimiter));
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

//    private String checkMood() {
//        return null;
//    }

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
