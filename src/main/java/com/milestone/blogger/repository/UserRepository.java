package com.milestone.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.milestone.blogger.model.User;
import java.util.Optional;

/**
 * Repository interface for accessing and managing user data.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for.
     * @return an {@link Optional} containing the user if found, or empty otherwise.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for.
     * @return an {@link Optional} containing the user if found, or empty otherwise.
     */
    Optional<User> findByEmail(String email);
}
