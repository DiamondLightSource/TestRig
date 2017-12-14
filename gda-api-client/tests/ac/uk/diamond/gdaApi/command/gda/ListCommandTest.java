package ac.uk.diamond.gdaApi.command.gda;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ListCommandTest {
    public static final String COMMAND_STRING = "ls";

    @Rule public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testCommandStringIsLs() {
        ListCommand commandModel = new ListCommand();
        assertEquals(COMMAND_STRING, commandModel.getCommandString());
    }
}