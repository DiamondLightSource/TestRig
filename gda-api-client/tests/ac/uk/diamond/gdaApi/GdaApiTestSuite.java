package ac.uk.diamond.gdaApi;

import ac.uk.diamond.gdaApi.client.ssh.SshTestSuite;
import ac.uk.diamond.gdaApi.command.CommandTestSuite;
import ac.uk.diamond.gdaApi.service.ServiceTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CommandTestSuite.class, ServiceTestSuite.class,
        SshTestSuite.class})
public class GdaApiTestSuite {}
