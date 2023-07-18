package com.ibk.customers.entity;

import com.ibk.customers.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @Column(unique = true, nullable = false)
    @NotNull(message = "uuid is required")
    private String uuid;

    @Column(nullable = false)
    @NotNull(message = "firstName is required")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "lastName is required")
    private String lastName;

    @Column(columnDefinition = "ENUM('RUC', 'DNI')", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "documentType is required")
//    @Pattern(regexp = "RUC|DNI", message = "documentType must be RUC or DNI")
    private DocumentType documentType;

    @Column(nullable = false)
    @NotNull(message = "documentNumber is required")
    @Pattern(regexp = "[0-9]{8,11}", message = "documentNumber must be between 8 and 11 digits")
    private String documentNumber;

    @Transient
    private List<Product> products;
}
