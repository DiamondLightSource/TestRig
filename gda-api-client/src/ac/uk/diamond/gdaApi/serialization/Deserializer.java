package ac.uk.diamond.gdaApi.serialization;

public interface Deserializer<TDeserializable, TDeserialized> {
    TDeserialized deserialize(
            TDeserializable deserializable) throws DeserializationException;
}
