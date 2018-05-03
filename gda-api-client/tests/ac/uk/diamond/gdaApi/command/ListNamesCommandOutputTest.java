package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.GdaConnection;
import ac.uk.diamond.gdaApi.client.serialization.Deserializer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.DataInput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ListNamesCommandOutputTest {

    private static final List<String> SINGLE_ITEM_LIST = Arrays.asList("Red");

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private ListNamesCommandOutput reader;
    private GdaConnection mockInput;
    private Deserializer<String, List<String>> mockDeserializer;

    @Before
    public void setUp() {
        mockDeserializer =
                (Deserializer<String, List<String>>) mock(Deserializer.class);
        mockInput = mock(GdaConnection.class);
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
        when(mockInput.nextMessage()).thenThrow(new IOException());
        reader.nextMessage();
    }

    @Test
    public void testDeserializerInvoked() throws IOException {
        when(mockInput.nextMessage()).thenReturn("1");
        reader.nextMessage();
        verify(mockDeserializer, times(1)).deserialize("1");
    }

    @Test
    public void testNextReadsString() throws IOException {
        when(mockInput.nextMessage()).thenReturn("1");
        when(mockDeserializer.deserialize("1")).thenReturn(SINGLE_ITEM_LIST);
        List<String> next = reader.nextMessage();
        assertEquals(SINGLE_ITEM_LIST, next);
    }

    private ListNamesCommandOutput makeCommandReader(
            GdaConnection input, Deserializer<String, List<String>> deserializer) {
        return new ListNamesCommandOutput(input, deserializer);
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