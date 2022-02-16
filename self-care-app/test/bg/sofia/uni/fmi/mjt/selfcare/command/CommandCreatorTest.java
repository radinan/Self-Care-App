package bg.sofia.uni.fmi.mjt.selfcare.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

//upgrade to junit4, add messages
@RunWith(MockitoJUnitRunner.class)
public class CommandCreatorTest {

    @Test
    public void testCreateNull() {
        String input = null;
        Command command = CommandCreator.create(input);
        assertNull(command);
    }

    @Test
    public void testCreateBlank() {
        String input = " ";
        Command command = CommandCreator.create(input);
        assertNull(command);
    }

    @Test
    public void testCreateOneArg() {
        String input = "argument";
        Command command = CommandCreator.create(input);
        assertEquals(command.name(), input);
        assertNull(command.arguments());
    }

    @Test
    public void testCreateManyArgs() {
        String name = "name";
        String arguments = "argument argument1 argument2";
        String input = name + " " + arguments;

        Command command = CommandCreator.create(input);
        assertEquals(command.name(), name);
        assertEquals(command.arguments(), arguments);
    }
}