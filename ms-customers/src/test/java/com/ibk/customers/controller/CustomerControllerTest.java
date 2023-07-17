package com.ibk.customers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibk.customers.dto.CreateCustomerRequest;
import com.ibk.customers.entity.Customer;
import com.ibk.customers.entity.Product;
import com.ibk.customers.enums.DocumentType;
import com.ibk.customers.service.CustomerService;
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
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<Product> products = new ArrayList<>() {{
        add(Product
                .builder()
                .id(1L)
                .customerId("123")
                .type("CUENTA_AHORROS")
                .name("Cuenta de Ahorros")
                .balance(1000000.0)
                .build());
        add(Product
                .builder()
                .id(2L)
                .customerId("123")
                .type("CUENTA_CORRIENTE")
                .name("Cuenta Corriente")
                .balance(500000.0)
                .build());
        add(Product
                .builder()
                .id(3L)
                .customerId("123")
                .type("TARJETA_CREDITO_AMEX")
                .name("Tarjeta de Crédito AMEX")
                .balance(1000000.0)
                .build());
    }};

    @Test
    @DisplayName("Create Customer")
    void createCustomer() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("1234567890")
                .firstName("John")
                .lastName("Doe")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .build();

        given(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        // Then
        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.uuid").value(customer.getUuid()))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.documentType").value(customer.getDocumentType()))
                .andExpect(jsonPath("$.documentNumber").value(customer.getDocumentNumber()))
        ;
    }

    @Test
    @DisplayName("Get All Customers")
    void getAllCustomers() throws Exception {
        // Given
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(Customer.builder().id(1L).uuid("1234567890").firstName("John").lastName("Doe").documentType(DocumentType.DNI).documentNumber("12345678").build());
        customers.add(Customer.builder().id(2L).uuid("0987654321").firstName("Jane").lastName("Doe").documentType(DocumentType.DNI).documentNumber("87654321").build());
        customers.add(Customer.builder().id(3L).uuid("1234509876").firstName("John").lastName("Smith").documentType(DocumentType.RUC).documentNumber("12345678901").build());

        given(customerService.getAllCustomers()).willReturn(customers);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/customers"));

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(customers.size()));
    }

    @Test
    @DisplayName("Get Customer By Id")
    void getCustomerById() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("1234567890")
                .firstName("John")
                .lastName("Doe")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .build();

        given(customerService.getCustomerById(any(Long.class))).willReturn(Optional.of(customer));

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/customers/{id}", 1L));

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.uuid").value(customer.getUuid()))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.documentType").value(customer.getDocumentType()))
                .andExpect(jsonPath("$.documentNumber").value(customer.getDocumentNumber()));
    }

    @Test
    @DisplayName("Get Customer By Id Not Found")
    void getCustomerByIdNotFound() throws Exception {
        // Given
        given(customerService.getCustomerById(1L)).willReturn(Optional.empty());

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/customers/{id}", 1L));

        // Then
        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get Products By Customer Id")
    void getProductsByCustomerId() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("123")
                .firstName("John")
                .lastName("Doe")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .products(products)
                .build();

        given(customerService.getProductsByCustomerUuid("123456")).willReturn(Optional.of(customer));

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/customers/{uuid}/products", "123456"));

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.uuid").value(customer.getUuid()))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.documentType").value(customer.getDocumentType()))
                .andExpect(jsonPath("$.documentNumber").value(customer.getDocumentNumber()))
                .andExpect(jsonPath("$.products.size()").value(customer.getProducts().size()))
                .andExpect(jsonPath("$.products[0].customerId").value(customer.getUuid()));
    }

    @Test
    @DisplayName("Update Customer")
    void updateCustomer() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("1234567890")
                .firstName("John")
                .lastName("Doe")
                .documentType(DocumentType.RUC)
                .documentNumber("12345678901")
                .build();

        Customer customerUpdated = Customer
                .builder()
                .id(1L)
                .uuid("1234567890")
                .firstName("John")
                .lastName("Doe")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .build();

        given(customerService.getCustomerById(1L)).willReturn(Optional.of(customer));
        given(customerService.updateCustomer(any(Customer.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions result = mockMvc.perform(put("/api/v1/customers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerUpdated)));

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerUpdated.getId()))
                .andExpect(jsonPath("$.uuid").value(customerUpdated.getUuid()))
                .andExpect(jsonPath("$.firstName").value(customerUpdated.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customerUpdated.getLastName()))
                .andExpect(jsonPath("$.documentType").value(customerUpdated.getDocumentType()))
                .andExpect(jsonPath("$.documentNumber").value(customerUpdated.getDocumentNumber()));
    }

    @Test
    @DisplayName("Update Customer Not Found")
    void updateCustomerNotFound() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("1234567890")
                .firstName("John")
                .lastName("Doe")
                .documentType(DocumentType.RUC)
                .documentNumber("12345678901")
                .build();

        given(customerService.getCustomerById(1L)).willReturn(Optional.empty());

        // When
        ResultActions result = mockMvc.perform(put("/api/v1/customers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        // Then
        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Customer")
    void deleteCustomer() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("1234567890")
                .firstName("John")
                .lastName("Doe")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .build();

        given(customerService.getCustomerById(1L)).willReturn(Optional.of(customer));

        // When
        ResultActions result = mockMvc.perform(delete("/api/v1/customers/{id}", 1L));

        // Then
        result.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Customer Not Found")
    void deleteCustomerNotFound() throws Exception {
        // Given
        given(customerService.getCustomerById(1L)).willReturn(Optional.empty());

        // When
        ResultActions result = mockMvc.perform(delete("/api/v1/customers/{id}", 1L));

        // Then
        result.andDo(print())
                .andExpect(status().isNotFound());
    }
}
