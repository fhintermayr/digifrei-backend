package de.icp.match.request.dto;

import de.icp.match.request.model.ProcessingStatus;
import de.icp.match.user.dto.TrainerDto;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A DTO for the {@link de.icp.match.request.model.RequestProcessing} entity
 */
public record RequestProcessingDto(
        UUID id,
        ProcessingStatus processingStatus,
        String comment,
        @PastOrPresent LocalDateTime processing_date,
        TrainerDto processor
) implements Serializable {
}