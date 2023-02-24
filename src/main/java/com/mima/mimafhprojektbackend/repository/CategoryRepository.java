package com.mima.mimafhprojektbackend.repository;
import com.mima.mimafhprojektbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
