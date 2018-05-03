package ac.uk.diamond.gdaApi.command;

import java.io.IOException;

/**
 * Describes functionality for sending commands to an external console.
 */
public interface Command<TCommandModel> {
    /**
     * Runs a command
     * @param command The command to run.
     */
    void run(TCommandModel command);
}
