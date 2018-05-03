package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.GdaConnection;
import ac.uk.diamond.gdaApi.client.serialization.Deserializer;

import java.util.List;

public class ListNamesCommandOutput
        implements CommandResultSupplier<List<String>> {
    private GdaConnection gda;
    private Deserializer<String, List<String>> deserializer;

    public ListNamesCommandOutput(
            GdaConnection gda, Deserializer<String, List<String>> deserializer) {
        exceptInputIfNull(gda);
        this.gda = gda;
        exceptDeserializerIfNull(deserializer);
        this.deserializer = deserializer;
    }

    private void exceptDeserializerIfNull(Deserializer<String,
            List<String>> deserializer) {
        if ( deserializer == null )
            throw new IllegalArgumentException(
                    "Deserializer cannot be null");
    }

    private void exceptInputIfNull(GdaConnection input) {
        if ( input == null )
            throw new IllegalArgumentException("GdaConnection cannot be null");
    }

    @Override
    public List<String> nextMessage() {
        String raw = readRawInput();
        return tryToDeserialize(raw);
    }

    private String readRawInput() {
        return gda.nextMessage();
    }

    private List<String> tryToDeserialize(String serializedData) {
        return deserializer.deserialize(serializedData);
    }
}
