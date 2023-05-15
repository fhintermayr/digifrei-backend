package de.icp.match.request.dto;

import de.icp.match.request.model.ExemptionCategory;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link de.icp.match.request.model.ExemptionRequest} entity
 */
public record ExemptionRequestSubmissionDto(
        @FutureOrPresent LocalDateTime startTime,
        @FutureOrPresent LocalDateTime endTime,
        String reason,
        @NotNull ExemptionCategory exemptionCategory,
        @NotNull Integer applicantId
) implements Serializable {
}