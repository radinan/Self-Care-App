package bg.sofia.uni.fmi.mjt.selfcare.command;

public class CommandCreator {
    public static Command create(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        String[] elements = input.split(" ", 2);
        return elements.length < 2
                ? new Command(elements[0], null)
                : new Command(elements[0], elements[1]);
    }
}
