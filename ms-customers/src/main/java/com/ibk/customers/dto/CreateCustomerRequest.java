package com.ibk.customers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {
    @NotNull(message = "uuid is required")
    private String uuid;

    @NotNull(message = "firstName is required")
    private String firstName;

    @NotNull(message = "lastName is required")
    private String lastName;

    @NotNull(message = "documentType is required")
    @Pattern(regexp = "RUC|DNI", message = "documentType must be RUC or DNI")
    private String documentType;

    @NotNull(message = "documentNumber is required")
    @Pattern(regexp = "[0-9]{8,11}", message = "documentNumber must be between 8 and 11 digits")
    private String documentNumber;
}
