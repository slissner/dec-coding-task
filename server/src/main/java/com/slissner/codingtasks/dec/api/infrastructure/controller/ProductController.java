package com.slissner.codingtasks.dec.api.infrastructure.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
class ProductController {

  @GetMapping
  public List<Void> all() {
    // FIXME
    return Collections.emptyList();
  }
}
