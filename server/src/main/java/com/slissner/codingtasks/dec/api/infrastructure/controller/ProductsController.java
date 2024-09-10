package com.slissner.codingtasks.dec.api.infrastructure.controller;

import com.slissner.codingtasks.dec.api.application.product.CreateProductRequest;
import com.slissner.codingtasks.dec.api.application.product.ProductService;
import com.slissner.codingtasks.dec.api.application.product.UpdateProductRequest;
import com.slissner.codingtasks.dec.api.domain.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
@AllArgsConstructor
class ProductsController {

  private final ProductService productService;

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product could not be found")
  @ExceptionHandler(value = Product.ProductNotFoundException.class)
  private static void handleProductNotFoundException() {}

  @GetMapping
  public ResponseEntity<ProductsResponseDto> findForProductsPage() {

    final List<Product> products = productService.findProductsForProductsPage();

    final ProductsResponseDto response = ProductsResponseDto.from(products);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<ProductEntryDto> createProduct(
      @RequestBody final CreateProductRequestDto request) {

    final CreateProductRequest serviceRequest =
        new CreateProductRequest(request.name(), request.price());

    final Product product = productService.createProduct(serviceRequest);

    final ProductEntryDto response = ProductEntryDto.from(product);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductEntryDto> updateProduct(
      @PathVariable final UUID productId, @RequestBody final UpdateProductRequestDto request) {

    final UpdateProductRequest serviceRequest =
        new UpdateProductRequest(productId, request.name(), request.price());

    final Product product = productService.updateProduct(serviceRequest);

    final ProductEntryDto response = ProductEntryDto.from(product);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable final UUID productId) {

    productService.deleteProduct(productId);

    return ResponseEntity.noContent().build();
  }

  public record ProductsResponseDto(List<ProductEntryDto> products) {
    public static ProductsResponseDto from(final List<Product> products) {
      final List<ProductEntryDto> productEntries =
          products.stream().map(ProductEntryDto::from).toList();

      return new ProductsResponseDto(productEntries);
    }
  }

  public record ProductEntryDto(UUID id, String name, BigDecimal price) {
    private static ProductEntryDto from(final Product product) {
      return new ProductEntryDto(product.getId(), product.getName(), product.getPrice());
    }
  }

  public record CreateProductRequestDto(String name, BigDecimal price) {}

  public record UpdateProductRequestDto(String name, BigDecimal price) {}
}
