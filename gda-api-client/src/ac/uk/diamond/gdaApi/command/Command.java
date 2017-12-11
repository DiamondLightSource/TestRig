package ac.uk.diamond.gdaApi.command;

/**
 * Object representing a command
 */
public interface Command {
    /**
     * Run this command, all output is delegated downstream.
     */
    void run();
}
