package com.slissner.codingtasks.dec.api.application.user;

import org.springframework.lang.NonNull;

public record CreateUserRequest(@NonNull String username, @NonNull String password) {}
