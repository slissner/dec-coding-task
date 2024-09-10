package com.slissner.codingtasks.dec.api.application.product;

import com.slissner.codingtasks.dec.api.domain.Product;
import com.slissner.codingtasks.dec.api.infrastructure.repository.ProductRepository;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
  private final ApplicationEventPublisher eventPublisher;
  private final ProductRepository productRepository;

  /** Method to display a table/list of products in the client UI. */
  public List<Product> findProductsForProductsPage() {
    return productRepository.findAllForProductsPage();
  }

  @Transactional
  public Product createProduct(@NonNull final CreateProductRequest request) {
    final Product newProduct = Product.create(request.name(), request.price());

    productRepository.save(newProduct);

    log.info("New product has been created. [product.id={}]", newProduct.getId());

    eventPublisher.publishEvent(
        new Product.ProductsHaveChangedEvent(
            // TODO use real UUID
            this, UUID.randomUUID(), newProduct.getLastChangedAt()));

    return newProduct;
  }

  @Transactional
  public Product updateProduct(@NonNull final UpdateProductRequest request) {
    final Product updatedProduct =
        productRepository
            .findById(request.id())
            .map(product -> product.update(request))
            .map(productRepository::save)
            .orElseThrow(
                () ->
                    new Product.ProductNotFoundException(
                        "Could not find product with id: " + request.id()));

    log.info("Product has been updated. [product.id={}]", updatedProduct.getId());

    eventPublisher.publishEvent(
        new Product.ProductsHaveChangedEvent(
            // TODO use real UUID
            this, UUID.randomUUID(), updatedProduct.getLastChangedAt()));

    return updatedProduct;
  }

  public void deleteProduct(final UUID productId) {
    final Product existingProduct =
        productRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new Product.ProductNotFoundException(
                        "Could not find product with id: " + productId));

    productRepository.delete(existingProduct);

    eventPublisher.publishEvent(
        new Product.ProductsHaveChangedEvent(
            // TODO use real UUID
            this, UUID.randomUUID(), Instant.now()));

    log.info("Product has been deleted. [product.id={}]", productId);
  }
}
