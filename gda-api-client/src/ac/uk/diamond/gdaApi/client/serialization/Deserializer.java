package ac.uk.diamond.gdaApi.client.serialization;

public interface Deserializer<TDeserializable, TDeserialized> {
    TDeserialized deserialize(TDeserializable serializable);
}
