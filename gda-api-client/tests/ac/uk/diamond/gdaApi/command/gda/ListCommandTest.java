package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ListCommandTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private CommandModelRunner<ListCommandModel> mockRunner;
    private ListCommandModel mockModel;
    private ListCommand command;

    @Before
    public void setUp() {
        mockRunner = (CommandModelRunner<ListCommandModel>)
                mock(CommandModelRunner.class);
        mockModel = mock(ListCommandModel.class);
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

    @Test
    public void testRunnerInvoked() {
        command.run();
        verify(mockRunner).run(mockModel);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}