package com.ibk.products.service;

import com.ibk.products.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> getAllProductsByCustomerId(String customerId);

    Optional<Product> getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);
}
