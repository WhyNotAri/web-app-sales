package com.ari.webapp.service;

import com.ari.webapp.dto.ProductCreateDto;
import com.ari.webapp.dto.ProductDto;
import com.ari.webapp.dto.ProductUpdateDto;
import com.ari.webapp.model.Category;
import com.ari.webapp.model.Product;
import com.ari.webapp.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        if (productRepository.findByName(productCreateDto.getProductName()).isPresent()) {
            throw new EntityExistsException("Product with name " + productCreateDto.getProductName() + " already exists");
        }
        Product product = new Product();
        product.setName(productCreateDto.getProductName());
        product.setPrice(productCreateDto.getProductPrice());
        product.setImage(productCreateDto.getProductImage());
        product.setDescription(productCreateDto.getProductDescription());
        product.setCategory(productCreateDto.getProductCategory());
        product.setStock(productCreateDto.getProductStock());
        Product savedProduct = productRepository.save(product);
        return new ProductDto(savedProduct.getName(),
                savedProduct.getPrice(), savedProduct.getImage(),
                savedProduct.getDescription(), savedProduct.getStock(),
                savedProduct.getCategory());
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductDto(
                        product.getName(),
                        product.getPrice(),
                        product.getImage(),
                        product.getDescription(),
                        product.getStock(),
                        product.getCategory()
                ))
                .collect(Collectors.toList());
    }

    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        return new ProductDto(product.getName(), product.getPrice(), product.getImage(), product.getDescription(), product.getStock(), product.getCategory());
    }

    public ProductDto findByName(String name) {
        Product product = productRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Product with name " + name + " not found"));
        return new ProductDto(product.getName(), product.getPrice(), product.getImage(), product.getDescription(), product.getStock(), product.getCategory());
    }

    public List<ProductDto> findByCategory(Category category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(product -> new ProductDto(
                        product.getName(), product.getPrice(), product.getImage(),
                        product.getDescription(), product.getStock(), product.getCategory()))
                .collect(Collectors.toList());
    }

    public List<ProductDto> inStock() {
        List<Product> products = productRepository.findByStockGreaterThan(0);
        return products.stream()
                .map(product -> new ProductDto(
                        product.getName(), product.getPrice(), product.getImage(),
                        product.getDescription(), product.getStock(), product.getCategory()
                )).collect(Collectors.toList());
    }

    public ProductDto updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if (product.getName() != null) {
            product.setName(productUpdateDto.getName());
        }
        if (product.getDescription() != null) {
            product.setDescription(productUpdateDto.getDescription());
        }
        if (product.getImage() != null) {
            product.setImage(productUpdateDto.getImage());
        }
        if (product.getPrice() != null) {
            product.setPrice(productUpdateDto.getPrice());
        }
        if (product.getDiscount() != null) {
            product.setDiscount(productUpdateDto.getDiscount());
        }

        Product updatedProduct = productRepository.save(product);
        return new ProductDto(updatedProduct.getName(), updatedProduct.getPrice(),
                updatedProduct.getDescription(), updatedProduct.getImage(),
                updatedProduct.getStock(), updatedProduct.getCategory());
    }

    public void deleteById(Long id) {
        Product product  = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);
    }
}
