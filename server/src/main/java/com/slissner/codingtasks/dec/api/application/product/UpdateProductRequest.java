package com.slissner.codingtasks.dec.api.application.product;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.lang.NonNull;

public record UpdateProductRequest(
    @NonNull UUID id, @NonNull String name, @NonNull BigDecimal price) {}
