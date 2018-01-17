package ac.uk.diamond.gdaApi.client.ssh;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SshClientFacadeTest {
    public static final SshServerDetails SSH_SERVER_DETAILS
            = mock(SshServerDetails.class);
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructorExceptsNullUsername() {
        expectIllegalArgumentException();
        new SshClientFacade(null);
    }

    @Test
    public void testConstructorInitializesServerDetails() {
        SshClientFacade facade = new SshClientFacade(SSH_SERVER_DETAILS);
        assertEquals(SSH_SERVER_DETAILS, facade.getServerDetails());
    }


    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }
}