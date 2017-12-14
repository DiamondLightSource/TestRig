package ac.uk.diamond.gdaApi.command.io;

import ac.uk.diamond.gdaApi.serialization.Deserializer;

import java.io.DataInput;
import java.io.IOException;

public class CommandReader<TOutputModel>
        implements CommandOutput<TOutputModel> {
    private DataInput input;
    private Deserializer<String, TOutputModel> deserializer;

    public CommandReader(
            DataInput input, Deserializer<String, TOutputModel> deserializer) {
        exceptInputIfNull(input);
        this.input = input;
        exceptDeserializerIfNull(deserializer);
        this.deserializer = deserializer;
    }

    private void exceptDeserializerIfNull(Deserializer<String, TOutputModel> deserializer) {
        if ( deserializer == null )
            throw new IllegalArgumentException(
                    "Deserializer input cannot be null");
    }

    private void exceptInputIfNull(DataInput input) {
        if ( input == null )
            throw new IllegalArgumentException("Data input cannot be null");
    }

    @Override
    public TOutputModel next() {
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

    private TOutputModel tryToDeserialize(String serializedData) {
        return deserializer.deserialize(serializedData);
    }
}
