package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.Command;
import ac.uk.diamond.gdaApi.command.gda.ListCommandModel;
import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;

public class ListCommand implements Command {
    private CommandModelRunner<ListCommandModel> runner;
    private ListCommandModel commandModel;

    public ListCommand(CommandModelRunner<ListCommandModel> runner,
                       ListCommandModel commandModel) {
        if ( runner == null )
            throw new IllegalArgumentException("Command runner cannot be null");
        this.runner = runner;
        if ( commandModel == null )
            throw new IllegalArgumentException("Command model cannot be null");
        this.commandModel = commandModel;
    }

    @Override
    public void run() {
        runner.run(commandModel);
    }
}

