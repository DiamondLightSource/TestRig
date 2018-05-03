package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.GdaConnection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ListNamesCommandTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private ListNamesCommand writer;
    private GdaConnection mockOutput;

    @Before
    public void setUp() {
        mockOutput = makeMockGda();
        writer = makeCommandWriter(mockOutput);
    }

    @Test
    public void testOutputCannotBeNull() throws IOException {
        expectIllegalArgumentException();
        makeCommandWriter(null);
    }

    @Test
    public void testIoExceptionThrownInRunWhenWriterThrows()
            throws IOException {
        expectRuntimeException();
        makeMockWriterThrowIoException(mockOutput);
        writer.run("1");
    }

    @Test
    public void testWriterSendsString() throws IOException {
        writer.run("1");
        verifyCharsWritten(mockOutput, "1");
    }

    private void verifyCharsWritten(GdaConnection mockGda, String chars)
            throws IOException {
        verify(mockGda, times(1)).sendMessage(chars);
    }

    private ListNamesCommand makeCommandWriter(
            GdaConnection gda) {
        return new ListNamesCommand(gda);
    }

    private GdaConnection makeMockGda() {
        return mock(GdaConnection.class);
    }

    private void makeMockWriterThrowIoException(GdaConnection mockGda) throws IOException {
        doThrow(new IOException()).when(mockGda).sendMessage(any());
    }

    private void expectIoException() {
        exception.expect(IOException.class);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }

    private void expectRuntimeException() {
        exception.expect(RuntimeException.class);
    }
}