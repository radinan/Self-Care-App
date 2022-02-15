package bg.sofia.uni.fmi.mjt.selfcare.command;

import bg.sofia.uni.fmi.mjt.selfcare.exceptions.*;
import bg.sofia.uni.fmi.mjt.selfcare.storage.FileEditor;
import bg.sofia.uni.fmi.mjt.selfcare.utilities.Journal;
import bg.sofia.uni.fmi.mjt.selfcare.utilities.Quote;
import bg.sofia.uni.fmi.mjt.selfcare.utilities.User;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandExecutor {
    private static final String REGISTER = "register";
    private static final String LOGIN = "login";
    private static final String LOGOUT = "logout";
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
    private final FileEditor fileEditor;

    public CommandExecutor() {
        currentUser = null;
        fileEditor = new FileEditor();
//        this.apiKey = apiKey;
    }

    public String execute(Command command, User user) throws InvalidArgumentException, FileEditorException,
            RestServerException, UnauthorizedException, UnknownCommandException {

        currentUser = user;
        String commandName = command.name();

        if (isUserLogged()) {
            switch (commandName) {
                case REGISTER:
                case LOGIN: throw new UnauthorizedException("Log out to perform this action.");
            }
        } else {
            switch (commandName) {
                case LOGOUT:
                case CREATE_JOURNAL:
                case LIST_ALL_JOURNALS:
                case FIND_BY_TITLE:
                case FIND_BY_KEYWORDS:
                case FIND_BY_DATE:
                case SORT_BY_TITLE:
                case SORT_BY_DATE:
                case GET_QUOTE: throw new UnauthorizedException("Log in to perform this action.");
            }
        }

        return switch (commandName) {
            case DISCONNECT -> disconnect();

            case REGISTER -> register(command.arguments());
            case LOGIN -> login(command.arguments());
            case LOGOUT -> logout();

            case CREATE_JOURNAL -> createJournal(command.arguments());
            case LIST_ALL_JOURNALS -> listAllJournalsTitle();
            case FIND_BY_TITLE -> findByTitle(command.arguments());
            case FIND_BY_KEYWORDS -> findByKeywords(command.arguments());
            case FIND_BY_DATE -> findByDate(command.arguments());
            case SORT_BY_TITLE -> sortByTitle(command.arguments());
            case SORT_BY_DATE -> sortByDate(command.arguments());

            case GET_QUOTE -> getQuote();

            default -> throw new UnknownCommandException("Unknown command.");
        };
    }

    private String disconnect() {
        return "Disconnected.";
    }

    private String register(String arguments) throws InvalidArgumentException, FileEditorException {
        Map.Entry<String, String> usernamePasswordPair = CommandParser.parseCredentials(arguments);
        String username = usernamePasswordPair.getKey();
        String password = usernamePasswordPair.getValue();

        if (!CommandValidator.isUsernameValid(username) || !CommandValidator.isPasswordValid(password)) {
            throw new InvalidArgumentException("Invalid arguments.");
        }

        if (fileEditor.isUsernameFree(username)) {
            fileEditor.addNewUser(username, password);
            loadUser(username);
            return String.format("Successfully registered user %s.", username);
        } else {
            throw new UsernameTakenException(String.format("Username %s is already taken.", username));
        }
    }

    private String login(String arguments) throws InvalidArgumentException, FileEditorException {
        Map.Entry<String, String> usernamePasswordPair = CommandParser.parseCredentials(arguments);
        String username = usernamePasswordPair.getKey();
        String password = usernamePasswordPair.getValue();

        if (!CommandValidator.isUsernameValid(username) || !CommandValidator.isPasswordValid(password)) {
            throw new InvalidArgumentException("Invalid arguments.");
        }

        if (fileEditor.areCredentialsCorrect(username, password)) {
            loadUser(username);
            return String.format("Successfully logged in with username %s.", username);
        } else {
            throw new WrongCredentialsException("Incorrect credentials.");
        }
    }

    private String logout() {
        currentUser.logout();
        return "Logged out.";
    }

    private String createJournal(String arguments) throws InvalidArgumentException, FileEditorException {
        Map.Entry<String, String> titleContentParsed = CommandParser.parseJournal(arguments);
        String title = titleContentParsed.getKey();
        String content = titleContentParsed.getValue();

        if (!CommandValidator.isTitleValid(title) || !CommandValidator.isContentValid(content)) {
            throw new InvalidArgumentException("Invalid arguments.");
        }

        Journal journal = new Journal(title, content);

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

    private String findByTitle(String argument) throws InvalidArgumentException {
        if (!CommandValidator.isTitleValid(argument)) {
            throw new InvalidArgumentException("Invalid arguments.");
        }

        String delimiter = ",\n";
        return currentUser.getJournals().stream()
                .filter(o -> o.getTitle().equals(argument))
                .map(Journal::toString)
                .collect(Collectors.joining(delimiter));
    }

    private String findByKeywords(String arguments) throws InvalidArgumentException {
        List<String> keywords = CommandParser.parseKeywords(arguments);

        if (CommandValidator.areKeywordsValid(keywords)) {
            throw new InvalidArgumentException("Invalid arguments.");
        }

        List<Map.Entry<Long, Journal>> journalPairs = new ArrayList<>();

        for (Journal journal : currentUser.getJournals()) {
            String separatorRegex = "[\\p{IsPunctuation}\\p{IsWhite_Space}]+";

            List<String> contentWords = Arrays.stream(journal.getContent().split(separatorRegex))
                    .distinct()
                    .toList();

            long matchCount = keywords.stream().filter(contentWords::contains).count();
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

    private String findByDate(String argument) throws InvalidArgumentException {
        if (!CommandValidator.isDateValid(argument)) {
            throw new InvalidArgumentException("Invalid arguments.");
        }

        LocalDate creationDate = LocalDate.parse(argument);

        String delimiter = ",\n";
        return currentUser.getJournals().stream()
                .filter(o -> o.getCreationDate().equals(creationDate))
                .map(Journal::toString)
                .collect(Collectors.joining(delimiter));
    }

    private String sortByTitle(String argument) throws InvalidArgumentException {
        Stream<Journal> stream = currentUser.getJournals().stream();

        if (CommandValidator.isAscending(argument)) {
            stream = stream.sorted(Comparator.comparing(Journal::getTitle));
        } else if (CommandValidator.isDescending(argument)) {
            stream = stream.sorted(Comparator.comparing(Journal::getTitle).reversed());
        } else {
            throw new InvalidArgumentException("Invalid argument.");
        }

        String delimiter = ",\n";
        return stream.map(Journal::toString)
                .collect(Collectors.joining(delimiter));
    }

    private String sortByDate(String argument) throws InvalidArgumentException {
        Stream<Journal> stream = currentUser.getJournals().stream();

        if (CommandValidator.isAscending(argument)) {
            stream = stream.sorted((Comparator.comparing(Journal::getCreationDate)));
        } else if (CommandValidator.isDescending(argument)) {
            stream = stream.sorted((Comparator.comparing(Journal::getCreationDate)).reversed());
        } else {
            throw new InvalidArgumentException("Invalid argument.");
        }

        String delimiter = ",\n";
        return stream.map(Journal::toString)
                .collect(Collectors.joining(delimiter));
    }

    private String getQuote() throws RestServerException {
        URI uri = URI.create("https://quotes15.p.rapidapi.com/quotes/random/");
        String hostName = "x-rapidapi-host";
        String hostValue = "quotes15.p.rapidapi.com";
        String keyName = "x-rapidapi-key";
        String keyValue = null;
        String method = "GET";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header(hostName, hostValue)
                .header(keyName, keyValue)
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            Gson gson = new Gson();
            Quote quote = gson.fromJson(HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString()).body(), Quote.class);
            return quote.getContent();
        } catch (Exception e) {
            throw new RestServerException("Problem with quotes server.");
        }
    }

    private void loadUser(String username) throws FileEditorException {
        currentUser.setUsername(username);
        currentUser.login();
        currentUser.setJournals(fileEditor.getAllJournalsOfUser(username));
    }

    private boolean isUserLogged() {
        return currentUser != null && currentUser.isLogged();
    }
}
