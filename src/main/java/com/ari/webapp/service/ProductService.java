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
        return new ProductDto(savedProduct.getId(), savedProduct.getName(),
                savedProduct.getPrice(), savedProduct.getImage(),
                savedProduct.getDescription(), savedProduct.getStock(),
                savedProduct.getCategory());
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductDto(
                        product.getId(),
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
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImage(), product.getDescription(), product.getStock(), product.getCategory());
    }

    public ProductDto findByName(String name) {
        Product product = productRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Product with name " + name + " not found"));
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImage(), product.getDescription(), product.getStock(), product.getCategory());
    }

    public List<ProductDto> findByCategory(Category category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(), product.getPrice(), product.getImage(),
                        product.getDescription(), product.getStock(), product.getCategory()))
                .collect(Collectors.toList());
    }

    public List<ProductDto> inStock() {
        List<Product> products = productRepository.findByStockGreaterThan(0);
        return products.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(), product.getPrice(), product.getImage(),
                        product.getDescription(), product.getStock(), product.getCategory()
                )).collect(Collectors.toList());
    }

    public ProductDto updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (productUpdateDto.getProductName() != null) {
            product.setName(productUpdateDto.getProductName());
        }
        if (productUpdateDto.getProductDescription() != null) {
            product.setDescription(productUpdateDto.getProductDescription());
        }
        if (productUpdateDto.getProductImage() != null) {
            product.setImage(productUpdateDto.getProductImage());
        }
        if (productUpdateDto.getProductPrice() != null) {
            product.setPrice(productUpdateDto.getProductPrice());
        }
        if (productUpdateDto.getProductDiscount() != null) {
            product.setDiscount(productUpdateDto.getProductDiscount());
        }
        if (productUpdateDto.getProductStock() != null) {
            product.setStock(productUpdateDto.getProductStock());
        }
        if (productUpdateDto.getProductCategory() != null) {
            product.setCategory(productUpdateDto.getProductCategory());
        }

        Product updatedProduct = productRepository.save(product);
        return new ProductDto(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getPrice(),
                updatedProduct.getDescription(), updatedProduct.getImage(),
                updatedProduct.getStock(), updatedProduct.getCategory());
    }

    public void deleteById(Long id) {
        Product product  = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);
    }
}
