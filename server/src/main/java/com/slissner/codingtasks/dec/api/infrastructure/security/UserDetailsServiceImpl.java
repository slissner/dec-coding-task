package com.slissner.codingtasks.dec.api.infrastructure.security;

import static java.lang.String.format;

import com.slissner.codingtasks.dec.api.domain.User;
import com.slissner.codingtasks.dec.api.infrastructure.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final String DEFAULT_ROLE = "USER";
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        format("User not found for username. [username=%s]", username)));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(DEFAULT_ROLE)));
  }
}
