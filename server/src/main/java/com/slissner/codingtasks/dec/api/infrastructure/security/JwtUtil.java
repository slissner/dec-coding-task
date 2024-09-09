package com.slissner.codingtasks.dec.api.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class JwtUtil {
  private static final String ISSUER = "codetask";

  @Value("${slissner.jwt.signing.secret}")
  private String signingSecret;

  private Algorithm algorithm;
  private JWTVerifier verifier;

  @PostConstruct
  private void init() {
    algorithm = Algorithm.HMAC256(signingSecret);
    verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
  }

  /**
   * @throws IllegalArgumentException
   * @throws JWTCreationException
   */
  public String createToken(final String subject) {
    Preconditions.checkNotNull(algorithm, "Algorithm must not be null.");

    final Instant now = Instant.now();

    return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(subject)
        .withIssuedAt(now)
        .withNotBefore(now)
        .withExpiresAt(now.plus(Duration.ofHours(24)))
        .sign(algorithm);
  }

  /**
   * @throws JWTVerificationException
   */
  public DecodedJWT verify(final String token) {
    Preconditions.checkNotNull(verifier, "Verifier must not be null.");

    return verifier.verify(token);
  }
}
