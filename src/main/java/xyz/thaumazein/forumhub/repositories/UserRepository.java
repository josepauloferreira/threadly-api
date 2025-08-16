package xyz.thaumazein.forumhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.thaumazein.forumhub.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}