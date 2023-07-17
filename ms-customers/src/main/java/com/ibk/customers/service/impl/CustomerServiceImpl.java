package com.ibk.customers.service.impl;

import com.ibk.customers.dto.CreateCustomerRequest;
import com.ibk.customers.entity.Customer;
import com.ibk.customers.entity.Product;
import com.ibk.customers.mapper.CustomerMapper;
import com.ibk.customers.repository.CustomerRepository;
import com.ibk.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper mapper;

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

    public Customer createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = this.mapper.toCustomer(customerRequest);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> getProductsByCustomerUuid(String uuid) {
        Optional<Customer> customer = customerRepository.findByUuid(uuid);
        return customer.map(c -> {
            c.setProducts(products);
            return customer;
        }).orElseGet(Optional::empty);
    }
}
