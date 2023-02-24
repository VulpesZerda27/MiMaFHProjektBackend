package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        category.setCategoryName(categoryDetails.getCategoryName());
        category.setCategoryId(categoryDetails.getCategoryId());
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
