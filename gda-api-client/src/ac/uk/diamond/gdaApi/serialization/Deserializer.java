package ac.uk.diamond.gdaApi.serialization;

import java.io.IOException;

public interface Deserializer<TDeserializable, TDeserialized> {
    TDeserialized deserialize(
            TDeserializable deserializable) throws IOException;
}
