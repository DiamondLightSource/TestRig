package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;
import ac.uk.diamond.gdaApi.command.io.CommandOutput;


public class ListScannablesServiceImpl<TOutput>
        implements ListScannablesService<TOutput> {
    private CommandModelRunner<ListScannablesCommand> runner;
    private CommandOutput<TOutput> output;

    public ListScannablesServiceImpl(
            CommandModelRunner<ListScannablesCommand> runner,
            CommandOutput<TOutput> output) {
        if ( runner == null )
            throw new IllegalArgumentException("Runner cannot be null");
        this.runner = runner;
        if ( output == null )
            throw new IllegalArgumentException("Output cannot be null");
        this.output = output;
    }

    @Override
    public TOutput collectList() {
        runner.run(new ListScannablesCommand());
        return output.next();
    }
}
