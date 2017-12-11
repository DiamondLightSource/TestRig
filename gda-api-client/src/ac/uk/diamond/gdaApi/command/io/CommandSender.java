package ac.uk.diamond.gdaApi.command.io;

import java.io.IOException;

/**
 * Describes functionality for sending commands to an external console.
 */
public interface CommandSender<TCommand> {
    /**
     * Runs a command
     * @param command The command to send.
     * @throws IOException If unable to send.
     */
    void send(TCommand command) throws IOException;
}
