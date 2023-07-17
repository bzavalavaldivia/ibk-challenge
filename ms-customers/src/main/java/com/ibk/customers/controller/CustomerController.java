package com.ibk.customers.controller;

import com.ibk.customers.entity.Customer;
import com.ibk.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer>
    getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{uuid}/products")
    public ResponseEntity<Customer> getProductsByCustomerId(@PathVariable("uuid") String uuid) {
        Optional<Customer> customer = customerService.getProductsByCustomerUuid(uuid);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return customerService.getCustomerById(id)
                .map(customerFound -> {
                    customerFound.setFirstName(customer.getFirstName());
                    customerFound.setLastName(customer.getLastName());
                    customerFound.setDocumentType(customer.getDocumentType());
                    customerFound.setDocumentNumber(customer.getDocumentNumber());
                    Customer updatedCustomer = customerService.updateCustomer(customerFound);
                    return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(customer -> {
                     customerService.deleteCustomer(customer.getId());
                    return new ResponseEntity<>(customer, HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
