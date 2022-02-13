package bg.sofia.uni.fmi.mjt.selfcare.command;

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

    public void execute(Command command) {
        switch (command.name()) {
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
        }
    }

    private void disconnect() {
    }

    private void register(String arguments) {
    }

    private void login(String arguments) {
    }

    private void createJournal(String arguments) {
    }

    private void listAllJournals(String arguments) {
    }

    private void findByTitle(String arguments) {
    }

    private void findByKeywords(String arguments) {
    }

    private void findByDate(String arguments) {
    }

    private void sortByTitle(String arguments) {
    }

    private void sortByDate(String arguments) {
    }

    private void getQuote() {
    }

    private void checkMood() {
    }

}
