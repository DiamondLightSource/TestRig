package ac.uk.diamond.gdaApi.service;

import ac.uk.diamond.gdaApi.service.ListScannablesCommand;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListScannablesCommandTest {
    @Test
    public void testCorrectCommandStringUsed() {
        ListScannablesCommand command = new ListScannablesCommand();
        assertEquals("ls_names", command.getCommandString());
    }
}