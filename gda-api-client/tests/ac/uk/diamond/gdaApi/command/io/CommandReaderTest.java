package ac.uk.diamond.gdaApi.command.io;

import ac.uk.diamond.gdaApi.serialization.Deserializer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.DataInput;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CommandReaderTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private CommandReader<Integer> reader;
    private DataInput mockInput;
    private Deserializer<String, Integer> mockDeserializer;

    @Before
    public void setUp() {
        mockDeserializer =
                (Deserializer<String, Integer>) mock(Deserializer.class);
        mockInput = mock(DataInput.class);
        reader = makeCommandReader(mockInput, mockDeserializer);
    }

    @Test
    public void testInputCannotBeNull() {
        expectIllegalArgumentException();
        makeCommandReader(null, mockDeserializer);
    }

    @Test
    public void testDeserializerCannotBeNull() {
        expectIllegalArgumentException();
        makeCommandReader(mockInput, null);
    }

    @Test
    public void testNextThrowsIoExceptionIfInputThrowsIoException()
            throws IOException {
        expectRuntimeException();
        when(mockInput.readUTF()).thenThrow(new IOException());
        reader.next();
    }

    @Test
    public void testDeserializerInvoked() throws IOException {
        when(mockInput.readUTF()).thenReturn("1");
        reader.next();
        verify(mockDeserializer, times(1)).deserialize("1");
    }

    @Test
    public void testNextReadsString() throws IOException {
        when(mockInput.readUTF()).thenReturn("1");
        when(mockDeserializer.deserialize("1")).thenReturn(1);
        int next = reader.next();
        assertEquals(1, next);
    }

    private CommandReader<Integer> makeCommandReader(
            DataInput input, Deserializer<String, Integer> deserializer) {
        return new CommandReader<>(input, deserializer);
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