package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.GdaConnection;

/**
 * Allows user to run commands to and from a telnet server or similar.
 */
public class ListNamesCommand implements Command<String> {

    private static final String NULL_OUTPUT_ERROR_MESSAGE
            = "GdaConnection cannot be null";
    public static final String COMMAND = "ls_names";

    private GdaConnection gda;

    public ListNamesCommand(
            GdaConnection gda) {
        exceptOutputIfNull(gda);
        this.gda = gda;
    }

    private void exceptOutputIfNull(GdaConnection gda) {
        if ( gda == null )
            throw new IllegalArgumentException(NULL_OUTPUT_ERROR_MESSAGE);
    }

    @Override
    public void run() {
        gda.sendMessage(COMMAND);
    }


}
