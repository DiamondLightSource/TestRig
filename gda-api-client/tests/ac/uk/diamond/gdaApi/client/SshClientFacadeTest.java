package ac.uk.diamond.gdaApi.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SshClientFacadeTest {
    public static final String USERNAME = "Bob";
    public static final String HOSTNAME = "bobslittleserver.net";
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private SshClientFacade client;

    @Before
    public void setUp() {
        client = new SshClientFacade(USERNAME, HOSTNAME);
    }

    @Test
    public void testConstructorExceptsNullUsername() {
        expectIllegalArgumentException();
        new SshClientFacade(null, HOSTNAME);
    }

    @Test
    public void testConstructorExceptsNullHostname() {
        expectIllegalArgumentException();
        new SshClientFacade(USERNAME, null);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}