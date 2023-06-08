package de.icp.match.request.dto;

import de.icp.match.request.model.ProcessingStatus;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link de.icp.match.request.model.RequestProcessing}
 */
public record RequestProcessingUpdateDto(
        @NotNull ProcessingStatus processingStatus,
        String comment
) implements Serializable {
}