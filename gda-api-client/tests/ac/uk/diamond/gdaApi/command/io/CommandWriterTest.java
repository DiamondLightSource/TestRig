package ac.uk.diamond.gdaApi.command.io;

import ac.uk.diamond.gdaApi.serialization.Serializer;
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

    private CommandWriter<Integer> writer;
    private DataOutput mockOutput;
    private Serializer<Integer, String> mockSerializer;

    @Before
    public void setUp() {
        mockSerializer = (Serializer<Integer, String>) (mock(Serializer.class));
        mockOutput = makeMockWriter();
        writer = makeCommandWriter(mockOutput, mockSerializer);
    }

    @Test
    public void testOutputCannotBeNull() throws IOException {
        expectIllegalArgumentException();
        makeCommandWriter(null, mockSerializer);
    }

    @Test
    public void testSerializerCannotBeNull() {
        expectIllegalArgumentException();
        makeCommandWriter(mockOutput, null);
    }

    @Test
    public void testIoExceptionThrownInSendWhenWriterThrows()
            throws IOException {
        expectIoException();
        makeMockWriterThrowIoException(mockOutput);
        writer.send(1);
    }

    @Test
    public void testWriterSendsChar() throws IOException {
        writer.send(1);
        verifyCharsWritten(mockOutput, "1");
    }

    @Test
    public void testWriterSendsChars() throws IOException {
        writer.send(12);
        verifyCharsWritten(mockOutput, "12");
    }

    private void verifyCharsWritten(DataOutput mockWriter, String chars) throws IOException {
        verify(mockWriter, times(1)).writeChars(chars);
    }

    private CommandWriter<Integer> makeCommandWriter(DataOutput writer, Serializer<Integer, String> serializer) {
        return new CommandWriter<>(writer, serializer);
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
}