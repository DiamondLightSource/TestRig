package ac.uk.diamond.gdaApi.command.gda.listCommand;

import ac.uk.diamond.gdaApi.command.Command;
import ac.uk.diamond.gdaApi.command.gda.GdaCommandModel;
import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;

import java.io.IOException;

public class ListCommand implements Command {
    private CommandModelRunner<GdaCommandModel> runner;
    private GdaCommandModel commandModel;

    public ListCommand(CommandModelRunner<GdaCommandModel> runner,
                       GdaCommandModel commandModel) {
        if  ( runner == null )
            throw new IllegalArgumentException("Command runner cannot be null");
        this.runner = runner;
        if ( commandModel == null )
            throw new IllegalArgumentException("Command model cannot be null");
        this.commandModel = commandModel;
    }

    @Override
    public void run() {

    }
}

