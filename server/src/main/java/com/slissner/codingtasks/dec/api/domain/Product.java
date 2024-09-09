package com.slissner.codingtasks.dec.api.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "products", schema = "public")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Getter
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  @NonNull
  private Instant createdAt;

  private Instant updatedAt;

  @Column(unique = true, nullable = false)
  @NonNull
  private String name;

  @Column(nullable = false)
  @NonNull
  private BigDecimal price;

  public static Product create(@NonNull final String name, @NonNull final BigDecimal price) {
    Preconditions.checkArgument(StringUtils.isNotBlank(name), "Username cannot be blank");
    Preconditions.checkArgument(
        price.compareTo(BigDecimal.ZERO) > 0, "Price must be of positive value");

    return Product.builder()
        .createdAt(Instant.now())
        .updatedAt(null)
        .name(name)
        .price(price)
        .build();
  }
}
