package ac.uk.diamond.gdaApi.commandIo;

/**
 * Describes functionality for reading command output from an external console.
 */
public interface CommandOutputReader {
    /**
     * Reads the next available output from the console.
     * @return A string output.
     */
    String readOutput();
}
