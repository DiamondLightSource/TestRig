package ac.uk.diamond.gdaApi.command.io;

import ac.uk.diamond.gdaApi.serialization.Serializer;

import java.io.*;

/**
 * Allows user to run commands to and from a telnet server or similar.
 */
public class CommandWriter<TCommandModel>
        implements CommandModelRunner<TCommandModel> {

    private static final String NULL_OUTPUT_ERROR_MESSAGE
            = "Data output cannot be null";
    private static final String NULL_SERIALIZER_ERROR_MESSAGE
            = "Serializer cannot be null";

    private DataOutput output;
    private Serializer<TCommandModel, String> serializer;

    public CommandWriter(
            DataOutput output, Serializer<TCommandModel, String> serializer) {
        exceptOutputIfNull(output);
        exceptSerializerIfNull(serializer);
        this.output = output;
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
    public void run(TCommandModel commandModel) throws IOException {
        String serialized = serializer.serialize(commandModel);
        output.writeChars(serialized);
    }
}
