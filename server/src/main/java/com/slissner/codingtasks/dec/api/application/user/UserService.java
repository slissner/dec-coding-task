package com.slissner.codingtasks.dec.api.application.user;

import com.slissner.codingtasks.dec.api.domain.User;
import com.slissner.codingtasks.dec.api.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public boolean hasAnyUsers() {
    return userRepository.count() > 0;
  }

  @Transactional
  public User createUser(@NonNull final CreateUserRequest request) {
    final String encryptedPassword = passwordEncoder.encode(request.password());

    final User newUser = User.create(request.username(), encryptedPassword);

    userRepository.save(newUser);

    log.info("New user has been created. [user.id={}]", newUser.getId());

    return newUser;
  }

}
