package com.slissner.codingtasks.dec.api.infrastructure.data;

import com.slissner.codingtasks.dec.api.application.CreateUserRequest;
import com.slissner.codingtasks.dec.api.application.UserService;
import com.slissner.codingtasks.dec.api.domain.User;
import com.slissner.codingtasks.dec.api.infrastructure.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** This class just generates some test users with test products. */
@Component
@AllArgsConstructor
@Slf4j
public class TestDataGenerator {

  private UserService userService;

  @PostConstruct
  public void init() {
    if (!shouldRun()) {
      log.trace("TestDataGenerator did not run because conditions were not met.");
      return;
    }

    Stream.of(
            new CreateUserRequest("test0@test.es", "Test123#"),
            new CreateUserRequest("test1@test.es", "Test123!"))
        .forEach(userService::createUser);

    log.info("Created test user data.");
  }

  private boolean shouldRun() {
    return !userService.hasAnyUsers();
  }
}
