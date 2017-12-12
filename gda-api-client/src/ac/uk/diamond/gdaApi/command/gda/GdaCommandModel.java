package ac.uk.diamond.gdaApi.command.gda;

public class GdaCommandModel {
    public static final String NULL_COMMAND_ERROR_MESSAGE = "Command cannot be null";

    private final String commandString;

    public GdaCommandModel(String commandString) {
        exceptCommandStringIfNull(commandString);
        this.commandString = commandString;
    }

    private void exceptCommandStringIfNull(String command) {
        if ( command == null )
            throw new IllegalArgumentException(NULL_COMMAND_ERROR_MESSAGE);
    }

    public String getCommandString() {
        return commandString;
    }
}
