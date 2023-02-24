package com.mima.mimafhprojektbackend.repository;

import com.mima.mimafhprojektbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
