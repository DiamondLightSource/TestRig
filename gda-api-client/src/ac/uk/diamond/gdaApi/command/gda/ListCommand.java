package ac.uk.diamond.gdaApi.command.gda;

public class ListCommand {
    private final String commandString;

    public ListCommand() {
        this.commandString = "ls";
    }

    public String getCommandString() {
        return commandString;
    }
}
