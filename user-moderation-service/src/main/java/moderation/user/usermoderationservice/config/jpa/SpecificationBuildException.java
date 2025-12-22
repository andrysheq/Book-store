package moderation.user.usermoderationservice.config.jpa;

public class SpecificationBuildException extends RuntimeException {
    public SpecificationBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpecificationBuildException(String message) {
        super(message);
    }
}
