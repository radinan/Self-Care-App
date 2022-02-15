package bg.sofia.uni.fmi.mjt.selfcare.command;

import bg.sofia.uni.fmi.mjt.selfcare.exceptions.InvalidArgumentException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CommandParserTest {

    @Test
    public void testParseCredentialsValid() throws InvalidArgumentException {
        String username = "username";
        String password = "password";
        String arguments = username + " " + password;

        Map.Entry<String, String> usernamePasswordPair = CommandParser.parseCredentials(arguments);
        assertEquals(usernamePasswordPair.getKey(), username);
        assertEquals(usernamePasswordPair.getValue(), password);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testParseCredentialsInvalidNumberOfArgs() throws Exception {
        String arguments = "argument1";
        Map.Entry<String, String> usernamePasswordPair = CommandParser.parseCredentials(arguments);
    }

    @Test
    public void testParseJournalValid() throws InvalidArgumentException {
        String title = "title";
        String content = "some words inside";
        String arguments = title + " " + content;

        Map.Entry<String, String> titleContentPair = CommandParser.parseJournal(arguments);
        assertEquals(titleContentPair.getKey(), title);
        assertEquals(titleContentPair.getValue(), content);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testParseJournalInvalidNumberOfArgs() throws Exception {
        String arguments = "argument1";
        Map.Entry<String, String> usernamePasswordPair = CommandParser.parseJournal(arguments);
    }

    @Test
    public void testParseKeywordsValid() {
        String keyword1 = "keyword1";
        String keyword2 = "keyword2";
        String arguments = keyword1 + " " + keyword2;

        List<String> keywordsParsed = CommandParser.parseKeywords(arguments);
        assertEquals(keywordsParsed.get(0), keyword1);
        assertEquals(keywordsParsed.get(1), keyword2);
    }
}