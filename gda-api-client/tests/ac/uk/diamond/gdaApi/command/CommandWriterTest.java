package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.serialization.Serializer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import java.io.DataOutput;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CommandWriterTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private ListNamesCommandWriter writer;
    private DataOutput mockOutput;

    @Before
    public void setUp() {
        mockOutput = makeMockWriter();
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

    private void verifyCharsWritten(DataOutput mockWriter, String chars)
            throws IOException {
        verify(mockWriter, times(1)).writeChars(chars);
    }

    private ListNamesCommandWriter makeCommandWriter(
            DataOutput writer) {
        return new ListNamesCommandWriter(writer);
    }

    private DataOutput makeMockWriter() {
        return mock(DataOutput.class);
    }

    private void makeMockWriterThrowIoException(DataOutput mockWriter) throws IOException {
        doThrow(new IOException()).when(mockWriter).writeChars(any());
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