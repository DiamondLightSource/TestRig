package ac.uk.diamond.gdaApi.service;

import ac.uk.diamond.gdaApi.client.GdaConnection;
import ac.uk.diamond.gdaApi.client.serialization.Deserializer;
import ac.uk.diamond.gdaApi.command.CommandResultSupplier;
import ac.uk.diamond.gdaApi.command.CommandRunner;
import ac.uk.diamond.gdaApi.command.CommandWriter;

import java.util.Arrays;
import java.util.List;


public class ScannableNameListerImpl implements ScannableNameLister {

    private final CommandRunner<String> runner;
    private final CommandResultSupplier<List<String>> resultSupplier;

    public ScannableNameListerImpl(
            CommandRunner<String> runner,
            CommandResultSupplier<List<String>> resultSupplier) {
        if ( runner == null )
            throw new IllegalArgumentException("Cannot have a null " +
                    "CommandRunner object");
        if ( resultSupplier == null )
            throw new IllegalArgumentException("Cannot have a null " +
                    "CommandResultSupplier object");
        this.runner = runner;
        this.resultSupplier = resultSupplier;
    }

    @Override
    public List<String> collectList() {
        runner.run("ls_names");
        return resultSupplier.nextMessage();
    }

}
