package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;
import ac.uk.diamond.gdaApi.command.io.CommandOutput;

import java.util.Arrays;
import java.util.List;


public class ScannableLister
        implements ListScannablesService<List<String>> {
    private CommandModelRunner<ListScannablesCommand> runner;
    private CommandOutput<List<String>> output;

    public ScannableLister(
            CommandModelRunner<ListScannablesCommand> runner,
            CommandOutput<List<String>> output) {
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
