package ac.uk.diamond.gdaApi.command.gda;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class GdaCommandModelTest {
    public static final String COMMAND_STRING = "bananas";

    @Rule public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testCommandStringCannotBeNull() {
        exception.expect(IllegalArgumentException.class);
        new GdaCommandModel(null);
    }

    @Test
    public void testCommandStringInitializedByConstructor() {
        GdaCommandModel commandModel = new GdaCommandModel(COMMAND_STRING);
        assertEquals(COMMAND_STRING, commandModel.getCommandString());
    }
}