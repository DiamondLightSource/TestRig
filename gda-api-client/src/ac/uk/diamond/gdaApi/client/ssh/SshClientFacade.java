package ac.uk.diamond.gdaApi.client.ssh;

import java.io.InputStream;
import java.io.OutputStream;

public class SshClientFacade {

    private SshServerDetails serverDetails;

    public SshClientFacade(SshServerDetails serverDetails) {
        if ( serverDetails == null )
            throw new IllegalArgumentException("Server details cannot be null");
        this.serverDetails = serverDetails;
    }

    public SshServerDetails getServerDetails() {
        return serverDetails;
    }
}
