package com.ibk.products.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibk.products.entity.Product;
import com.ibk.products.enums.ProductType;
import com.ibk.products.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test to create a product")
    public void createProduct() throws Exception {
        // Given
        Product product = Product
                .builder()
                .id(1L)
                .customerId("123")
                .type(ProductType.CUENTA_AHORROS)
                .name("Cuenta de Ahorros")
                .balance(1000000.0)
                .build();

        given(productService.createProduct(any(Product.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // Then
        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.customerId").value(product.getCustomerId()))
                .andExpect(jsonPath("$.type").value(product.getType().name()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.balance").value(product.getBalance()));
    }

    @Test
    @DisplayName("Test to get all products")
    void getAllProducts() throws Exception {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(1L).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build());
        products.add(Product.builder().id(2L).customerId("123").type(ProductType.CUENTA_CORRIENTE).name("Cuenta Corriente").balance(500000.0).build());

        given(productService.getAllProducts()).willReturn(products);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(products.size()))
                .andExpect(jsonPath("$[0].id").value(products.get(0).getId()))
                .andExpect(jsonPath("$[0].customerId").value(products.get(0).getCustomerId()))
                .andExpect(jsonPath("$[0].type").value(products.get(0).getType().name()))
                .andExpect(jsonPath("$[0].name").value(products.get(0).getName()))
                .andExpect(jsonPath("$[0].balance").value(products.get(0).getBalance()));
    }

    @Test
    @DisplayName("Test to get all products by customer id")
    void getAllProductsByCustomerId() throws Exception {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(1L).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build());
        products.add(Product.builder().id(2L).customerId("123").type(ProductType.CUENTA_CORRIENTE).name("Cuenta Corriente").balance(500000.0).build());

        given(productService.getAllProductsByCustomerId(any(String.class))).willReturn(products);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/products/customer/{customerId}", products.get(0).getCustomerId())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(products.size()))
                .andExpect(jsonPath("$[0].id").value(products.get(0).getId()))
                .andExpect(jsonPath("$[0].customerId").value(products.get(0).getCustomerId()))
                .andExpect(jsonPath("$[0].type").value(products.get(0).getType().name()))
                .andExpect(jsonPath("$[0].name").value(products.get(0).getName()))
                .andExpect(jsonPath("$[0].balance").value(products.get(0).getBalance()));
    }

    @Test
    @DisplayName("Test to get a product by id")
    void getProductById() throws Exception {
        // Given
        Product product = Product.builder().id(1L).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build();

        given(productService.getProductById(any(Long.class))).willReturn(Optional.of(product));

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.customerId").value(product.getCustomerId()))
                .andExpect(jsonPath("$.type").value(product.getType().name()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.balance").value(product.getBalance()));
    }

    @Test
    @DisplayName("Test to get a product by id not found")
    void getProductByIdNotFound() throws Exception {
        // Given
        Long id = 1L;

        given(productService.getProductById(any(Long.class))).willReturn(Optional.empty());

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test to update a product")
    void updateProduct() throws Exception {
        // Given
        Product product = Product.builder().id(1L).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build();

        given(productService.getProductById(any(Long.class))).willReturn(Optional.of(product));
        given(productService.updateProduct(any(Product.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions result = mockMvc.perform(put("/api/v1/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.customerId").value(product.getCustomerId()))
                .andExpect(jsonPath("$.type").value(product.getType().name()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.balance").value(product.getBalance()));
    }

    @Test
    @DisplayName("Test to update a product not found")
    void updateProductNotFound() throws Exception {
        // Given
        Long id = 1L;
        Product product = Product.builder().id(id).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build();

        given(productService.getProductById(any(Long.class))).willReturn(Optional.empty());

        // When
        ResultActions result = mockMvc.perform(put("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // Then
        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test to delete a product")
    void deleteProduct() throws Exception {
        // Given
        Long id = 1L;
        Product product = Product.builder().id(id).customerId("123").type(ProductType.CUENTA_AHORROS).name("Cuenta de Ahorros").balance(1000000.0).build();

        given(productService.getProductById(any(Long.class))).willReturn(Optional.of(product));

        // When
        ResultActions result = mockMvc.perform(delete("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test to delete a product not found")
    void deleteProductNotFound() throws Exception {
        // Given
        Long id = 1L;

        given(productService.getProductById(any(Long.class))).willReturn(Optional.empty());

        // When
        ResultActions result = mockMvc.perform(delete("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(print())
                .andExpect(status().isNotFound());
    }
}
