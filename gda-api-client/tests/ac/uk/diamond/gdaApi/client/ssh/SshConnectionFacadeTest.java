package ac.uk.diamond.gdaApi.client.ssh;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.future.AuthFuture;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;
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
    private SshCommunicationStreams streams;

    @Before
    public void setUp() throws IOException {
        serverDetails = makeMockServerDetails();
        apacheClient = makeMockApacheClient();
        streams = makeMockCommunicationStreams();

    }

    @Test
    public void testConstructorExceptsNullConnectionDetails() {
        expectIllegalArgumentException();
        makeFacade(null, apacheClient, streams);
    }

    @Test
    public void testConstructorExceptsNullApacheClient() {
        expectIllegalArgumentException();
        makeFacade(serverDetails, null, streams);
    }

    @Test
    public void testConstructorExceptsNullStreams() {
        expectIllegalArgumentException();
        makeFacade(serverDetails, apacheClient, null);
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
        ConnectFuture connectFuture = mock(ConnectFuture.class);
        ClientSession session = mock(ClientSession.class);
        ClientChannel channel = mock(ClientChannel.class);
        AuthFuture authFuture = mock(AuthFuture.class);
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenReturn(connectFuture);
        when(connectFuture.getSession()).thenReturn(session);
        when(session.createChannel(ClientChannel.CHANNEL_SHELL))
                .thenReturn(channel);
        when(session.auth()).thenReturn(authFuture);

        SshConnectionFacade facade = makeDefaultFacade();
        facade.attemptConnection();
        verify(apacheClient, times(1))
                .start();
    }

    @Test
    public void testConnectStartsSessions() throws IOException {
        ConnectFuture connectFuture = mock(ConnectFuture.class);
        ClientSession session = mock(ClientSession.class);
        ClientChannel channel = mock(ClientChannel.class);
        AuthFuture authFuture = mock(AuthFuture.class);
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenReturn(connectFuture);
        when(connectFuture.getSession()).thenReturn(session);
        when(session.createChannel(ClientChannel.CHANNEL_SHELL))
                .thenReturn(channel);
        when(session.auth()).thenReturn(authFuture);

        SshConnectionFacade facade = makeDefaultFacade();
        facade.attemptConnection();
        verify(connectFuture, times(1)).getSession();
    }

    @Test
    public void testConnectConnectsClient() throws IOException {
        ConnectFuture connectFuture = mock(ConnectFuture.class);
        ClientSession session = mock(ClientSession.class);
        ClientChannel channel = mock(ClientChannel.class);
        AuthFuture authFuture = mock(AuthFuture.class);
        when(apacheClient.connect(anyString(), anyString(), anyInt()))
                .thenReturn(connectFuture);
        when(connectFuture.getSession()).thenReturn(session);
        when(session.createChannel(ClientChannel.CHANNEL_SHELL))
                .thenReturn(channel);
        when(session.auth()).thenReturn(authFuture);

        SshConnectionFacade facade = makeDefaultFacade();
        facade.attemptConnection();
        verify(apacheClient, times(1))
                .connect(anyString(), anyString(), anyInt());
    }

    @Test
    public void testClientIsStoppedAfterConnectionFailsWithIoException()
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

    private SshConnectionFacade makeDefaultFacade() {
        return makeFacade(serverDetails, apacheClient, streams);
    }


    private SshConnectionDetails makeMockServerDetails() {
        return mock(SshConnectionDetails.class);
    }

    private SshCommunicationStreams makeMockCommunicationStreams() {
        return mock(SshCommunicationStreams.class);
    }

    private SshClient makeMockApacheClient() {
        return mock(SshClient.class);
    }

    private SshConnectionFacade makeFacade(
            SshConnectionDetails sshConnectionDetails, SshClient apacheClient,
            SshCommunicationStreams streams) {
        return new SshConnectionFacade(
                sshConnectionDetails, apacheClient, streams);
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