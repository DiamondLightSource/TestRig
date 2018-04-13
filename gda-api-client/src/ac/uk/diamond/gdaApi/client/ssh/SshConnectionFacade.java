package ac.uk.diamond.gdaApi.client.ssh;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;

public class SshConnectionFacade {

    private final SshClient apacheClient;
    private final SshConnectionDetails serverDetails;
    private final SshSessionFacade sessionFacade;

    public SshConnectionFacade(
            SshConnectionDetails connectionDetails, SshClient apacheClient,
            SshSessionFacade sessionFacade) {
        if ( connectionDetails == null )
            throw new IllegalArgumentException(
                    "Connection details cannot be null");
        if ( apacheClient == null )
            throw new IllegalArgumentException("Apache client cannot be null");
        if ( sessionFacade == null )
            throw new IllegalArgumentException("Session facade cannot be null");
        this.serverDetails = connectionDetails;
        this.apacheClient = apacheClient;
        this.sessionFacade = sessionFacade;
    }

    public SshConnectionDetails getServerDetails() {
        return serverDetails;
    }

    public SshSessionFacade getSession() {
        if ( apacheClient.isOpen() )
            return sessionFacade;
        else
            throw new IllegalStateException(
                    "Cannot provide session while client is not connected");
    }

    public void attemptConnection(){
        ConnectFuture future;

        try {
            apacheClient.start();
            future = apacheClient.connect(
                    serverDetails.getUsername(),
                    serverDetails.getHostname(),
                    serverDetails.getPortNumber());
            future.await();
            //try (ClientSession session = future.getSession()) {
            //    session.auth().verify();
            //}
        } catch (IOException exception) {
            disconnect();
            throw new RuntimeException(exception);
        }
    }

    public void disconnect() {
        apacheClient.stop();
    }
}
