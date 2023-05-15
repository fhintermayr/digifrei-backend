package de.icp.match.request.handler;

import de.icp.match.request.controller.ExemptionRequestController;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(assignableTypes = ExemptionRequestController.class)
public class ExemptionRequestExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, EntityNotFoundException.class})
    public ResponseEntity<Void> handleBadRequestException(Exception exception) {

        logException(exception);

        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<Void> handleClassCastException(Exception exception) {

        log.error("Tried to get exemption requests of a trainer: {} - {}",
                exception.getClass().getName(),
                exception.getMessage());

        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleUnknownException(Exception exception) {

        logException(exception);

        return ResponseEntity.internalServerError().build();
    }

    private void logException(Exception exception) {
        log.error("Failed to save new exemption request: {} - {}",
                exception.getClass().getName(),
                exception.getMessage());
    }

}