package com.ibk.products.controller;

import com.ibk.products.entity.Product;
import com.ibk.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product storedProduct = productService.createProduct(product);
        return new ResponseEntity<>(storedProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Product>> getProductsByCustomerId(@PathVariable("customerId") String customerId) {
        List<Product> products = productService.getAllProductsByCustomerId(customerId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.getProductById(id)
                .map(productFound -> {
                    productFound.setCustomerId(product.getCustomerId());
                    productFound.setType(product.getType());
                    productFound.setName(product.getName());
                    productFound.setBalance(product.getBalance());
                    Product updatedProduct = productService.updateProduct(productFound);
                    return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id)
                .map(product -> {
                    productService.deleteProduct(id);
                    return new ResponseEntity<>(product, HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
