package ac.uk.diamond.gdaApi.serialization;

public class DeserializationException extends Exception {
    private String errorMessage;

    public DeserializationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
