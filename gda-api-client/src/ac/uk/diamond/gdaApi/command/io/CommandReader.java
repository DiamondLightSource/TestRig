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
        if ( input == null )
            throw new IllegalArgumentException("Data input cannot be null");
        if ( deserializer == null )
            throw new IllegalArgumentException(
                    "Deserializer input cannot be null");
        this.input = input;
        this.deserializer = deserializer;
    }

    @Override
    public TOutputModel next() throws IOException {
        String raw = input.readUTF();
        return deserializer.deserialize(raw);
    }
}
