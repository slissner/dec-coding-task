package com.slissner.codingtasks.dec.api.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "public")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Getter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The E-Mail address of an user.
   *
   * <p>note: A secondary index exists for this field.
   *
   * <p>TODO Consider renaming to `email`. I remember that due to some Spring magic, Spring prefers
   * the field to be named `username`. Therefore, we leave the name for the time being, although it
   * should be named `email.
   */
  @Column(unique = true, nullable = false)
  @NonNull
  private String username;

  @Column(nullable = false)
  @NonNull
  private String password;

  public static User create(@NonNull final String username, @NonNull final String password) {
    Preconditions.checkArgument(StringUtils.isNotBlank(username), "Username cannot be blank");
    Preconditions.checkArgument(StringUtils.isNotBlank(password), "Password cannot be blank");

    return User.builder().username(username).password(password).build();
  }
}
