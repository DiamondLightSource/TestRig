package ac.uk.diamond.gdaApi.client.ssh;

import java.io.InputStream;
import java.io.OutputStream;

public class SshClientFacade {

    public SshClientFacade(String username, String hostname) {
        if ( username == null )
            throw new IllegalArgumentException("Username cannot be null");
        if ( hostname == null )
            throw new IllegalArgumentException("Hostname cannot be null");
    }


}
