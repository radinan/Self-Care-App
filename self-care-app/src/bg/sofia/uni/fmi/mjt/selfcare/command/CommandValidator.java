package bg.sofia.uni.fmi.mjt.selfcare.command;

import java.util.List;

public class CommandValidator {
    private final static String ALLOWED_CHARS_REGEX = "^[\\p{Alnum}._-]{3,20}$";
    private final static String ALLOWED_PASSWORD_REGEX = "^[\\p{Alnum}_*@]{5,10}$";
    private final static String ALLOWED_CONTENT_REGEX = "^[\\p{Alnum} .?!,_-]{1,}$";
    private final static String ALLOWED_DATE_REGEX = "^[0-9.]{10}$";
    private final static String ASCENDING = "asc";
    private final static String DESCENDING = "desc";

    public static boolean isUsernameValid(String username) {
        return username.matches(ALLOWED_CHARS_REGEX);
    }

    public static boolean isPasswordValid(String password) {
        return password.matches(ALLOWED_PASSWORD_REGEX);
    }

    public static boolean isTitleValid(String title) {
        return title.matches(ALLOWED_CHARS_REGEX);
    }

    public static boolean isContentValid(String content) {
        return content.matches(ALLOWED_CONTENT_REGEX);
    }

    public static boolean areKeywordsValid(List<String>keywords) {
        return keywords.stream().allMatch(o -> o.matches(ALLOWED_CHARS_REGEX));
    }

    public static boolean isDateValid(String date) {
        return date.matches(ALLOWED_DATE_REGEX);
    }

    public static boolean isAscending(String argument) {
        return argument.equals(ASCENDING);
    }

    public static boolean isDescending(String argument) {
        return argument.equals(DESCENDING);
    }
}
