package com.huy.aristino.service.impl;

import com.huy.aristino.model.Category;
import com.huy.aristino.repository.CategoryRepository;
import com.huy.aristino.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category categoryExist = getCategoryById(category.getId());
        categoryExist.setName(category.getName());
        Category categoryUpdate = categoryRepository.save(categoryExist);

        return categoryUpdate;
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category getCategoryById(int id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.get();
    }

}
