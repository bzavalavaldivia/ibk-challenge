package com.ibk.customers.entity;

import com.ibk.customers.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    private String firstName;

    private String lastName;

    @Column(columnDefinition = "ENUM('RUC', 'DNI')")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String documentNumber;

    @Transient
    private List<Product> products;
}
