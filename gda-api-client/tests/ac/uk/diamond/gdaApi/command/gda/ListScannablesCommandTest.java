package ac.uk.diamond.gdaApi.command.gda;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListScannablesCommandTest {
    @Test
    public void testCorrectCommandStringUsed() {
        ListScannablesCommand command = new ListScannablesCommand();
        assertEquals("ls_names", command.getCommandString());
    }
}