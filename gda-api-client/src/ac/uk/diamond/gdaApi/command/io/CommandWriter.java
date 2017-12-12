package ac.uk.diamond.gdaApi.command.io;

import java.io.*;

/**
 * Allows user to send commands to and from a telnet server or similar.
 */
public class CommandWriter<TCommandModel>
        implements CommandModelRunner<TCommandModel> {

    private DataOutput writer;

    public CommandWriter(DataOutput writer) {
        this.writer = writer;
    }

    @Override
    public void send(TCommandModel commandModel) throws IOException {
        writer.writeChars(commandModel.toString());
    }
}
