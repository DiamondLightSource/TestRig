package ac.uk.diamond.gdaApi.command;

import java.io.IOException;

/**
 * Describes functionality for reading command output from an external console.
 */
public interface CommandOutputSupplier<TCommandOutput> {
    /**
     * Reads the next available output from the console.
     * @return A TCommandOutput output.
     */
    TCommandOutput next();
}
