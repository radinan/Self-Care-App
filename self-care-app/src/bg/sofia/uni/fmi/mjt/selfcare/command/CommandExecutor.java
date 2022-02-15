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
    private static final String REGISTER = "register";
    private static final String LOGIN = "login";
    private static final String DISCONNECT = "disconnect";
    private static final String CREATE_JOURNAL = "create-journal";
    private static final String LIST_ALL_JOURNALS = "list-all-journals";
    private static final String FIND_BY_TITLE = "find-by-title";
    private static final String FIND_BY_KEYWORDS = "find-by-keywords";
    private static final String FIND_BY_DATE = "find-by-date";
    private static final String SORT_BY_DATE = "sort-by-date";
    private static final String SORT_BY_TITLE = "sort-by-title";
    private static final String GET_QUOTE = "get-quote";

    private User currentUser;
    private FileEditor fileEditor;

    public CommandExecutor() {
        currentUser = null;
        fileEditor = new FileEditor();
    }

    public String execute(Command command, User user) {
        currentUser = user;

        return switch (command.name()) {
            case DISCONNECT -> disconnect(); //maybe move outside?
            case REGISTER -> register(command.arguments());
            case LOGIN -> login(command.arguments());

            case CREATE_JOURNAL -> createJournal(command.arguments());
            case LIST_ALL_JOURNALS -> listAllJournalsTitle();
            case FIND_BY_TITLE -> findByTitle(command.arguments());
            case FIND_BY_KEYWORDS -> findByKeywords(command.arguments());
            case FIND_BY_DATE -> findByDate(command.arguments());
            case SORT_BY_TITLE -> sortByTitle(command.arguments());
            case SORT_BY_DATE -> sortByDate(command.arguments());

            case GET_QUOTE -> getQuote();

            default -> "Unknown command";
        };
    }

    private String disconnect() {
        return "Disconnected.";
    }

    private String register(String arguments) {
        String[] separatedArguments = CommandParser.parseCredentials(arguments);
        String username = separatedArguments[0];
        String password = separatedArguments[1];
        //validate

        if (fileEditor.isUsernameFree(username)) {
            fileEditor.addNewUser(username, password);
            loadUser(username);
            return String.format("Successfully registered user %s.", username);
        } else {
            return String.format("Username %s is already taken.", username);
        }
    }

    private String login(String arguments) {
        String[] separatedArguments = CommandParser.parseCredentials(arguments);
        String username = separatedArguments[0];
        String password = separatedArguments[1];
        //validate

        if (fileEditor.areCredentialsCorrect(username, password)) {
            loadUser(username);
            return String.format("Successfully logged with username %s.", username);
        } else {
            return "Incorrect credentials.";
        }
    }

    private String createJournal(String arguments) {
        Journal journal = CommandParser.parseJournal(arguments);
        fileEditor.addNewJournal(currentUser.getUsername(), journal);
        currentUser.addJournal(journal);

        return String.format("Journal %s successfully added.", journal.getTitle());
    }

    private String listAllJournalsTitle() {
        String delimiter = ", ";
        return currentUser.getJournals().stream()
                .map(Journal::getTitle)
                .collect(Collectors.joining(delimiter));
    }

    private String findByTitle(String argument) {
        String delimiter = ",\n";
        //validate
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
            if (matchCount > 0) {
                journalPairs.add(new AbstractMap.SimpleEntry<>(matchCount, journal));
            }
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
        //parse sort arguments
        String delimiter = ",\n";
        if (arguments.equals("asc")) {
            return currentUser.getJournals().stream()
                    .sorted(Comparator.comparing(Journal::getTitle))
                    .map(Journal::toString)
                    .collect(Collectors.joining(delimiter));
        } else {
            return currentUser.getJournals().stream()
                    .sorted(Comparator.comparing(Journal::getTitle).reversed())
                    .map(Journal::toString)
                    .collect(Collectors.joining(delimiter));
        }
    }

    private String sortByDate(String arguments) {
        //parse sort arguments
        String delimiter = ",\n";
        if (arguments.equals("asc")) {
            return currentUser.getJournals().stream()
                    .sorted((Comparator.comparing(Journal::getCreationDate)))
                    .map(Journal::toString)
                    .collect(Collectors.joining(delimiter));
        } else {
            return currentUser.getJournals().stream()
                    .sorted((Comparator.comparing(Journal::getCreationDate)).reversed())
                    .map(Journal::toString)
                    .collect(Collectors.joining(delimiter));
        }
    }

    private String getQuote() {
        return null;
    }

    private void loadUser(String username) {
        currentUser.setUsername(username);
        currentUser.login();
        currentUser.setJournals(fileEditor.getAllJournalsOfUser(username));
    }
}
