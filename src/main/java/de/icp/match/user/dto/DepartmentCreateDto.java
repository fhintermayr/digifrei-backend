package de.icp.match.user.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link de.icp.match.user.model.Department}
 */
public record DepartmentCreateDto(@NotBlank String name) implements Serializable {
}