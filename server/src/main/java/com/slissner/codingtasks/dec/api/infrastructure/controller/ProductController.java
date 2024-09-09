package com.slissner.codingtasks.dec.api.infrastructure.controller;

import com.slissner.codingtasks.dec.api.application.product.ProductService;
import com.slissner.codingtasks.dec.api.domain.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
@AllArgsConstructor
class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ProductsResponseDto> findForProductsPage() {

    final List<Product> products = productService.findProductsForProductsPage();

    return ResponseEntity.ok(new ProductsResponseDto(products));
  }

  public record ProductsResponseDto(List<Product> products) {}
}
