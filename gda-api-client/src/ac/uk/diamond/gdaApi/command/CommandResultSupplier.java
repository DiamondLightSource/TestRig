package ac.uk.diamond.gdaApi.command;

import java.io.IOException;

/**
 * Describes functionality for reading command output from an external console.
 */
public interface CommandResultSupplier<TCommandOutput> {
    /**
     * Reads the nextMessage available output from the console.
     * @return A TCommandOutput output.
     */
    TCommandOutput nextMessage();
}