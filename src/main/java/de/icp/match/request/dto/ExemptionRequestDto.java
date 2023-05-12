package de.icp.match.request.dto;

import de.icp.match.request.model.ExemptionCategory;
import de.icp.match.user.dto.apprentice.ApprenticeDto;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A DTO for the {@link de.icp.match.request.model.ExemptionRequest} entity
 */
public record ExemptionRequestDto(
        UUID id,
        @FutureOrPresent LocalDateTime startTime,
        @FutureOrPresent LocalDateTime endTime,
        @PastOrPresent LocalDateTime submissionDate,
        String reason,
        ExemptionCategory exemptionCategory,
        ApprenticeDto applicant,
        RequestProcessingDto requestProcessing
) implements Serializable {
}