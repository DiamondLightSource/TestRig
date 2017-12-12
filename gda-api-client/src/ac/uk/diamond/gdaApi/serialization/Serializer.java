package ac.uk.diamond.gdaApi.serialization;

public interface Serializer<TSerializable, TSerialized> {
    TSerialized serialize(TSerializable serializable);
}
