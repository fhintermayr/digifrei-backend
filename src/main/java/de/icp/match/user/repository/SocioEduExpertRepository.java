package de.icp.match.user.repository;

import de.icp.match.user.model.SocioEduExpert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioEduExpertRepository extends JpaRepository<SocioEduExpert, Long> {

    default List<SocioEduExpert> findAllContainingSearchTerm(String searchTerm) {
        return findTop10ByFirstNameOrLastNameOrEmailAllIgnoreCaseOrderByFirstNameDesc(searchTerm, searchTerm, searchTerm);
    }

    List<SocioEduExpert> findTop10ByFirstNameOrLastNameOrEmailAllIgnoreCaseOrderByFirstNameDesc(String firstName, String lastName, String email);

}