package com.slissner.codingtasks.dec.api.infrastructure.security;

import static java.lang.String.format;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
@Slf4j
public class BearerTokenRequestFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
      throws ServletException, IOException {

    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      // User has been authenticated already... skipping checks
      chain.doFilter(request, response);
      return;
    }

    final Optional<String> optionalUsername = extractUsername(request);

    // note: Cannot use ifPresentOrElse here easily due to checked exceptions and insufficient java
    // type system.
    if (optionalUsername.isEmpty()) {
      chain.doFilter(request, response);
      return;
    }

    final String username =
        optionalUsername.orElseThrow(
            () ->
                new IllegalStateException(
                    "Username should be present when processing bearer auth, but was not."));

    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

    if (userDetails == null) {
      logger.warn(
          format(
              "Could extract username from JWT, but username was not found in database – has the user been deleted "
                  + "meanwhile or other inconsistency? [username=%s]",
              username));
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token.");
      chain.doFilter(request, response);
      return;
    }

    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    usernamePasswordAuthenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    chain.doFilter(request, response);
  }

  private Optional<String> extractUsername(final HttpServletRequest request) {
    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      return Optional.empty();
    }

    final String jwt =
        authorizationHeader
            // Extract text starting after "Bearer "
            .substring(7);

    try {
      final DecodedJWT decodedJWT = jwtUtil.verify(jwt);

      return Optional.ofNullable(decodedJWT.getSubject());
    } catch (final Exception e) {
      return Optional.empty();
    }
  }
}
