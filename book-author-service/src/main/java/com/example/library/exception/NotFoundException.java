package com.example.library.exception;

/**
 * Исключение для ресурсов, которые не найдены
 */
public class NotFoundException extends RuntimeException {

    private final String resourceName;
    private final Long resourceId;

    public NotFoundException(String resourceName, Long resourceId) {
        super(String.format("%s с ID %d не найден", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public NotFoundException(String message) {
        super(message);
        this.resourceName = null;
        this.resourceId = null;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Long getResourceId() {
        return resourceId;
    }
}
