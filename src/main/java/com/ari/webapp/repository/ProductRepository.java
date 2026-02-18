package com.ari.webapp.repository;

import com.ari.webapp.model.Category;
import com.ari.webapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String productName);
    List<Product> findByCategory(Category category);
    List<Product> findByStockGreaterThan(Integer stock);
}
