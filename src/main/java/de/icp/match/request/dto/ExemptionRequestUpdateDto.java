package de.icp.match.request.dto;

import de.icp.match.request.model.ExemptionCategory;
import de.icp.match.request.model.ExemptionRequest;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ExemptionRequest}
 */
public record ExemptionRequestUpdateDto(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String reason,
        ExemptionCategory exemptionCategory
) implements Serializable {
}