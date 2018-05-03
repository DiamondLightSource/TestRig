package ac.uk.diamond.gdaApi.client.serialization;

import java.util.Arrays;
import java.util.List;

public class ListNamesCommandOutputDeserializer
        implements Deserializer<String, List<String>> {

    private static final String DELIMETER = "\n";

    @Override
    public List<String> deserialize(String serializable) {
        return Arrays.asList(serializable.split(DELIMETER));
    }
}
