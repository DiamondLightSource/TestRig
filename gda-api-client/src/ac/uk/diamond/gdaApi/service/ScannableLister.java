package ac.uk.diamond.gdaApi.service;

import ac.uk.diamond.gdaApi.command.CommandRunner;
import ac.uk.diamond.gdaApi.command.CommandResultSupplier;

import java.util.List;


public class ScannableLister
        implements ListScannablesService<List<String>> {
    private CommandRunner<ListScannablesCommand> runner;
    private CommandResultSupplier<List<String>> output;

    public ScannableLister(
            CommandRunner<ListScannablesCommand> runner,
            CommandResultSupplier<List<String>> output) {
        if ( runner == null )
            throw new IllegalArgumentException("Runner cannot be null");
        this.runner = runner;
        if ( output == null )
            throw new IllegalArgumentException("Output cannot be null");
        this.output = output;
    }

    @Override
    public List<String> collectList() {
        runner.run(new ListScannablesCommand());
        return output.next();
    }
}
