package com.trantring.ecommerce.DAO;

import com.trantring.ecommerce.Entity.Category;
import com.trantring.ecommerce.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByCategory(Category category, Pageable pageable);
}
