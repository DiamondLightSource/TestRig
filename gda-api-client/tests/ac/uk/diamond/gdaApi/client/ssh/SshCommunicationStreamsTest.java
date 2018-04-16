package ac.uk.diamond.gdaApi.client.ssh;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

public class SshCommunicationStreamsTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private InputStream inputStream;
    private OutputStream outputStream;
    private OutputStream errorStream;

    @Before
    public void setUp() {
        inputStream = mock(InputStream.class);
        outputStream = mock(OutputStream.class);
        errorStream = mock(OutputStream.class);
    }

    @Test
    public void testInputStreamCannotBeNull() {
        expectIllegalArgumentException();
        new SshCommunicationStreams(null, outputStream, errorStream);
    }

    @Test
    public void testOutputStreamCannotBeNull() {
        expectIllegalArgumentException();
        new SshCommunicationStreams(inputStream, null, errorStream);
    }

    @Test
    public void testErrorStreamCannotBeNull() {
        expectIllegalArgumentException();
        new SshCommunicationStreams(inputStream, outputStream, null);
    }

    @Test
    public void testGetInputStreamReturnsInputStream() {
        SshCommunicationStreams streams = new SshCommunicationStreams(
                inputStream, outputStream, errorStream);
        assertEquals(inputStream, streams.getInputStream());
    }

    @Test
    public void testGetOutputStreamReturnsOutputStream() {
        SshCommunicationStreams streams = new SshCommunicationStreams(
                inputStream, outputStream, errorStream);
        assertEquals(outputStream, streams.getOutputStream());
    }

    @Test
    public void testGetErrorStreamReturnsErrorStream() {
        SshCommunicationStreams streams = new SshCommunicationStreams(
                inputStream, outputStream, errorStream);
        assertEquals(errorStream, streams.getErrorStream());
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}