package com.ibk.customers.mapper;

import com.ibk.customers.dto.CreateCustomerRequest;
import com.ibk.customers.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Customer toCustomer(CreateCustomerRequest customerRequest) {
        return modelMapper.map(customerRequest, Customer.class);
    }

    public CreateCustomerRequest toCreateCustomerRequest(Customer customer) {
        return modelMapper.map(customer, CreateCustomerRequest.class);
    }
}
