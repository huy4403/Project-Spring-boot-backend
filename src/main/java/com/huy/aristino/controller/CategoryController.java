package com.huy.aristino.controller;

import com.huy.aristino.model.Category;
import com.huy.aristino.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("api/category")
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") int id, @RequestBody Category category) {
        category.setId(id);
        Category updateCategory = categoryService.updateCategory(category);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<String>("Đẫ xóa thành công category", HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
