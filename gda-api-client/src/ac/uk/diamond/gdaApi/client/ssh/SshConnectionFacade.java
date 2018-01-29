package ac.uk.diamond.gdaApi.client.ssh;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;

public class SshConnectionFacade {

    private final SshClient apacheClient;
    private SshConnectionDetails serverDetails;

    public SshConnectionFacade(
            SshConnectionDetails connectionDetails, SshClient apacheClient) {
        if ( connectionDetails == null )
            throw new IllegalArgumentException("Connection details cannot be null");
        if ( apacheClient == null )
            throw new IllegalArgumentException("Apache client cannot be null");
        this.serverDetails = connectionDetails;
        this.apacheClient = apacheClient;
    }

    public SshConnectionDetails getServerDetails() {
        return serverDetails;
    }

    public void connect(){
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
            throw new RuntimeException(exception);
        } finally {
            disconnect();
        }
    }

    public void disconnect() {
        apacheClient.stop();
    }
}
