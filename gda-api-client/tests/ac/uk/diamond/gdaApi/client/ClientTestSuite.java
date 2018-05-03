package ac.uk.diamond.gdaApi.client;


import ac.uk.diamond.gdaApi.client.serialization.ListNamesCommandOutputDeserializerTest;
import ac.uk.diamond.gdaApi.client.serialization.SerializationTestSuite;
import ac.uk.diamond.gdaApi.client.ssh.SshTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SshTestSuite.class, SerializationTestSuite.class})
public class ClientTestSuite {}
