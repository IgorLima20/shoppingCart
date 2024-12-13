package com.project.shoppingCart.repositories;

import com.project.shoppingCart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
