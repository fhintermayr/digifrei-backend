package de.icp.match.user.exception.handler;

import de.icp.match.user.controller.SocioEduExpertController;
import de.icp.match.user.exception.DuplicateEmailException;
import de.icp.match.user.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = SocioEduExpertController.class)
public class SocioEduExpertExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleDuplicateEmailException(DuplicateEmailException e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleUncaughtException(Exception e) {
        log.error("An unexpected exception occurred", e);
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
