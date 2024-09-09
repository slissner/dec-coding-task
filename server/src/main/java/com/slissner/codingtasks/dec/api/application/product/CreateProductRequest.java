package com.slissner.codingtasks.dec.api.application.product;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public record CreateProductRequest(@NonNull String name, @NonNull BigDecimal price) {}
