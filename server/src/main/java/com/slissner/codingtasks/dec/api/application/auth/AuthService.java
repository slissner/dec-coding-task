package com.slissner.codingtasks.dec.api.application.auth;

import com.slissner.codingtasks.dec.api.infrastructure.security.JwtUtil;
import com.slissner.codingtasks.dec.api.infrastructure.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

  final JwtUtil jwtUtil;
  final PasswordEncoder passwordEncoder;
  final UserDetailsServiceImpl userDetailsService;

  /**
   * @param email
   * @param clearTextPassword
   * @return the JWT to be used in the client as bearer token
   */
  public String authenticateUser(
      @NonNull final String email, @NonNull final String clearTextPassword) {
    Preconditions.checkArgument(StringUtils.isNotBlank(email), "E-Mail must not be blank.");
    Preconditions.checkArgument(
        StringUtils.isNotBlank(clearTextPassword), "Clear text password must not be blank.");

    final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

    final String encodedPassword = userDetails.getPassword();

    if (!passwordEncoder.matches(clearTextPassword, encodedPassword)) {
      throw new InsufficientAuthenticationException(
          format("User could not be authenticated. [username=%s]", email));
    }

    return jwtUtil.createToken(email);
  }
}
