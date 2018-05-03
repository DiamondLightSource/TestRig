package ac.uk.diamond.gdaApi.client.serialization;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ListNamesCommandOutputDeserializerTest {
    @Test
    public void TestSingleItemTurnedToSingleItemList() {
        ListNamesCommandOutputDeserializer deserializer
                = new ListNamesCommandOutputDeserializer();
        List<String> singleItemList = Arrays.asList("Red");
        assertEquals(singleItemList, deserializer.deserialize("Red"));
    }

    @Test
    public void TestMultipleItemsTurnedToMultileItemList() {
        ListNamesCommandOutputDeserializer deserializer
                = new ListNamesCommandOutputDeserializer();
        List<String> singleItemList = Arrays.asList("Red", "Green");
        assertEquals(singleItemList, deserializer.deserialize("Red\nGreen"));
    }
}