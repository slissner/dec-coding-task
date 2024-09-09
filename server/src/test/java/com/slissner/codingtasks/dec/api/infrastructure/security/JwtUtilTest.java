package com.slissner.codingtasks.dec.api.infrastructure.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

  @Autowired JwtUtil jwtUtil;

  @Test
  void test_createToken_givenToken_expect_canVerifyAndExtract() {
    final String token = jwtUtil.createToken("testuser");

    final DecodedJWT decodedToken = jwtUtil.verify(token);

    Assertions.assertNotNull(decodedToken);

    final String subject = decodedToken.getSubject();

    Assertions.assertEquals("testuser", subject);
  }
}
