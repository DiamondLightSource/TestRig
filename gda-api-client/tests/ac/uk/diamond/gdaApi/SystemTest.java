package ac.uk.diamond.gdaApi;


import ac.uk.diamond.gdaApi.client.GdaConnection;
import ac.uk.diamond.gdaApi.client.ssh.SshConnectionDetails;
import ac.uk.diamond.gdaApi.client.ssh.SshConnectionFacade;
import ac.uk.diamond.gdaApi.command.Command;
import ac.uk.diamond.gdaApi.command.ListNamesCommand;
import ac.uk.diamond.gdaApi.command.ListNamesCommandOutput;
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
//        ListNamesCommand command = new ListNamesCommand(p99);
//        ListNamesCommandOutput output = new ListNamesCommandOutput(p99);
//
//        ScannableNameLister lister = new ScannableNameListerImpl(
//                command, output);
//        List<String> names = lister.collectList();
//        System.out.println(names.toString());
    }
}
