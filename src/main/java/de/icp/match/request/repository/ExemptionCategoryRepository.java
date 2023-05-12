package de.icp.match.request.repository;

import de.icp.match.request.model.ExemptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemptionCategoryRepository extends JpaRepository<ExemptionCategory, Integer> {
}