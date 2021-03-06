package bg.sofia.uni.fmi.mjt.selfcare.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandValidatorTest {
    private final static String VALID_CHARS = "Aa0.-_";
    private final static String VALID_PASSWORD = "Aa0*@_";
    private final static String VALID_CONTENT = "Aa0 .?!,_-";
    private final static String VALID_DATE = "2020-10-10";
    private final static String INVALID = null;

    private final static String ASCENDING = "asc";
    private final static String DESCENDING = "desc";

    @Test
    public void testIsUsernameValidTrue() {
        boolean isValid = CommandValidator.isUsernameValid(VALID_CHARS);
        assertTrue(isValid);
    }

    @Test
    public void testIsUsernameValidFalse() {
        boolean isValid = CommandValidator.isUsernameValid(INVALID);
        assertFalse(isValid);
    }

    @Test
    public void isPasswordValidTrue() {
        boolean isValid = CommandValidator.isPasswordValid(VALID_PASSWORD);
        assertTrue(isValid);
    }

    @Test
    public void isPasswordValidFalse() {
        boolean isValid = CommandValidator.isPasswordValid(INVALID);
        assertFalse(isValid);
    }

    @Test
    public void isTitleValidTrue() {
        boolean isValid = CommandValidator.isTitleValid(VALID_CHARS);
        assertTrue(isValid);
    }

    @Test
    public void isTitleValidFalse() {
        boolean isValid = CommandValidator.isTitleValid(INVALID);
        assertFalse(isValid);
    }

    @Test
    public void isContentValidTrue() {
        boolean isValid = CommandValidator.isContentValid(VALID_CONTENT);
        assertTrue(isValid);
    }

    @Test
    public void isContentValidFalse() {
        boolean isValid = CommandValidator.isContentValid(INVALID);
        assertFalse(isValid);
    }

    @Test
    public void areKeywordsValidTrue() {
        List<String> list = new ArrayList<>();
        list.add(VALID_CONTENT);

        boolean isValid = CommandValidator.areKeywordsValid(list);
        assertTrue(isValid);
    }

    @Test
    public void areKeywordsValidFalse() {
        List<String> list = new ArrayList<>();
        list.add(INVALID);

        boolean isValid = CommandValidator.areKeywordsValid(list);
        assertFalse(isValid);
    }

    @Test
    public void isDateValidTrue() {
        boolean isValid = CommandValidator.isDateValid(VALID_DATE);
        assertTrue(isValid);
    }

    @Test
    public void isDateValidFalse() {
        boolean isValid = CommandValidator.isDateValid(INVALID);
        assertFalse(isValid);
    }

    @Test
    public void isAscendingTrue() {
        boolean isValid = CommandValidator.isAscending(ASCENDING);
        assertTrue(isValid);
    }

    @Test
    public void isAscendingFalse() {
        boolean isValid = CommandValidator.isAscending(DESCENDING);
        assertFalse(isValid);
    }

    @Test
    public void isDescendingTrue() {
        boolean isValid = CommandValidator.isDescending(DESCENDING);
        assertTrue(isValid);
    }

    @Test
    public void isDescendingFalse() {
        boolean isValid = CommandValidator.isDescending(ASCENDING);
        assertFalse(isValid);
    }
}