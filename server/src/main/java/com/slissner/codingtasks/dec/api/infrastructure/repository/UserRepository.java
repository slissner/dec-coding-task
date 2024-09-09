package com.slissner.codingtasks.dec.api.infrastructure.repository;

import com.slissner.codingtasks.dec.api.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);
}
