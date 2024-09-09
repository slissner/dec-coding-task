package com.slissner.codingtasks.dec.api.infrastructure.repository;

import com.slissner.codingtasks.dec.api.domain.Product;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, UUID> {
  @Query("SELECT p FROM Product p " + "ORDER BY COALESCE(p.updatedAt, p.createdAt) DESC LIMIT 50")
  List<Product> findAllForProductsPage();
}
