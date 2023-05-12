package de.icp.match.request.model;

import lombok.Getter;

public enum ExemptionCategory {
    MEDICAL_APPOINTMENT(1),
    OFFICE_APPOINTMENT(2),
    OTHER(3);

    @Getter
    private final Integer code;

    ExemptionCategory(Integer code) {
        this.code = code;
    }
}