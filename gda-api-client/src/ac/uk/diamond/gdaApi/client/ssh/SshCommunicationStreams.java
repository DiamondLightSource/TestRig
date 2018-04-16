package ac.uk.diamond.gdaApi.client.ssh;

import java.io.InputStream;
import java.io.OutputStream;

public class SshCommunicationStreams {
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final OutputStream errorStream;

    public SshCommunicationStreams(
            InputStream inputStream, OutputStream outputStream,
            OutputStream errorStream) {
        if ( inputStream == null )
            throw new IllegalArgumentException("inputStream cannot be null");
        if ( outputStream == null )
            throw  new IllegalArgumentException("outputStream cannot be null");
        if ( errorStream == null )
            throw  new IllegalArgumentException("errorStream cannot be null");
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.errorStream = errorStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public OutputStream getErrorStream() {
        return errorStream;
    }
}
