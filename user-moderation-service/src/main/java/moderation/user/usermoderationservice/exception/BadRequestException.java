package moderation.user.usermoderationservice.exception;

public class BadRequestException extends RuntimeException {

    private final String errorCode;
    private final String details;

    public BadRequestException(String message) {
        super(message);
        this.errorCode = "BAD_REQUEST";
        this.details = null;
    }

    public BadRequestException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = null;
    }

    public BadRequestException(String message, String errorCode, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }
}
