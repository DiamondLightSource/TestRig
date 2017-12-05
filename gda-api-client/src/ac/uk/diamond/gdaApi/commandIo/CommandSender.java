package ac.uk.diamond.gdaApi.commandIo;

/**
 * Describes functionality for sending commands to an external console.
 */
public interface CommandSender {
    /**
     * Send a command to an external console.
     * @param command The command to send.
     */
    void sendCommand(String command);
}
