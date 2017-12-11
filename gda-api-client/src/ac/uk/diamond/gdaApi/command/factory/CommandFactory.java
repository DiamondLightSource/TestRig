package ac.uk.diamond.gdaApi.command.factory;

import ac.uk.diamond.gdaApi.command.Command;

/**
 * Factory, creates new {@link Command}s
 */
public interface CommandFactory {
    /**
     * Make a new command.
     * @return A new {@link Command} instance
     */
    Command makeCommand();
}
