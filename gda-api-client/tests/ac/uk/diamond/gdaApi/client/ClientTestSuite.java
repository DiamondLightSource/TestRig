package ac.uk.diamond.gdaApi.client;

import ac.uk.diamond.gdaApi.command.CommandTestSuite;
import ac.uk.diamond.gdaApi.service.ServiceTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({GdaSshClientTest.class})
public class ClientTestSuite {}
