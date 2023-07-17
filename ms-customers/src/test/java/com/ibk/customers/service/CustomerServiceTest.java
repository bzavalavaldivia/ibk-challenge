package com.ibk.customers.service;

import com.ibk.customers.entity.Customer;
import com.ibk.customers.entity.Product;
import com.ibk.customers.repository.CustomerRepository;
import com.ibk.customers.service.impl.CustomerServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

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

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Customer")
    void createCustomer() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("uuid")
                .firstName("John")
                .lastName("Doe")
                .documentType("CC")
                .documentNumber("123456789")
                .build();

        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        // When
        Customer savedCustomer = customerService.createCustomer(customer);

        // Then
        assertAll("Create Customer",
                () -> assertNotNull(savedCustomer),
                () -> assertEquals(customer.getUuid(), savedCustomer.getUuid()),
                () -> assertEquals(customer.getFirstName(), savedCustomer.getFirstName()),
                () -> assertEquals(customer.getLastName(), savedCustomer.getLastName()),
                () -> assertEquals(customer.getDocumentType(), savedCustomer.getDocumentType()),
                () -> assertEquals(customer.getDocumentNumber(), savedCustomer.getDocumentNumber())
        );
    }

    @Test
    @DisplayName("Get All Customers")
    void getAllCustomers() throws Exception {
        // Given
        List<Customer> customers = new ArrayList<>();
        customers.add(Customer
                .builder()
                .id(1L)
                .uuid("uuid")
                .firstName("John")
                .lastName("Doe")
                .documentType("CC")
                .documentNumber("123456789")
                .build());

        given(customerRepository.findAll()).willReturn(customers);

        // When
        List<Customer> returnedCustomers = customerService.getAllCustomers();

        // Then
        assertAll("Get All Customers",
                () -> assertNotNull(returnedCustomers),
                () -> assertEquals(customers.size(), returnedCustomers.size()),
                () -> assertEquals(customers.get(0).getUuid(), returnedCustomers.get(0).getUuid()),
                () -> assertEquals(customers.get(0).getFirstName(), returnedCustomers.get(0).getFirstName()),
                () -> assertEquals(customers.get(0).getLastName(), returnedCustomers.get(0).getLastName()),
                () -> assertEquals(customers.get(0).getDocumentType(), returnedCustomers.get(0).getDocumentType()),
                () -> assertEquals(customers.get(0).getDocumentNumber(), returnedCustomers.get(0).getDocumentNumber())
        );
    }

    @Test
    @DisplayName("Get Customer By Id")
    void getCustomerById() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("uuid")
                .firstName("John")
                .lastName("Doe")
                .documentType("CC")
                .documentNumber("123456789")
                .build();

        given(customerRepository.findById(any(Long.class))).willReturn(java.util.Optional.of(customer));

        // When
        Customer returnedCustomer = customerService.getCustomerById(1L).get();

        // Then
        assertAll("Get Customer By Id",
                () -> assertNotNull(returnedCustomer),
                () -> assertEquals(customer.getUuid(), returnedCustomer.getUuid()),
                () -> assertEquals(customer.getFirstName(), returnedCustomer.getFirstName()),
                () -> assertEquals(customer.getLastName(), returnedCustomer.getLastName()),
                () -> assertEquals(customer.getDocumentType(), returnedCustomer.getDocumentType()),
                () -> assertEquals(customer.getDocumentNumber(), returnedCustomer.getDocumentNumber())
        );
    }

    @Test
    @DisplayName("Update Customer")
    void updateCustomer() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("uuid")
                .firstName("John")
                .lastName("Doe")
                .documentType("CC")
                .documentNumber("123456789")
                .build();

        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        // When
        Customer savedCustomer = customerService.updateCustomer(customer);

        // Then
        assertAll("Update Customer",
                () -> assertNotNull(savedCustomer),
                () -> assertEquals(customer.getUuid(), savedCustomer.getUuid()),
                () -> assertEquals(customer.getFirstName(), savedCustomer.getFirstName()),
                () -> assertEquals(customer.getLastName(), savedCustomer.getLastName()),
                () -> assertEquals(customer.getDocumentType(), savedCustomer.getDocumentType()),
                () -> assertEquals(customer.getDocumentNumber(), savedCustomer.getDocumentNumber())
        );
    }

    @Test
    @DisplayName("Delete Customer")
    void deleteCustomer() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("uuid")
                .firstName("John")
                .lastName("Doe")
                .documentType("CC")
                .documentNumber("123456789")
                .build();

        willDoNothing().given(customerRepository).deleteById(any(Long.class));

        // When
        customerService.deleteCustomer(1L);

        // Then
        verify(customerRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("Get Products By Customer Uuid")
    void getProductsByCustomerUuid() throws Exception {
        // Given
        Customer customer = Customer
                .builder()
                .id(1L)
                .uuid("123")
                .firstName("John")
                .lastName("Doe")
                .documentType("CC")
                .documentNumber("123456789")
                .products(products)
                .build();

        given(customerRepository.findByUuid(any(String.class))).willReturn(Optional.of(customer));

        // When
        Customer returnedCustomer = customerService.getProductsByCustomerUuid("uuid").get();

        // Then
        assertAll("Get Products By Customer Uuid",
                () -> assertNotNull(returnedCustomer),
                () -> assertEquals(customer.getUuid(), returnedCustomer.getUuid()),
                () -> assertEquals(customer.getFirstName(), returnedCustomer.getFirstName()),
                () -> assertEquals(customer.getLastName(), returnedCustomer.getLastName()),
                () -> assertEquals(customer.getDocumentType(), returnedCustomer.getDocumentType()),
                () -> assertEquals(customer.getDocumentNumber(), returnedCustomer.getDocumentNumber()),
                () -> assertEquals(customer.getProducts().get(0).getCustomerId(), returnedCustomer.getUuid()),
                () -> assertEquals(customer.getProducts().size(), returnedCustomer.getProducts().size()),
                () -> assertEquals(customer.getProducts().get(0).getType(), returnedCustomer.getProducts().get(0).getType()),
                () -> assertEquals(customer.getProducts().get(0).getName(), returnedCustomer.getProducts().get(0).getName()),
                () -> assertEquals(customer.getProducts().get(0).getBalance(), returnedCustomer.getProducts().get(0).getBalance())
        );
    }
}
