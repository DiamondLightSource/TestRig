package ac.uk.diamond.gdaApi.client.serialization;

public interface Serializer<TSerializable, TSerialized> {
    TSerialized serialize(TSerializable serializable);
}
