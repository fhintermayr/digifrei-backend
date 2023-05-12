package de.icp.match.request.repository;

import de.icp.match.request.model.ExemptionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemptionCategoryRepository extends JpaRepository<ExemptionCategoryEntity, Integer> {
}