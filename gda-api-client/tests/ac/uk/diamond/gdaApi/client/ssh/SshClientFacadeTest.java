package ac.uk.diamond.gdaApi.client.ssh;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.future.ConnectFuture;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SshClientFacadeTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private SshServerDetails serverDetails;
    private SshClient apacheClient;
    private ConnectFuture connectFuture;

    @Before
    public void setUp() throws IOException {
        serverDetails = makeMockServerDetails();
        apacheClient = makeMockApacheClient();
        connectFuture = mock(ConnectFuture.class);
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenReturn(connectFuture);
    }

    @Test
    public void testConstructorExceptsNullServerDetails() {
        expectIllegalArgumentException();
        makeFacade(null, makeMockApacheClient());
    }

    @Test
    public void testConstructorExceptsNullApacheClient() {
        expectIllegalArgumentException();
        makeFacade(makeMockServerDetails(), null);
    }

    @Test
    public void testConstructorInitializesServerDetails() {
        SshClientFacade facade = makeDefaultFacade();
        assertEquals(serverDetails, facade.getServerDetails());
    }

    @Test
    public void testConnectForwardsExceptionIfPresent() throws IOException {
        expectRuntimeException();
        SshClientFacade facade = makeDefaultFacade();
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenThrow(new IOException());
        facade.connect();
    }

    @Test
    public void testConnectStartsClient() throws IOException {
        SshClientFacade facade = makeDefaultFacade();
        facade.connect();
        verify(apacheClient, times(1))
                .start();
    }

    @Test
    public void testConnectConnectsClient() throws IOException {
        SshClientFacade facade = makeDefaultFacade();
        facade.connect();
        verify(apacheClient, times(1))
                .connect(anyString(), anyString(), anyInt());
    }

    @Test
    public void testClientIsStoppedAfterUnsuccessfulConnection()
            throws IOException {
        expectRuntimeException();
        SshClientFacade facade = makeDefaultFacade();
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenThrow(new IOException());
        facade.connect();
        verify(apacheClient, times(1)).stop();
    }

    @Test
    public void testClientIsStoppedAfterSuccessfulConnection()  {
        SshClientFacade facade = makeDefaultFacade();
        facade.connect();
        verify(apacheClient, times(1)).stop();
    }


    private SshClientFacade makeDefaultFacade() {

        return makeFacade(serverDetails, apacheClient);
    }

    private SshServerDetails makeMockServerDetails() {
        return mock(SshServerDetails.class);
    }

    private SshClient makeMockApacheClient() {
        return mock(SshClient.class);
    }

    private SshClientFacade makeFacade(
            SshServerDetails sshServerDetails, SshClient apacheClient) {
        return new SshClientFacade(sshServerDetails, apacheClient);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }

    private void expectRuntimeException() {
        exception.expect(RuntimeException.class);
    }
}