package de.icp.match.user.exception;

public class DepartmentCreationException extends RuntimeException {

    public DepartmentCreationException(String message) {
        super(message);
    }

    public DepartmentCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
