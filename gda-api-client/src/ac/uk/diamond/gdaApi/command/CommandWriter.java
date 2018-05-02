package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.serialization.Serializer;

import java.io.*;
import java.util.function.Function;

/**
 * Allows user to run commands to and from a telnet server or similar.
 */
public class CommandWriter<TCommandModel>
        implements CommandRunner<TCommandModel> {

    private static final String NULL_OUTPUT_ERROR_MESSAGE
            = "Data output cannot be null";
    private static final String NULL_SERIALIZER_ERROR_MESSAGE
            = "Deserializer cannot be null";

    private DataOutput output;
    private Serializer<TCommandModel, String> serializer;

    public CommandWriter(
            DataOutput output, Serializer<TCommandModel, String> serializer) {
        exceptOutputIfNull(output);
        this.output = output;
        exceptSerializerIfNull(serializer);
        this.serializer = serializer;
    }

    private void exceptSerializerIfNull(
            Serializer<TCommandModel, String> serializer) {
        if ( serializer == null )
            throw new IllegalArgumentException(NULL_SERIALIZER_ERROR_MESSAGE);
    }

    private void exceptOutputIfNull(DataOutput output) {
        if ( output == null )
            throw new IllegalArgumentException(NULL_OUTPUT_ERROR_MESSAGE);
    }

    @Override
    public void run(TCommandModel commandModel) {
        String serializedCommand = serializeCommandModel(commandModel);
        writeData(serializedCommand);
    }

    private void writeData(String serializedData) {
        try {
            output.writeChars(serializedData);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String serializeCommandModel(TCommandModel commandModel) {
        return serializer.serialize(commandModel);
    }
}
