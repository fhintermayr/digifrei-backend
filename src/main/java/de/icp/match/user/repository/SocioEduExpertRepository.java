package de.icp.match.user.repository;

import de.icp.match.user.model.SocioEduExpert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioEduExpertRepository extends JpaRepository<SocioEduExpert, Long> {
}