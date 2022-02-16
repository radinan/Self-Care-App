package bg.sofia.uni.fmi.mjt.selfcare.command;

import bg.sofia.uni.fmi.mjt.selfcare.exceptions.*;
import bg.sofia.uni.fmi.mjt.selfcare.storage.FileEditor;
import bg.sofia.uni.fmi.mjt.selfcare.entities.Journal;
import bg.sofia.uni.fmi.mjt.selfcare.entities.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutorTest {
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

    private static List<Journal> journals;

    @Mock
    private FileEditor fileEditorMock;

    @Mock
    private User userMock;

    @InjectMocks
    private CommandExecutor commandExecutor;

    @BeforeClass
    public static void setUpClass() {
        journals = new ArrayList<>();
        Journal journal = new Journal("title", LocalDate.of(2019, 10, 10), "word abc");
        Journal journal1 = new Journal("title1", LocalDate.of(2020, 10, 10), "keyword abc");
        journals.add(journal);
        journals.add(journal1);
    }

    @Before
    public void setUp() {
        commandExecutor = new CommandExecutor(userMock, fileEditorMock);
    }

    //unknown command
    @Test(expected = UnknownCommandException.class)
    public void testExecuteNull() throws CommandException {
        Command command = new Command(null, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnknownCommandException.class)
    public void testExecuteWrongCommand() throws CommandException {
        Command command = new Command("wrong", "");
        commandExecutor.execute(command, userMock);
    }
    //-----

    //unauthorized
    @Test(expected = UnauthorizedException.class)
    public void testExecuteLoggedRegister() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(REGISTER, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteLoggedLogin() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(LOGIN, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedLogout() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(LOGOUT, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedCreateJournal() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(CREATE_JOURNAL, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedListAllJournals() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(LIST_ALL_JOURNALS, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedFindByTitle() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(FIND_BY_TITLE, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedFindByKeywords() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(FIND_BY_KEYWORDS, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedFindByDate() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(FIND_BY_DATE, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedSortByTitle() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(SORT_BY_TITLE, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedSortByDate() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(SORT_BY_DATE, null);
        String result = commandExecutor.execute(command, userMock);
    }

    @Test(expected = UnauthorizedException.class)
    public void testExecuteNotLoggedGetByQuote() throws CommandException {
        when(userMock.isLogged()).thenReturn(false);

        Command command = new Command(GET_QUOTE, null);
        commandExecutor.execute(command, userMock);
    }
    //-----

    @Test
    public void testExecuteDisconnect() throws CommandException {
        Command command = new Command(DISCONNECT, null);
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, "Disconnected.");
    }

    //register
    @Test(expected = InvalidArgumentException.class)
    public void testExecuteRegisterInvalid() throws CommandException {
        Command command = new Command(REGISTER, "   ");
        commandExecutor.execute(command, userMock);
    }

    @Test(expected = UsernameTakenException.class)
    public void testExecuteRegisterTaken() throws CommandException {
        when(fileEditorMock.isUsernameFree(any())).thenReturn(false);
        Command command = new Command(REGISTER, "username pass");
        commandExecutor.execute(command, userMock);
    }

    @Test
    public void testExecuteRegisterValid() throws CommandException {
        when(fileEditorMock.isUsernameFree(any())).thenReturn(true);

        Command command = new Command(REGISTER, "username pass");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("Successfully registered user %s.", "username"));
    }
    //-----

    //login
    @Test(expected = InvalidArgumentException.class)
    public void testExecuteLoginInvalid() throws CommandException {
        Command command = new Command(LOGIN, "   ");
        commandExecutor.execute(command, userMock);
    }

    @Test(expected = WrongCredentialsException.class)
    public void testExecuteLoginWrong() throws CommandException {
        when(fileEditorMock.areCredentialsCorrect(any(), any())).thenReturn(false);
        Command command = new Command(LOGIN, "username pass");
        commandExecutor.execute(command, userMock);
    }

    @Test
    public void testExecuteLoginValid() throws CommandException {
        when(fileEditorMock.areCredentialsCorrect(any(), any())).thenReturn(true);

        Command command = new Command(LOGIN, "username pass");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("Successfully logged in with username %s.", "username"));
    }
    //-----

    //logout
    @Test
    public void testExecuteLogoutValid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(LOGOUT, null);
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, "Logged out.");
    }

    //create journal
    @Test(expected = InvalidArgumentException.class)
    public void testExecuteCreateJournalInvalid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(CREATE_JOURNAL, "^% %$");
        commandExecutor.execute(command, userMock);
    }

    @Test
    public void testExecuteCreateJournalValid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(CREATE_JOURNAL, "title content");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("Journal %s successfully added.", "title"));
    }

    //list all journals
    @Test
    public void testExecuteListAllJournals() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);
        when(userMock.getJournals()).thenReturn(journals);

        Command command = new Command(LIST_ALL_JOURNALS, null);
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("%s, %s", journals.get(0).getTitle(), journals.get(1).getTitle()));
    }

    //find by title
    @Test(expected = InvalidArgumentException.class)
    public void testExecuteFindByTitleInvalid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(FIND_BY_TITLE, "*");
        commandExecutor.execute(command, userMock);
    }

    @Test
    public void testExecuteFindByTitleValid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);
        when(userMock.getJournals()).thenReturn(journals);

        Command command = new Command(FIND_BY_TITLE, "title");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("%s", journals.get(0).toString()));
    }

    //find by keywords
    @Test(expected = InvalidArgumentException.class)
    public void testExecuteFindByKeywordsInvalid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(FIND_BY_KEYWORDS, "}");
        commandExecutor.execute(command, userMock);
    }

    @Test
    public void testExecuteFindByKeywordsValid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);
        when(userMock.getJournals()).thenReturn(journals);

        Command command = new Command(FIND_BY_KEYWORDS, "abc");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("%s,\n%s", journals.get(0).toString(), journals.get(1).toString()));
    }

    //find by date
    @Test(expected = InvalidArgumentException.class)
    public void testExecuteFindByDateInvalid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(FIND_BY_DATE, "0.0");
        commandExecutor.execute(command, userMock);
    }

    @Test
    public void testExecuteFindByDateValid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);
        when(userMock.getJournals()).thenReturn(journals);

        Command command = new Command(FIND_BY_DATE, "2020-10-10");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("%s", journals.get(1).toString()));
    }

    //sort by title
    @Test(expected = InvalidArgumentException.class)
    public void testExecuteSortByTitleInvalid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(SORT_BY_TITLE, "}");
        commandExecutor.execute(command, userMock);
    }

    @Test
    public void testExecuteSortByTitleAsc() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);
        when(userMock.getJournals()).thenReturn(journals);

        Command command = new Command(SORT_BY_TITLE, "asc");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("%s,\n%s", journals.get(0).toString(), journals.get(1).toString()));
    }

    @Test
    public void testExecuteSortByTitleDesc() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);
        when(userMock.getJournals()).thenReturn(journals);

        Command command = new Command(SORT_BY_TITLE, "desc");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("%s,\n%s", journals.get(1).toString(), journals.get(0).toString()));
    }

    //sort by date
    @Test(expected = InvalidArgumentException.class)
    public void testExecuteSortByDateInvalid() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(SORT_BY_DATE, "}");
        commandExecutor.execute(command, userMock);
    }

    @Test
    public void testExecuteSortByDateAsc() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);
        when(userMock.getJournals()).thenReturn(journals);

        Command command = new Command(SORT_BY_DATE, "asc");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("%s,\n%s", journals.get(0).toString(), journals.get(1).toString()));
    }

    @Test
    public void testExecuteSortByDateDesc() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);
        when(userMock.getJournals()).thenReturn(journals);

        Command command = new Command(SORT_BY_DATE, "desc");
        String response = commandExecutor.execute(command, userMock);
        assertEquals(response, String.format("%s,\n%s", journals.get(1).toString(), journals.get(0).toString()));
    }

    //get quote
    @Test
    public void testExecuteGetQuote() throws CommandException {
        when(userMock.isLogged()).thenReturn(true);

        Command command = new Command(GET_QUOTE, null);
        String response = commandExecutor.execute(command, userMock);
        assertNotNull(response);
    }
}