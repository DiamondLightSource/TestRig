package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.gda.listCommand.ListCommand;
import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;

public class ListCommandTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private CommandModelRunner<GdaCommandModel> mockRunner;
    private GdaCommandModel mockModel;
    private ListCommand command;

    @Before
    public void setUp() {
        mockRunner = (CommandModelRunner<GdaCommandModel>)
                mock(CommandModelRunner.class);
        mockModel = mock(GdaCommandModel.class);
        command = new ListCommand(mockRunner, mockModel);
    }

    @Test
    public void testRunnerCannotBeNull() {
        expectIllegalArgumentException();
        new ListCommand(null, mockModel);
    }

    @Test
    public void testModelCannotBeNull() {
        expectIllegalArgumentException();
        new ListCommand(mockRunner, null);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}