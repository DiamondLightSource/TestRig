package ac.uk.diamond.gdaApi;

import ac.uk.diamond.gdaApi.command.gda.GdaTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ac.uk.diamond.gdaApi.command.io.CommandIoTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CommandIoTestSuite.class, GdaTestSuite.class})
public class GdaApiTestSuite {}
