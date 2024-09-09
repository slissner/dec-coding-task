package com.slissner.codingtasks.dec.api.application;

import org.springframework.lang.NonNull;

public record CreateUserRequest(@NonNull String username, @NonNull String password) {}
