package ac.uk.diamond.gdaApi.command;

import java.io.IOException;

/**
 * Describes functionality for running Gda commands
 */
public interface Command<TCommandModel> {
    /**
     * Runs a command
     */
    void run();
}
