package com.slissner.codingtasks.dec.api.application.product;

import java.math.BigDecimal;
import org.springframework.lang.NonNull;

public record CreateProductRequest(@NonNull String name, @NonNull BigDecimal price) {}
