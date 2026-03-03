package com.ari.webapp.controller;

import com.ari.webapp.dto.ProductCreateDto;
import com.ari.webapp.dto.ProductDto;
import com.ari.webapp.dto.ProductUpdateDto;
import com.ari.webapp.model.Category;
import com.ari.webapp.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        ProductDto product = productService.createProduct(productCreateDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        ProductDto product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> findByName(@PathVariable String name) {
        ProductDto product = productService.findByName(name);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<List<ProductDto>> inStock() {
        List<ProductDto> products = productService.inStock();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> findByCategory(@PathVariable Category category) {
        List<ProductDto> product = productService.findByCategory(category);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDto productUpdateDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productUpdateDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        log.info("Product successfully deleted with id {}", id);
        return ResponseEntity.noContent().build();
    }
}
