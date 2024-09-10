package com.slissner.codingtasks.dec.api.infrastructure.controller;

import com.slissner.codingtasks.dec.api.application.product.ProductService;
import com.slissner.codingtasks.dec.api.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
@AllArgsConstructor
class ProductsController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ProductsResponseDto> findForProductsPage() {

    final List<Product> products = productService.findProductsForProductsPage();

    final ProductsResponseDto response = ProductsResponseDto.from(products);

    return ResponseEntity.ok(response);
  }

  public record ProductsResponseDto(List<Entry> products) {

    public static ProductsResponseDto from(final List<Product> products) {
      final List<ProductsResponseDto.Entry> productEntries =
          products.stream().map(ProductsResponseDto.Entry::from).toList();

      return new ProductsResponseDto(productEntries);
    }

    public record Entry(UUID id, String name, BigDecimal price) {
      private static Entry from(final Product product) {
        return new Entry(product.getId(), product.getName(), product.getPrice());
      }
    }
  }
}
