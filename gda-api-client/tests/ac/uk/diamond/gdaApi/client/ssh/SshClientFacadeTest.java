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
    private SshConnectionDetails serverDetails;
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
    public void testConstructorExceptsNullConnectionDetails() {
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
    public void testConnectForwardsExceptionIfThrownByApacheConnect()
            throws IOException {
        expectRuntimeException();
        SshClientFacade facade = makeDefaultFacade();
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenThrow(new IOException());
        facade.connect();
    }

    @Test
    public void testConnectForwardsExceptionIfThrownByFutureAwait()
            throws IOException {
        expectRuntimeException();
        ConnectFuture mockFuture = mock(ConnectFuture.class);
        when(mockFuture.await()).thenThrow(new IOException());
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenReturn(mockFuture);
        SshClientFacade facade = makeDefaultFacade();
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
    public void testClientIsStoppedAfterConnectionFialsWithIoException()
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

    @Test
    public void testDisconnectStopsClient() {
        SshClientFacade facade = makeDefaultFacade();
        facade.disconnect();
        verify(apacheClient, times(1)).stop();
    }

    private SshClientFacade makeDefaultFacade() {

        return makeFacade(serverDetails, apacheClient);
    }

    private SshConnectionDetails makeMockServerDetails() {
        return mock(SshConnectionDetails.class);
    }

    private SshClient makeMockApacheClient() {
        return mock(SshClient.class);
    }

    private SshClientFacade makeFacade(
            SshConnectionDetails sshConnectionDetails, SshClient apacheClient) {
        return new SshClientFacade(sshConnectionDetails, apacheClient);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }

    private void expectRuntimeException() {
        exception.expect(RuntimeException.class);
    }
}