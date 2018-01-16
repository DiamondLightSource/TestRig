package ac.uk.diamond.gdaApi.client;

public class GdaSshClient {

    public GdaSshClient(String username, String hostname) {
        if ( username == null )
            throw new IllegalArgumentException("Username cannot be null");
        if ( hostname == null )
            throw new IllegalArgumentException("Username cannot be null");
    }
}
