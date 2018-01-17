package ac.uk.diamond.gdaApi.client.ssh;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

public class SshServerDetailsTest {
    public static final String USERNAME = "Bob";
    public static final String HOSTNAME = "bobslittleserver.net";
    public static final int PORT_NUMBER = 8192;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructorExceptsNullUsername() {
        expectIllegalArgumentException();
        makeServer(null, HOSTNAME, PORT_NUMBER);
    }

    @Test
    public void testConstructorExceptsNullHostname() {
        expectIllegalArgumentException();
        makeServer(USERNAME, null, PORT_NUMBER);
    }

    @Test
    public void testConstructorExceptsNegativePortNumber() {
        expectIllegalArgumentException();
        makeServer(USERNAME, HOSTNAME, -1);
    }

    @Test
    public void testConstructorInitializesUsername() {
        SshServerDetails server = makeDefaultServer();
        assertEquals(USERNAME, server.getUsername());
    }

    @Test
    public void testConstructorInitializesHostname() {
        SshServerDetails server = makeDefaultServer();
        assertEquals(HOSTNAME, server.getHostname());
    }

    @Test
    public void testConstructorInitializesPortNumber() {
        SshServerDetails server = makeDefaultServer();
        assertEquals(PORT_NUMBER, server.getPortNumber());
    }

    private SshServerDetails makeDefaultServer() {
        return makeServer(USERNAME, HOSTNAME, PORT_NUMBER);
    }

    private SshServerDetails makeServer(String username, String hostname, int portNumber) {
        return new SshServerDetails(username, hostname, portNumber);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}