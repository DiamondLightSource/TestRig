package ac.uk.diamond.gdaApi.command.io;

import java.io.IOException;

/**
 * Describes functionality for reading command output from an external console.
 */
public interface CommandOutput<TCommandOutput> {
    /**
     * Reads the next available output from the console.
     * @return A TCommandOutput output.
     */
    TCommandOutput next();
}
