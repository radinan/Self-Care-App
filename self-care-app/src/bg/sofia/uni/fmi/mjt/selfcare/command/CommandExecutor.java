package bg.sofia.uni.fmi.mjt.selfcare.command;
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

//            default -> throw new Exception();
        };
    }

    private String disconnect() {
        return "Disconnected.";
    }

    private String register(String arguments) {
        // separate USERNAME, PASSWORD
        // validate

        //check credentials.txt
        // read lines by 2;
            //check 1st == USERNAME
            //yes -> "This username is already taken"
        //append USERNAME\nPASSWORD
            // -> "Successfully registered" (auto login)
        return null;
    }

    private String login(String arguments) {
        return null;
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
