package com.ibk.products.entity;

import com.ibk.products.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('CUENTA_AHORROS', 'CUENTA_CORRIENTE', 'CREDITO_PERSONAL', 'TARJETA_CREDITO_AMEX', 'TARJETA_CREDITO_VISA', 'TARJETA_CREDITO_MASTER_CARD')"
            , nullable = false)
    private ProductType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2), default 0.0")
    private Double balance;
}
