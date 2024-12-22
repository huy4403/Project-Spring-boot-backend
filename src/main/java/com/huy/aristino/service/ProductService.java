package com.huy.aristino.service;

import com.huy.aristino.model.Category;
import com.huy.aristino.model.Product;

import java.util.List;

public interface ProductService {

    Boolean existsProductByName(String name);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(int id);

    Product getProductById(int id);

    List<Product> getAllProducts();

    Product findProductByName(String name);

    List<Product> findProductByCategory(Category category);

    List<Product> findByNameContaining(String name);
}
