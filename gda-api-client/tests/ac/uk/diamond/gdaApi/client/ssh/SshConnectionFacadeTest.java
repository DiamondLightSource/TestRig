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

public class SshConnectionFacadeTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private SshConnectionDetails serverDetails;
    private SshClient apacheClient;
    private SshSessionFacade sessionFacade;
    private ConnectFuture connectFuture;

    @Before
    public void setUp() throws IOException {
        serverDetails = makeMockServerDetails();
        apacheClient = makeMockApacheClient();
        sessionFacade = makeMockSessionFacade();
        connectFuture = mock(ConnectFuture.class);
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenReturn(connectFuture);
    }

    @Test
    public void testConstructorExceptsNullConnectionDetails() {
        expectIllegalArgumentException();
        makeFacade(null, makeMockApacheClient(), makeMockSessionFacade());
    }

    @Test
    public void testConstructorExceptsNullApacheClient() {
        expectIllegalArgumentException();
        makeFacade(makeMockServerDetails(), null, makeMockSessionFacade());
    }

    @Test
    public void testConstructorExceptsNullSessionFacade() {
        expectIllegalArgumentException();
        makeFacade(makeMockServerDetails(), makeMockApacheClient(), null);
    }

    @Test
    public void testConstructorInitializesServerDetails() {
        SshConnectionFacade facade = makeDefaultFacade();
        assertEquals(serverDetails, facade.getServerDetails());
    }

    @Test
    public void testConnectForwardsExceptionIfThrownByApacheConnect()
            throws IOException {
        expectRuntimeException();
        SshConnectionFacade facade = makeDefaultFacade();
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenThrow(new IOException());
        facade.attemptConnection();
    }

    @Test
    public void testConnectForwardsExceptionIfThrownByFutureAwait()
            throws IOException {
        expectRuntimeException();
        ConnectFuture mockFuture = mock(ConnectFuture.class);
        when(mockFuture.await()).thenThrow(new IOException());
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenReturn(mockFuture);
        SshConnectionFacade facade = makeDefaultFacade();
        facade.attemptConnection();
    }

    @Test
    public void testConnectStartsClient() throws IOException {
        SshConnectionFacade facade = makeDefaultFacade();
        facade.attemptConnection();
        verify(apacheClient, times(1))
                .start();
    }

    @Test
    public void testConnectConnectsClient() throws IOException {
        SshConnectionFacade facade = makeDefaultFacade();
        facade.attemptConnection();
        verify(apacheClient, times(1))
                .connect(anyString(), anyString(), anyInt());
    }

    @Test
    public void testClientIsStoppedAfterConnectionFialsWithIoException()
            throws IOException {
        expectRuntimeException();
        SshConnectionFacade facade = makeDefaultFacade();
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenThrow(new IOException());
        facade.attemptConnection();
        verify(apacheClient, times(1)).stop();
    }

    @Test
    public void testDisconnectStopsClient() {
        SshConnectionFacade facade = makeDefaultFacade();
        facade.disconnect();
        verify(apacheClient, times(1)).stop();
    }

    @Test
    public void testCannotGetSessionWithoutClientBeingOpen() {
        expectIllegalStateException();
        when(apacheClient.isOpen()).thenReturn(false);
        SshConnectionFacade facade = makeDefaultFacade();
        facade.getSession();
    }

    @Test
    public void testSessionReturnedWhileClientIsOpen() {
        when(apacheClient.isOpen()).thenReturn(true);
        SshConnectionFacade facade = makeDefaultFacade();
        assertEquals(sessionFacade, facade.getSession());
    }

    private SshConnectionFacade makeDefaultFacade() {
        return makeFacade(serverDetails, apacheClient, sessionFacade);
    }

    private SshSessionFacade makeMockSessionFacade() {
        return new SshSessionFacade();
    }

    private SshConnectionDetails makeMockServerDetails() {
        return mock(SshConnectionDetails.class);
    }

    private SshClient makeMockApacheClient() {
        return mock(SshClient.class);
    }

    private SshConnectionFacade makeFacade(
            SshConnectionDetails sshConnectionDetails, SshClient apacheClient,
            SshSessionFacade sessionFacade) {
        return new SshConnectionFacade(sshConnectionDetails, apacheClient,
                sessionFacade);
    }

    private void expectIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
    }

    private void expectRuntimeException() {
        exception.expect(RuntimeException.class);
    }

    private void expectIllegalStateException() {
        exception.expect(IllegalStateException.class);
    }
}