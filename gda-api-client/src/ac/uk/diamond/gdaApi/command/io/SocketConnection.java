package ac.uk.diamond.gdaApi.command.io;

import java.io.*;
import java.net.Socket;

/**
 * Allows user to send commands to and from a telnet server or similar.
 */
public class SocketConnection implements CommandSender<String>, CommandOutputReceiver<String> {

    private final String hostName;
    private final int portNumber;
    private Socket socket;
    private DataOutput writer;
    private DataInput reader;

    public SocketConnection(String hostName, int portNumber) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
        socket = new Socket(hostName, portNumber);
        writer = new DataOutputStream(socket.getOutputStream());
        reader = new DataInputStream(socket.getInputStream());
    }

    public String getHostName() {
        return hostName;
    }

    public int getPortNumber() {
        return portNumber;
    }

    @Override
    public String next() throws IOException {
        return reader.readUTF();
    }

    @Override
    public void send(String command) throws IOException {
        writer.writeChars(command);
    }
}
