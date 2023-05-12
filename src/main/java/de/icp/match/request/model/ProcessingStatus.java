package de.icp.match.request.model;

import lombok.Getter;

public enum ProcessingStatus {
    DRAFT(1),
    SUBMITTED(2),
    APPROVED(2),
    REJECTED(3),
    RECORDED_IN_SYSTEM(4),
    CONFIRMATION_PRESENT(5),
    CONFIRMATION_MISSING(6);

    @Getter
    private final Integer code;

    ProcessingStatus(Integer code) {
        this.code = code;
    }
}
