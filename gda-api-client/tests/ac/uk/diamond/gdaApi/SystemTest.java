package ac.uk.diamond.gdaApi;


import ac.uk.diamond.gdaApi.client.ssh.SshConnectionDetails;
import ac.uk.diamond.gdaApi.client.ssh.SshConnectionFacade;
import ac.uk.diamond.gdaApi.command.CommandReader;
import ac.uk.diamond.gdaApi.command.CommandResultSupplier;
import ac.uk.diamond.gdaApi.command.CommandRunner;
import ac.uk.diamond.gdaApi.command.CommandWriter;
import ac.uk.diamond.gdaApi.service.ScannableNameLister;
import ac.uk.diamond.gdaApi.service.ScannableNameListerImpl;
import org.apache.sshd.client.SshClient;

import java.util.List;

public class SystemTest {
    public static void main(String[] args) {
        SystemTest test = new SystemTest();
        test.listScannables();
    }

    public void listScannables() {
//        SshConnectionDetails p99Details = new SshConnectionDetails(
//                "vid18871", "p99-control.diamond.ac.uk", 2222);
//        SshClient apacheClient = SshClient.setUpDefaultClient();
//        GdaConnection p99 = new SshConnectionFacade(p99Details, apacheClient);
//
//        ScannableNameLister lister = new ScannableNameListerImpl(p99);
//        List<String> names = lister.collectList();
//        System.out.println(names.toString());
    }
}
