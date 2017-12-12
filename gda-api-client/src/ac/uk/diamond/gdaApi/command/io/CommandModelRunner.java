package ac.uk.diamond.gdaApi.command.io;

import java.io.IOException;

/**
 * Describes functionality for sending commands to an external console.
 */
public interface CommandModelRunner<TCommand> {
    /**
     * Runs a command
     * @param command The command to run.
     * @throws IOException If unable to run.
     */
    void run(TCommand command) throws IOException;
}
