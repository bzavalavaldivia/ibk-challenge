package com.ibk.customers.service.impl;

import com.ibk.customers.entity.Customer;
import com.ibk.customers.entity.Product;
import com.ibk.customers.repository.CustomerRepository;
import com.ibk.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByUuid(String uuid) {
        return customerRepository.findByUuid(uuid);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> getProductsByCustomerUuid(String uuid) {
        List<Product> products = restTemplate.getForObject("http://ms-products/api/v1/products/customer/" + uuid, List.class);
        Optional<Customer> customer = customerRepository.findByUuid(uuid);
        return customer.map(c -> {
            c.setProducts(products);
            return customer;
        }).orElseGet(Optional::empty);
    }
}
