package ac.uk.diamond.gdaApi.client;

public interface GdaConnection extends AutoCloseable{
    void sendMessage(String message);
    String nextMessage();
}
