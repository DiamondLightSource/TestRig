package ac.uk.diamond.gdaApi.service;

import ac.uk.diamond.gdaApi.command.Command;
import ac.uk.diamond.gdaApi.command.CommandResultSupplier;

import java.util.List;


public class ScannableNameListerImpl implements ScannableNameLister {

    private final Command<String> runner;
    private final CommandResultSupplier<List<String>> resultSupplier;

    public ScannableNameListerImpl(
            Command<String> runner,
            CommandResultSupplier<List<String>> resultSupplier) {
        if ( runner == null )
            throw new IllegalArgumentException("Cannot have a null " +
                    "Command object");
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
