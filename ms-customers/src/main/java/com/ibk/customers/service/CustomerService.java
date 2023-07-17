package com.ibk.customers.service;

import com.ibk.customers.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Long id);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Long id);

    Optional<Customer> getProductsByCustomerUuid(String uuid);
}
