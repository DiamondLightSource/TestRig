package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.io.CommandRunner;
import ac.uk.diamond.gdaApi.command.io.CommandOutputSupplier;

import java.util.List;


public class ScannableLister
        implements ListScannablesService<List<String>> {
    private CommandRunner<ListScannablesCommand> runner;
    private CommandOutputSupplier<List<String>> output;

    public ScannableLister(
            CommandRunner<ListScannablesCommand> runner,
            CommandOutputSupplier<List<String>> output) {
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
