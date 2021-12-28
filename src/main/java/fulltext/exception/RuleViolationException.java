package fulltext.exception;

public class RuleViolationException extends RuntimeException {
    public static final String defaultMessage = "You have violated the code convention rules of Full Text Mapper.";

    public RuleViolationException() {
        super(defaultMessage);
    }

    public RuleViolationException(String message) {
        super(message);
    }
}
