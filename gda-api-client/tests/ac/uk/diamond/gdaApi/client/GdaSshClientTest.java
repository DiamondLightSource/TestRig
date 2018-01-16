package ac.uk.diamond.gdaApi.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GdaSshClientTest {
    public static final String USERNAME = "Bob";
    public static final String HOSTNAME = "bobslittleserver.net";
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private GdaSshClient client;

    @Before
    public void setUp() {
        client = new GdaSshClient(USERNAME, HOSTNAME);
    }

    @Test
    public void testConstructorExceptsNullUsername() {
        expectIllegalArgumentException();
        new GdaSshClient(null, HOSTNAME);
    }

    @Test
    public void testConstructorExceptsNullHostname() {
        expectIllegalArgumentException();
        new GdaSshClient(USERNAME, null);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}