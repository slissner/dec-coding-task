package com.slissner.codingtasks.dec.api.infrastructure.controller;


import com.slissner.codingtasks.dec.api.application.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@AllArgsConstructor
class AuthController {

  private final AuthService authService;

  @PostMapping
  public ResponseEntity<AuthResponseDto> authenticateUser(
      @RequestBody final AuthRequestDto request) {

    final String token = authService.authenticateUser(request.email(), request.password());

    return ResponseEntity.ok(new AuthResponseDto(request.email(), token));
  }

  public record AuthRequestDto(String email, String password) {}

  public record AuthResponseDto(String email, String token) {}
}
