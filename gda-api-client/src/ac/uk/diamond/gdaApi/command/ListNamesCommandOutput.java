package ac.uk.diamond.gdaApi.command;

import ac.uk.diamond.gdaApi.client.serialization.Deserializer;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class ListNamesCommandOutput
        implements CommandResultSupplier<List<String>> {
    private DataInput input;
    private Deserializer<String, List<String>> deserializer;

    public ListNamesCommandOutput(
            DataInput input, Deserializer<String, List<String>> deserializer) {
        exceptInputIfNull(input);
        this.input = input;
        exceptDeserializerIfNull(deserializer);
        this.deserializer = deserializer;
    }

    private void exceptDeserializerIfNull(Deserializer<String,
            List<String>> deserializer) {
        if ( deserializer == null )
            throw new IllegalArgumentException(
                    "Deserializer input cannot be null");
    }

    private void exceptInputIfNull(DataInput input) {
        if ( input == null )
            throw new IllegalArgumentException("Data input cannot be null");
    }

    @Override
    public List<String> nextMessage() {
        String raw = tryToReadRawInput();
        return tryToDeserialize(raw);
    }

    private String tryToReadRawInput() {
        try {
            return input.readUTF();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private List<String> tryToDeserialize(String serializedData) {
        return deserializer.deserialize(serializedData);
    }
}
