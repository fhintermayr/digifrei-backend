package de.icp.match.request.model;

import lombok.Getter;

public enum ProcessingStatus {
    DRAFT(1),
    SUBMITTED(2),
    APPROVED(3),
    REJECTED(4),
    RECORDED_IN_SYSTEM(5),
    CONFIRMATION_PRESENT(6),
    CONFIRMATION_MISSING(7);

    @Getter
    private final Integer code;

    ProcessingStatus(Integer code) {
        this.code = code;
    }
}
