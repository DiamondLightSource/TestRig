package ac.uk.diamond.gdaApi.service;

import ac.uk.diamond.gdaApi.service.ScannableListerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ListScannablesCommandTest.class,
        ScannableListerTest.class})
public class ServiceTestSuite { }
