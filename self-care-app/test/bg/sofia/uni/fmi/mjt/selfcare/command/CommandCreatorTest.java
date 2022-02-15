package bg.sofia.uni.fmi.mjt.selfcare.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandCreatorTest {

    @Test
    void testCreateNull() {
        String input = null;
        Command command = CommandCreator.create(input);
        assertNull(command);
    }

    @Test
    void testCreateBlank() {
        String input = " ";
        Command command = CommandCreator.create(input);
        assertNull(command);
    }

    @Test
    void testCreateOneArg() {
        String input = "argument";
        Command command = CommandCreator.create(input);
        assertEquals(command.name(), input);
        assertNull(command.arguments());
    }

    @Test
    void testCreateManyArgs() {
        String name = "name";
        String arguments = "argument argument1 argument2";
        String input = name + " " + arguments;

        Command command = CommandCreator.create(input);
        assertEquals(command.name(), name);
        assertEquals(command.arguments(), arguments);
    }
}