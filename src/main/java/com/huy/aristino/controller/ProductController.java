package com.huy.aristino.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.aristino.model.Category;
import com.huy.aristino.model.Product;
import com.huy.aristino.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("api/product")
public class ProductController {

    @Value("${upload.path}")
    private String uploadPath;

    private ProductService productService;


    @PostMapping
    public ResponseEntity<?> createProduct(@RequestPart("product") String productJson,
                                           @RequestParam("file") MultipartFile file) {
        // Convert the product JSON string to a Product object
        ObjectMapper objectMapper = new ObjectMapper();
        Product product;
        try {
            product = objectMapper.readValue(productJson, Product.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid product data: " + e.getMessage()));
        }

        // Check if the product exists
        boolean existProduct = productService.existsProductByName(product.getName());
        if (existProduct) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Sản phẩm đã tồn tại rồi"));
        }

        try {
            // Handle file upload
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadDir = Path.of(uploadPath, "imgProduct");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path targetPath = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            product.setImg(fileName);  // Set the image path on the product

            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.OK).body(createdProduct);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Lỗi khi lưu ảnh: " + e.getMessage()));
        }
    }


    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        product.setId(id);
        Product updateProduct = productService.updateProduct(product);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Xóa thành công sản phẩm", HttpStatus.OK);
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<?> findProductByName(@PathVariable("name") String name) {
        boolean existProduct = productService.existsProductByName(name);
        if (existProduct) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Không tìm thấy sản phẩm tên " + name);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Product product = productService.findProductByName(name);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("category")
    public ResponseEntity<?> findProductByCategory(@RequestBody Category category) {
        List<Product> products = productService.findProductByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductsByName(@RequestParam("name") String name) {
        List<Product> products = productService.findByNameContaining(name);
        if(products == null){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Không tìm thấy sản phẩm " +name);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
