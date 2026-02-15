package com.ari.webapp.service;

import com.ari.webapp.model.Product;
import com.ari.webapp.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new EntityExistsException("Product with name " + product.getName() + " already exists");
        }
        if (product.getPrice() < 0) {
            throw new RuntimeException("Price cant be less than 0");
        }
        if (product.getCategory() == null) {
            throw new RuntimeException("The product category cant be null");
        }
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public Product findByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public List<Product> getByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> inStock() {
        return productRepository.findByStockGreaterThan(0);
    }

    public Product updateProduct(Long id, Product productData) {
        Product updatedProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        updatedProduct.setName(productData.getName());
        updatedProduct.setPrice(productData.getPrice());
        updatedProduct.setCategory(productData.getCategory());
        return productRepository.save(updatedProduct);
    }

    public void deleteById(Long id) {
        Product product  = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
