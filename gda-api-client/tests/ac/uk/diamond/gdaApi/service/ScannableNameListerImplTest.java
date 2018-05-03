package ac.uk.diamond.gdaApi.service;

import ac.uk.diamond.gdaApi.command.Command;
import ac.uk.diamond.gdaApi.command.CommandResultSupplier;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ScannableNameListerImplTest {
    private static final List<String> SINGLE_ITEM_RESPONSE
            = Arrays.asList("Red");
    private static final List<String> MULTI_ITEM_RESPONSE
            = Arrays.asList("Red", "Green", "Blue");

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private Command runner;
    private CommandResultSupplier resultSupplier;
    private ScannableNameLister nameLister;

    @Before
    public void setUp() {
        runner = mock(Command.class);
        resultSupplier = mock(CommandResultSupplier.class);
        nameLister = new ScannableNameListerImpl(runner, resultSupplier);
    }

    @Test
    public void testRunnerCannotBeNull() {
        expectIllegalArgumentException();
        new ScannableNameListerImpl(null, resultSupplier);
    }

    @Test
    public void testResultSupplierCannotBeNull() {
        expectIllegalArgumentException();
        new ScannableNameListerImpl(runner, null);
    }

    @Test
    public void testListScannablesCommandSentToGdaConnection() {
        nameLister.collectList();
        verify(runner, times(1)).run("ls_names");
    }

    @Test
    public void testTakesListScannablesCommandResult() {
        nameLister.collectList();
        verify(resultSupplier, times(1)).nextMessage();
    }

    @Test
    public void testListScannablesCommandResultReturnedIfSingleItem() {
        when(resultSupplier.nextMessage()).thenReturn(SINGLE_ITEM_RESPONSE);
        List<String> result = nameLister.collectList();
        assertEquals(SINGLE_ITEM_RESPONSE, result);
    }

    @Test
    public void testListScannablesCommandResultReturnedIfMultipleItems() {
        when(resultSupplier.nextMessage()).thenReturn(MULTI_ITEM_RESPONSE);
        List<String> result = nameLister.collectList();
        assertEquals(MULTI_ITEM_RESPONSE, result);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}