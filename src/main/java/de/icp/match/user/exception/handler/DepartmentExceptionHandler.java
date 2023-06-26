package de.icp.match.user.exception.handler;

import de.icp.match.user.controller.DepartmentController;
import de.icp.match.user.exception.DepartmentCreationException;
import de.icp.match.user.exception.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = DepartmentController.class)
public class DepartmentExceptionHandler {

    @ExceptionHandler(DepartmentCreationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleDepartmentCreationException(DepartmentCreationException e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleUncaughtException(Exception e) {
        log.error("An unexpected exception occurred", e);
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
