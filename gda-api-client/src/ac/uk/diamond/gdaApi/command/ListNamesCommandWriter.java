package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.serialization.Serializer;

import java.io.*;
import java.util.function.Function;

/**
 * Allows user to run commands to and from a telnet server or similar.
 */
public class ListNamesCommandWriter implements CommandRunner<String> {

    private static final String NULL_OUTPUT_ERROR_MESSAGE
            = "Data output cannot be null";

    private DataOutput output;

    public ListNamesCommandWriter(
            DataOutput output) {
        exceptOutputIfNull(output);
        this.output = output;
    }

    private void exceptOutputIfNull(DataOutput output) {
        if ( output == null )
            throw new IllegalArgumentException(NULL_OUTPUT_ERROR_MESSAGE);
    }

    @Override
    public void run(String commandModel) {
        writeData(commandModel);
    }

    private void writeData(String serializedData) {
        try {
            output.writeChars(serializedData);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
