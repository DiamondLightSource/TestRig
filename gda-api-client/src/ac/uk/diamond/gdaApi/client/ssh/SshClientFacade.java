package ac.uk.diamond.gdaApi.client.ssh;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;

import java.io.IOException;

public class SshClientFacade {

    private final SshClient apacheClient;
    private SshConnectionDetails serverDetails;

    public SshClientFacade(
            SshConnectionDetails serverDetails, SshClient apacheClient) {
        if ( serverDetails == null )
            throw new IllegalArgumentException("Server details cannot be null");
        if ( apacheClient == null )
            throw new IllegalArgumentException("Apache client cannot be null");
        this.serverDetails = serverDetails;
        this.apacheClient = apacheClient;
    }

    public SshConnectionDetails getServerDetails() {
        return serverDetails;
    }

    public void connect(){
        ConnectFuture future;
        ClientSession session;

        try {
            apacheClient.start();
            future = apacheClient.connect(
                    serverDetails.getUsername(),
                    serverDetails.getHostname(),
                    serverDetails.getPortNumber());
            future.await();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } finally {
            disconnect();
        }
    }

    public void disconnect() {
        apacheClient.stop();
    }
}
