package com.ibk.customers.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Product {
    private Long id;

    private String customerId;

    private String type;

    private String name;

    private Double balance;
}
