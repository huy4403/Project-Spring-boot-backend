package com.huy.aristino.repository;

import com.huy.aristino.model.Category;
import com.huy.aristino.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Boolean existsProductByName(String name);

    Product findProductByName(String name);

    List<Product> findProductByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findByNameContaining(@Param("name") String name);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.name = :name AND p.id != :id")
    Boolean existsProductByNameDiffId(String name, int id);
}
