package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.GdaConnection;

/**
 * Allows user to run commands to and from a telnet server or similar.
 */
public class ListNamesCommandWriter implements CommandRunner<String> {

    private static final String NULL_OUTPUT_ERROR_MESSAGE
            = "GdaConnection cannot be null";

    private GdaConnection gda;

    public ListNamesCommandWriter(
            GdaConnection gda) {
        exceptOutputIfNull(gda);
        this.gda = gda;
    }

    private void exceptOutputIfNull(GdaConnection gda) {
        if ( gda == null )
            throw new IllegalArgumentException(NULL_OUTPUT_ERROR_MESSAGE);
    }

    @Override
    public void run(String message) {
        gda.sendMessage(message);
    }


}
