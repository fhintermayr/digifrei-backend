package de.icp.match.user.repository;

import de.icp.match.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    default List<User> findUsersContaining(String searchTerm) {
        return findByFirstNameContainsOrLastNameContainsOrUsernameContainsAllIgnoreCaseOrderByFirstNameDesc(searchTerm, searchTerm, searchTerm);
    }

    List<User> findByFirstNameContainsOrLastNameContainsOrUsernameContainsAllIgnoreCaseOrderByFirstNameDesc(String firstName, String lastName, String username);

}
