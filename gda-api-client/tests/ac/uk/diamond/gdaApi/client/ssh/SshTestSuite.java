package ac.uk.diamond.gdaApi.client.ssh;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SshConnectionFacadeTest.class,
        SshConnectionDetailsTest.class, SshCommunicationStreamsTest.class})
public class SshTestSuite {}