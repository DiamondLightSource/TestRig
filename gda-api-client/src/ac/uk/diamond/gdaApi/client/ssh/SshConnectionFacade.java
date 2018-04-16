package ac.uk.diamond.gdaApi.client.ssh;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SshConnectionFacade {

    private final SshClient apacheClient;
    private final SshConnectionDetails serverDetails;
    private SshCommunicationStreams communicationStreams;

    public SshConnectionFacade(SshConnectionDetails connectionDetails,
                               SshClient apacheClient,
                               SshCommunicationStreams communicationStreams) {
        if ( connectionDetails == null )
            throw new IllegalArgumentException(
                    "Connection details cannot be null");
        if ( apacheClient == null )
            throw new IllegalArgumentException("Apache client cannot be null");
        if ( communicationStreams == null )
            throw new IllegalArgumentException(
                    "Communication streams cannot be null");
        this.serverDetails = connectionDetails;
        this.apacheClient = apacheClient;
        this.communicationStreams = communicationStreams;
    }

    public SshConnectionDetails getServerDetails() {
        return serverDetails;
    }

    public void attemptConnection(){
        try {
            apacheClient.start();
            ConnectFuture future = apacheClient.connect(
                    serverDetails.getUsername(),
                    serverDetails.getHostname(),
                    serverDetails.getPortNumber());
            future.await();
            try (ClientSession session = future.getSession()) {
                session.auth().verify();
                try (ClientChannel channel = session.createChannel(
                        ClientChannel.CHANNEL_SHELL)) {
                    channel.setIn(communicationStreams.getInputStream());
                    channel.setOut(communicationStreams.getOutputStream());
                    channel.setErr(communicationStreams.getErrorStream());
                }
            }
        } catch (IOException exception) {
            disconnect();
            throw new RuntimeException(exception);
        }
    }

    public void disconnect() {
        apacheClient.stop();
    }

}
