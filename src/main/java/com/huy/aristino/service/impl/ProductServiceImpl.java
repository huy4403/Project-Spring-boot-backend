package com.huy.aristino.service.impl;

import com.huy.aristino.model.Category;
import com.huy.aristino.model.Product;
import com.huy.aristino.repository.ProductRepository;
import com.huy.aristino.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product existProduct = getProductById(product.getId());
        if(product.getName() != null){
            existProduct.setName(product.getName());
        }
        if(product.getQuantity() != -1){
            existProduct.setQuantity(product.getQuantity());
        }
        if(product.getImportPrice() != -1){
            existProduct.setImportPrice(product.getImportPrice());
        }
        if(product.getPrice() != -1){
            existProduct.setPrice(product.getPrice());
        }
        if(product.getImg() != null){
            existProduct.setImg(product.getImg());
        }
        if(product.getDescription() != null){
            existProduct.setDescription(product.getDescription());
        }
        if(product.getCategory() != null){
            existProduct.setCategory(product.getCategory());
        }
        Product updateProduct = productRepository.save(existProduct);
        return updateProduct;
    }

    @Override
    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }

    @Override
    public List<Product> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Boolean existsProductByName(String name){
        return productRepository.existsProductByName(name);
    }

    @Override
    public Product findProductByName(String name){
        Product product = productRepository.findProductByName(name);
        return product;
    }

    @Override
    public List<Product> findProductByCategory(Category category){
        List<Product> products = productRepository.findProductByCategory(category);
        return products;
    }

    @Override
    public List<Product> findByNameContaining(String name){
        List<Product> products = productRepository.findByNameContaining(name);
        return products;
    }
}
