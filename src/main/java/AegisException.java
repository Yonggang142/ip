/**
 * Represents an error caused by invalid user input in Aegis.
 */
public class AegisException extends Exception {
    /**
     * Returns void
     * Creates an exception with a message that can be shown to the user.
     *
     * @param message Explanation of the input error.
     */
    public AegisException(String message) {
        super(message);
    }
}
