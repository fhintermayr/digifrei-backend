package de.icp.match.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SocioEduExpertRepository extends JpaRepository<SocioEduExpert, UUID> {
}