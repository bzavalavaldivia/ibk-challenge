package com.ibk.products.service;

import com.ibk.products.entity.Product;
import com.ibk.products.enums.ProductType;
import com.ibk.products.repository.ProductRepository;
import com.ibk.products.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Product")
    public void createProduct() {
        // Given
        Product product = Product
                .builder()
                .id(1L)
                .customerId("123")
                .type(ProductType.CUENTA_AHORROS)
                .name("Cuenta de Ahorros")
                .balance(1000000.0)
                .build();

        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        Product storedProduct = productService.createProduct(product);

        // Then
        assertAll("Create Product",
                () -> assertNotNull(storedProduct),
                () -> assertEquals(product.getCustomerId(), storedProduct.getCustomerId()),
                () -> assertEquals(product.getType(), storedProduct.getType()),
                () -> assertEquals(product.getName(), storedProduct.getName()),
                () -> assertEquals(product.getBalance(), storedProduct.getBalance())
        );
    }

    @Test
    @DisplayName("Get All Products")
    public void getAllProducts() {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(1L).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build());
        products.add(Product.builder().id(2L).customerId("123").type(ProductType.CUENTA_CORRIENTE).name("Cuenta Corriente").balance(500000.0).build());

        given(productRepository.findAll()).willReturn(products);

        // When
        List<Product> returnedProducts = productService.getAllProducts();

        // Then
        assertAll("Get All Products",
                () -> assertNotNull(returnedProducts),
                () -> assertEquals(products.size(), returnedProducts.size()),
                () -> assertEquals(products.get(0).getCustomerId(), returnedProducts.get(0).getCustomerId()),
                () -> assertEquals(products.get(0).getType(), returnedProducts.get(0).getType()),
                () -> assertEquals(products.get(0).getName(), returnedProducts.get(0).getName()),
                () -> assertEquals(products.get(0).getBalance(), returnedProducts.get(0).getBalance())
        );
    }

    @Test
    @DisplayName("Get All Products By Customer Id")
    public void getAllProductsByCustomerId() {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(1L).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build());
        products.add(Product.builder().id(2L).customerId("123").type(ProductType.CUENTA_CORRIENTE).name("Cuenta Corriente").balance(500000.0).build());

        given(productRepository.findByCustomerId("123")).willReturn(products);

        // When
        List<Product> returnedProducts = productService.getAllProductsByCustomerId("123");

        // Then
        assertAll("Get All Products By Customer Id",
                () -> assertNotNull(returnedProducts),
                () -> assertEquals(products.size(), returnedProducts.size()),
                () -> assertEquals(products.get(0).getCustomerId(), returnedProducts.get(0).getCustomerId()),
                () -> assertEquals(products.get(0).getType(), returnedProducts.get(0).getType()),
                () -> assertEquals(products.get(0).getName(), returnedProducts.get(0).getName()),
                () -> assertEquals(products.get(0).getBalance(), returnedProducts.get(0).getBalance())
        );
    }

    @Test
    @DisplayName("Get Product By Id")
    void getProductById() {
        // Given
        Product product = Product.builder().id(1L).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build();

        given(productRepository.findById(1L)).willReturn(java.util.Optional.of(product));

        // When
        Product returnedProduct = productService.getProductById(1L).get();

        // Then
        assertAll("Get Product By Id",
                () -> assertNotNull(returnedProduct),
                () -> assertEquals(product.getCustomerId(), returnedProduct.getCustomerId()),
                () -> assertEquals(product.getType(), returnedProduct.getType()),
                () -> assertEquals(product.getName(), returnedProduct.getName()),
                () -> assertEquals(product.getBalance(), returnedProduct.getBalance())
        );
    }

    @Test
    @DisplayName("Update Product")
    void updateProduct() {
        // Given
        Product product = Product.builder().id(1L).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build();

        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        Product returnedProduct = productService.updateProduct(product);

        // Then
        assertAll("Update Product",
                () -> assertNotNull(returnedProduct),
                () -> assertEquals(product.getCustomerId(), returnedProduct.getCustomerId()),
                () -> assertEquals(product.getType(), returnedProduct.getType()),
                () -> assertEquals(product.getName(), returnedProduct.getName()),
                () -> assertEquals(product.getBalance(), returnedProduct.getBalance())
        );
    }

    @Test
    @DisplayName("Delete Product")
    void deleteProduct() {
        // Given
        willDoNothing().given(productRepository).deleteById(any(Long.class));

        // When
        productService.deleteProduct(1L);

        // Then
        verify(productRepository, times(1)).deleteById(any(Long.class));
    }
}
