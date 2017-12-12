package ac.uk.diamond.gdaApi.command.io;

import ac.uk.diamond.gdaApi.serialization.Serializer;

import java.io.*;

/**
 * Allows user to send commands to and from a telnet server or similar.
 */
public class CommandWriter<TCommandModel>
        implements CommandModelRunner<TCommandModel> {

    private DataOutput output;
    private Serializer<TCommandModel, String> serializer;

    public CommandWriter(DataOutput output, Serializer<TCommandModel, String> serializer) {
        exceptOutputIfNull(output);
        exceptSerializerIfNull(serializer);
        this.output = output;
        this.serializer = serializer;
    }

    private void exceptSerializerIfNull(Serializer<TCommandModel, String> serializer) {
        if ( serializer == null )
            throw new IllegalArgumentException("Serializer cannot be null");
    }

    private void exceptOutputIfNull(DataOutput output) {
        if ( output == null )
            throw new IllegalArgumentException("Data output cannot be null");
    }

    @Override
    public void send(TCommandModel commandModel) throws IOException {
        output.writeChars(commandModel.toString());
    }
}
