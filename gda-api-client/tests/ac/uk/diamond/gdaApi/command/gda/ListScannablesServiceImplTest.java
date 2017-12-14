package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;
import ac.uk.diamond.gdaApi.command.io.CommandOutput;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ListScannablesServiceImplTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private ListScannablesService<Integer> service;
    private CommandModelRunner<ListScannablesCommand> runner;
    private CommandOutput<Integer> output;

    @Before
    public void setUp() {
        runner = makeMockRunner();
        output = makeMockOutput();
        service = new ListScannablesServiceImpl<>(
                runner, output);
    }

    @Test
    public void testRunnerCannotBeNull() {
        expectIllegalArgumentException();
        new ListScannablesServiceImpl<Integer>(null, output);
    }

    @Test
    public void testOutputCannotBeNull() {
        expectIllegalArgumentException();
        new ListScannablesServiceImpl<Integer>(runner, null);
    }

    @Test
    public void testRunnerInvoked() {
        service.collectList();
        verify(runner, times(1)).run(any());
    }

    @Test
    public void testOutputInvoked() {
        service.collectList();
        verify(output, times(1)).next();
    }

    @Test
    public void testReturnsOutputResult() {
        when(output.next()).thenReturn(1);
        int list = service.collectList();
        assertEquals(1, list);
    }

    private CommandModelRunner<ListScannablesCommand> makeMockRunner() {
        return (CommandModelRunner<ListScannablesCommand>)
                mock(CommandModelRunner.class);
    }

    private CommandOutput<Integer> makeMockOutput() {
        return (CommandOutput<Integer>) mock(CommandOutput.class);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}