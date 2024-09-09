package com.slissner.codingtasks.dec.api.application.product;

import com.slissner.codingtasks.dec.api.application.user.CreateUserRequest;
import com.slissner.codingtasks.dec.api.domain.Product;
import com.slissner.codingtasks.dec.api.domain.User;
import com.slissner.codingtasks.dec.api.infrastructure.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
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

    return newProduct;
  }
}
