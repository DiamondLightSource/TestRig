package ac.uk.diamond.gdaApi.client.ssh;

public class SshServerDetails {

    private String username;
    private String hostname;
    private int portNumber;

    public SshServerDetails(String username, String hostname, int portNumber) {
        if ( username == null )
            throw new IllegalArgumentException("Username cannot be null");
        if ( hostname == null )
            throw new IllegalArgumentException("Hostname cannot be null");
        if ( portNumber < 0 )
            throw new IllegalArgumentException("Port number must be positive");

        this.username = username;
        this.hostname = hostname;
        this.portNumber = portNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPortNumber() {
        return portNumber;
    }
}
