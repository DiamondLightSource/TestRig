package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;
import ac.uk.diamond.gdaApi.command.io.CommandOutput;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ScannableListerTest {
    public static final List<String> TEST_LIST = Arrays.asList("a", "b", "c");
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private ListScannablesService<List<String>> service;
    private CommandModelRunner<ListScannablesCommand> runner;
    private CommandOutput<List<String>> output;

    @Before
    public void setUp() {
        runner = makeMockRunner();
        output = makeMockOutput();
        service = new ScannableLister(runner, output);
    }

    @Test
    public void testRunnerCannotBeNull() {
        expectIllegalArgumentException();
        new ScannableLister(null, output);
    }

    @Test
    public void testOutputCannotBeNull() {
        expectIllegalArgumentException();
        new ScannableLister(runner, null);
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
        when(output.next()).thenReturn(TEST_LIST);
        List<String> list = service.collectList();
        assertEquals(TEST_LIST, list);
    }

    private CommandModelRunner<ListScannablesCommand> makeMockRunner() {
        return (CommandModelRunner<ListScannablesCommand>)
                mock(CommandModelRunner.class);
    }

    private CommandOutput<List<String>> makeMockOutput() {
        return (CommandOutput<List<String>>) mock(CommandOutput.class);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}